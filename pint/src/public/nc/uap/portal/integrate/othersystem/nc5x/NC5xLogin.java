package nc.uap.portal.integrate.othersystem.nc5x;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.logging.Logger;
import nc.bs.ml.NCLangResOnserver;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.authfield.ComboExtAuthField;
import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.lfw.login.authfield.PasswordExtAuthField;
import nc.uap.lfw.login.authfield.RefExtAuthField;
import nc.uap.lfw.login.authfield.TextExtAuthField;
import nc.uap.portal.integrate.IWebAppFunNodesService;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.exception.PortletLoginException;
import nc.uap.portal.integrate.funnode.SsoSystemNode;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.user.entity.IUserVO;
import nc.uap.portal.util.ToolKit;
import nc.vo.ml.IProductCode;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.sm.cmenu.CustomMenuShortcutVO;
import nc.vo.sm.funcreg.FunRegisterConst;
import nc.vo.sm.funcreg.FuncRegisterVO;

import org.apache.commons.lang.StringUtils;

/**
 * NC5.x������
 * @author licza
 *
 */
public class NC5xLogin implements IWebAppLoginService,IWebAppFunNodesService{
	private static final String NULL_STRING = "NULL";
	@Override
	public PtCredentialVO credentialProcess(HttpServletRequest req,
			SSOProviderVO provider) throws CredentialValidateException {
		try {
			// ����ƾ֤VO
			PtCredentialVO credentialVO = new PtCredentialVO();
			String userId = (String)req.getAttribute("userid");
			String password = (String)req.getAttribute("password");
			if(password == null)
				password = "";
			credentialVO.setUserid(userId);
			credentialVO.setPassword(password);
			String pkcorp = StringUtils.defaultIfEmpty((String)req.getAttribute("pkcorp"),"0001");
			// ����������Ϣ��ƾ֤Reference��
			String accountcode = (String)req.getAttribute("accountcode");
			credentialVO.getCredentialReference().setValue("accountcode", accountcode);
			credentialVO.getCredentialReference().setValue("pkcorp", pkcorp);
			credentialVO.getCredentialReference().setValue("language", (String)req.getAttribute("language"));
			
			// �û���Ϣ��֤
			verifyUserInfo(req, credentialVO, provider);
//			// ͨ�����ײ�������Դ
//			credentialVO.getCredentialReference().setValue("datasource", nc5xDs);
		
			
			String ncuserpk = verifyUserInfo(req, credentialVO, provider);
			// ͨ���û���Ϣ�����û���ID
			credentialVO.getCredentialReference().setValue("ncuserpk", ncuserpk);
			return credentialVO;
		} catch (LfwBusinessException e) {
			Logger.error(e, e);
			throw new CredentialValidateException( " ��������NCϵͳ��ƾ֤ʧ��:" + e.getMessage());
		}  
	}
	
	public ExtAuthField[] getCredentialFields(HttpServletRequest req, SSOProviderVO provider, IUserVO userVO, PtCredentialVO credential) {
	    Logger.debug("===NCLogin��getCredentialFields����:����NCLogin��getCredentialFields()����!");
	    Map<String, String> userInputMap = getUserInputValue(req);
	    
		//��ȡ�Ѿ����ڵ�credential
		//��������
		ExtAuthField[] fields = new ExtAuthField[5];
		fields[0] = new ComboExtAuthField("����", "accountcode", true);
		fields[1] = new RefExtAuthField("��˾", "pkcorp", false);
		fields[2] = new TextExtAuthField("�û�", "userid", true);
		fields[3] = new PasswordExtAuthField("����", "password", false);
		fields[4] = new ComboExtAuthField("����", "language", true);
		fields[0].setRequired(true);
		fields[2].setRequired(true);
		fields[3].setRequired(true);
		fields[4].setRequired(true);
		//fields[5].setRequired(false);
		
		String defaultAccount = initAccountCodeField(((ComboExtAuthField)fields[0]), userInputMap, provider, userVO, credential);
		initCorpField(((RefExtAuthField)fields[1]), defaultAccount, provider, credential, userInputMap);
		initUserAndPwdField(userInputMap, (TextExtAuthField)fields[2], (PasswordExtAuthField)fields[3], credential, userVO);
		initLanageField(userInputMap, (ComboExtAuthField)fields[4], credential);
		//initWorkdate((DateExtAuthField)fields[5], req);
		return fields;
	}
	 /**
     * ��ʼ���û���/������Ϣ������ԭ���û����� > ����ƾ֤ > ��ǰ��¼�û���Ϣ��NCUserProvider�������
     * 
     * @param userIdField
     * @param pwdField
     * @param credential
     * @param user
     * @param isLoginProvider
     */
    private void initUserAndPwdField(Map<String, String> userInputMap, TextExtAuthField userIdField, PasswordExtAuthField pwdField, PtCredentialVO credential, IUserVO user)
    {
    	String userId = !userInputMap.get("userid").equals(NULL_STRING) ? userInputMap.get("userid") : null;
    	String password = !userInputMap.get("password").equals(NULL_STRING) ? userInputMap.get("password") : null;
    	
    	if(userId == null && credential != null)
    		userId = credential.getUserid();
    	if(password == null && credential != null)
    		password = credential.getPassword();

    	Logger.debug("===NCLogin��initUserAndPwdField����:��ȡȱʡ�û���/����=" + userId + "/" + password);
    	
    	userIdField.setDefaultValue(userId);
    	pwdField.setDefaultValue(password);
    }
    /**
     * ��ʼ����˾����
     * ���ȼ����û������ > ������Ϣ
     * 
     * @param corpField
     */
    private void initCorpField(RefExtAuthField corpField, String defaultAccount, SSOProviderVO provider, PtCredentialVO credential, Map<String, String> userInputMap){
    	
    	Logger.debug("===NCLogin��initCorpField����:����NcLogin��initCorpField����,defaultAccount=" + defaultAccount);
    	corpField.setPageMeta("reference");
    	corpField.setRefmodel("nc.uap.portal.integrate.othersystem.nc5x.NC5xCropRefModel");
    	corpField.setDsloaderclass("nc.uap.portal.integrate.othersystem.nc5x.Nc5xCropRefDsListener");
    	corpField.setReadDs("masterDs");
    	corpField.setReadFields("pk_corp");
    	corpField.setPath("reference/reftree.jsp");
    	corpField.setReadDs("corpds");
    	corpField.setWriteFields("pkcorp");
			
		String pkcorp = !userInputMap.get("pkcorp").equals(NULL_STRING) ? userInputMap.get("pkcorp") : null;
		if(pkcorp == null && credential != null)
			pkcorp = credential.getCredentialReference().getValue("pkcorp");
		
		Logger.debug("===NCLogin��initCorpField����:��ȡpkcorp��Ĭ��ֵ=" + pkcorp);
		corpField.setDefaultValue(pkcorp);		
		
		String nc5xds = provider.getValue(Nc5xIntConstent.NC5XDs);
		LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(Nc5xIntConstent.NC5XDs, nc5xds);

    }
	
	/**
	 * ҳ����ʾ������ԭ������˳�� �� �û������>�û���¼ʱ��������ף�NCUserProvider��¼ϵͳʱ�� > ���е�credential�е�������Ϣ��
	 * ����û���sso-provider.xmlû�����õ�ǰʹ�õ����ף���ʹ�õ�ǰ������NCϵͳ��������Ч���ס�
	 * 
	 * @param accountField
	 * @param req
	 * @param provider
	 * @param userVO
	 * @param credential
	 * @return ���ص�ǰ���õ�Ĭ�����ף��������ù�˾����
	 */
    private String initAccountCodeField(ComboExtAuthField accountField, Map<String, String> userInputMap, SSOProviderVO provider, IUserVO userVO, PtCredentialVO credential)
    {
    	String defaultAccount = null;
      	 
		//String accountCode = !userInputMap.get("accountcode").equals(NULL_STRING) ? userInputMap.get("accountcode") : userVO == null ? null : (String)userVO.getUserReference("accountCode");
	    String accountCode = userInputMap.get("accountcode");
		if(accountCode == null || accountCode.trim().equals(""))
		{
			if(credential != null)
			{
				accountCode = credential.getCredentialReference().getValue("accountcode");
			}
		}
		String[] xmlAccountValues = provider.getValue("AccountCode").split(",");
		String[] xmlAccountNames = provider.getValue("AccountName") == null ? new String[]{} : provider.getValue("AccountName").split(",");
		Logger.debug("===NcLogin��initAccountCodeField����:��provider�л�ȡ���õ��������ױ������=" + xmlAccountValues.length);
	
		// ���sso-provider.xml��û������AccountCode���ԣ���ʹ�ñ�����NC����������
		Map<String, String> allAccounts = new HashMap<String, String>();
 
		if(xmlAccountValues == null || xmlAccountValues.length == 0)
			xmlAccountValues = allAccounts.keySet().toArray(new String[0]);
		
		if (xmlAccountValues != null) {
			int selected = -1;
			String[][] options = new String[xmlAccountValues.length][2];
			for (int i = 0; i < xmlAccountValues.length; i++) {
				options[i][0] = xmlAccountValues[i];
				options[i][1] = xmlAccountNames.length > i ? xmlAccountNames[i] : xmlAccountValues[i];
				if (xmlAccountValues[i] != null && xmlAccountValues[i].equals(accountCode)) {
					selected = i;
				}
			}
			if(selected == -1)
				selected = 0;
			accountField.setOptions(options);
			accountField.setSelectedIndex(selected);
			
			defaultAccount = xmlAccountValues[selected];
	    }
		
		Logger.debug("===NcLogin��initAccountCodeField����:��ȡĬ������=" + defaultAccount);
		return defaultAccount;
    }
	/**
	 * �����û�������Ϣ����ʱ�ٽ����ȡ��֤�򷽷�ʱͨ��
	 * �˷�����ȡ�û�֮ǰ�������Ϣ��
	 * 
	 * @param req
	 * @return
	 */
	private Map<String, String> getUserInputValue(HttpServletRequest req)
	{
		Map<String, String> map = new HashMap<String, String>();
		String accountcode = req.getParameter("accountcode") == null ? NULL_STRING : req.getParameter("accountcode");
		String pkcorp = req.getParameter("pkcorp") == null ? NULL_STRING : req.getParameter("pkcorp");
		String username = req.getParameter("userid") == null ? NULL_STRING : req.getParameter("userid");
		String password = req.getParameter("password") == null ? NULL_STRING : req.getParameter("password");
		String language = req.getParameter("language") == null ? NULL_STRING : req.getParameter("language");
		
		map.put("accountcode", accountcode);
		map.put("pkcorp", pkcorp);
		map.put("userid", username);
		map.put("password", password);
		map.put("language", language);
		return map;
	}
	
 
	@Override
	public String getGateUrl(HttpServletRequest req, HttpServletResponse res,
			PtCredentialVO credential, SSOProviderVO provider)
			throws CredentialValidateException {
		try {
			String gateUrl = provider.getGateUrl();
			String ncUrl = provider.getValue("runtimeUrl");
			
			Logger.debug("===NCLogin��getGateUrl����:��ȡԭʼgateUrl=" + gateUrl);
			Logger.debug("===NCLogin��getGateUrl����:��ȡNC��runtimeUrl=" + ncUrl);
			
			// ��ƾ֤�л�õ�¼��Ϣ
			String usercode = credential.getUserid();
			String password = credential.getPassword();
			String accountcode = credential.getCredentialReference().getValue("accountcode");
			String pkcorp = credential.getCredentialReference().getValue("pkcorp");
			String language = credential.getCredentialReference().getValue("language");
			String key = req.getSession().getId();
			StringBuffer parameters = new StringBuffer("key=" + key);
			if(pkcorp != null && pkcorp.trim().length() != 0)
			    parameters.append("&pkcorp=" + pkcorp);
			else
				parameters.append("&pkcorp=0001");
			
			UFDate tick = new UFDate(new Date().getTime());
			String workdate = req.getParameter("workdate");
//			if(workdate == null || "".equals(workdate))
//				workdate = tick.toLocalString();
			workdate = "2011-10-11";
			parameters.append("&accountcode=" + accountcode);
			parameters.append("&workdate=" + workdate);
			parameters.append("&language=" + language);
			// ���û����ƺ��������URL����,ȷ�������ַ��ܹ�ͨ��
			parameters.append("&usercode=" + URLEncoder.encode(usercode, "UTF-8"));
			parameters.append("&pwd=" + URLEncoder.encode(password, "UTF-8"));
			
			Logger.debug("===NCLogin��getGateUrl����:��ȡ��credential��Ϣ,usercode=" + usercode + ";accountcode=" + accountcode
					+ ";pkcorp=" + pkcorp + ";language=" + language + ";key=" + key);
			// �û���Ϣ��֤
			verifyUserInfo(req, credential, provider);
			// NC��½��Ϣע��
			Logger.debug("===NCLogin��getGateUrl����:���ɵ�ע�������Ϣ=" + parameters.toString());
			String registeResult = ncRegiste(parameters.toString(), provider.getValue("registryUrl"));
			if(registeResult != null && registeResult.startsWith("Error"))
				throw new PortletLoginException(registeResult);
			
			// ����NCϵͳ
			int clientWidth = 2048;
			int clientHeight = 1436;
			String screenWidth,screenHeight;
			screenWidth = (String)req.getAttribute("screenWidth");
			screenHeight = (String)req.getAttribute("screenHeight");
			if(screenWidth != null && !screenWidth.trim().equals(""))
				clientWidth = Integer.parseInt(screenWidth);
			if(screenHeight != null && !screenHeight.trim().equals(""))
				clientHeight = Integer.parseInt(screenHeight);
			
			gateUrl += "?key=" + key + "&clienttype=portal&width=" + clientWidth + "&height=" + clientHeight;
			Logger.debug("===NCLogin��getGateUrl����:��ȡ���յĵ�¼NC��gateUrl=" + gateUrl);
			return gateUrl;
		} catch (IOException e) {
			Logger.error("===NCLogin��getGateUrl����:��¼��������,�����ɷ�����û���������������Ӳ�ͨ!",e); 
			throw new CredentialValidateException("��¼NC��������,�����ɷ�����û�����������������޷��ﵽ!");
		} catch (Exception e) {
			Logger.error(e, e);
			throw new CredentialValidateException("У��NC��¼ʧ��:" + e.getMessage());
		}
	}

	@Override
	public String getNodeGateUrl(HttpServletRequest req,
			HttpServletResponse res, String nodeId, PtCredentialVO credential,
			SSOProviderVO providerVO) throws CredentialValidateException {
		return LfwRuntimeEnvironment.getRootPath() + "/pt/nc5x/fwd?funcode=" + nodeId + "&systemcode=" + providerVO.getSystemCode();
	}

	@Override
	public String verifyUserInfo(HttpServletRequest req, PtCredentialVO credentialVO, SSOProviderVO providerVO)
			throws CredentialValidateException {
		String oldDs = LfwRuntimeEnvironment.getDatasource();
		String nc5xDs = providerVO.getValue(Nc5xIntConstent.NC5XDs);
		NC5xUserVO ncUser = null;
		String userId = credentialVO.getUserid();
		String password = credentialVO.getPassword();
		try {
			InvocationInfoProxy.getInstance().setUserDataSource(nc5xDs);
			
			NC5xUserVO[] vos = CRUDHelper.getCRUDService().queryVOs("user_code='"+userId+"'", NC5xUserVO.class, null, null);
			if(vos != null && vos.length > 0)
				ncUser = vos[0];
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
		}finally{
			InvocationInfoProxy.getInstance().setUserDataSource(oldDs);
		}
		if(ncUser == null){
			throw new CredentialValidateException("�û������ڣ�");
		}
		if(ncUser.getLocked_tag().booleanValue()){
			throw new CredentialValidateException("�û���������");
		}
		try {
			if(!StringUtils.equals(PortalServiceUtil.getEncodeService().encode(password),ncUser.getUser_password()))
				throw new CredentialValidateException("���벻��ȷ��");
		} catch (BusinessException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new CredentialValidateException("��������NCϵͳ��ƾ֤ʧ��");
		}
		return ncUser.getPrimaryKey();
	}
    /**
     * ��ʼ����������ѡ��
     * 
     * @param userInputMap
     * @param languageField
     * @param credential
     */
    private void initLanageField(Map<String, String> userInputMap, ComboExtAuthField languageField, PtCredentialVO credential)
    {
	    String lanaguage = "simpchn";
	    if(!userInputMap.get("language").equals(NULL_STRING))
	    	lanaguage = userInputMap.get("language");
	    
	    if(userInputMap.get("language").equals(NULL_STRING) && credential != null)
	    	lanaguage = credential.getCredentialReference().getValue("language");
	    
		String[][] options = new String[3][2];
		options[0][0] = "simpchn";
		options[0][1] = "��������";
		options[1][0] = "tradchn";
		options[1][1] = "���w����";
		options[2][0] = "english";
		options[2][1] = "English";
		int selected = 0;
		for (int i = 0; i < options.length; i++) {
			if (options[i][0].equals(lanaguage)) {
				selected = i;
			}
		}
		languageField.setOptions(options);
		languageField.setSelectedIndex(selected);
    }	
	/**
	 * ��NCϵͳע���¼ƾ֤
	 */
	private String ncRegiste(String parameters, String registrUrl)
			throws IOException {
		
		Logger.debug("===NCLogin��ncRegiste����:NC registry URL:" + registrUrl);
		Logger.debug("===NCLogin��ncRegiste����:NC registry parameters:" + parameters);
		
		// ����NCע��URL
		URL preUrl = new URL(registrUrl);
		URLConnection uc = preUrl.openConnection();
		// ����������������/ֵ�������������������Դ
		uc.setDoOutput(true);
		// ����ֻ�ܷ������õ���Ϣ
		uc.setUseCaches(false);
		// ����Content-Typeͷ��ָʾָ��URL�ѱ������ݵĴ���MIME����
		uc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// ����Content-Typeͷ��ָʾָ��URL�ѱ������ݵĴ���MIME����
		uc.setRequestProperty("Content-Length", "" + parameters.length());
		// ��ȡ���ӵ��ʵ�������
		HttpURLConnection hc = (HttpURLConnection) uc;
		// ��HTTP���󷽷�����ΪPOST��Ĭ�ϵ���GET��
		hc.setRequestMethod("POST");
		// �������
		OutputStream os = hc.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeBytes(parameters);
		dos.flush();
		dos.close();
		
		// ��ȡNC��ƾ֤����֤���
		InputStream is = hc.getInputStream();
		String returnFlag = "";
		int ch;
		while ((ch = is.read()) != -1) {
			returnFlag += String.valueOf((char) ch);
		}
		Logger.debug("===NCLogin��ncRegiste����:NC Registe result=" + returnFlag);
		if (is != null)
			is.close();
		return returnFlag;
	}

	@Override
	public SsoSystemNode[] getAllFunNodes(SSOProviderVO provider) {
		String oldDs = LfwRuntimeEnvironment.getDatasource();
		String nc5xDs = provider.getValue(Nc5xIntConstent.NC5XDs);
		try {
			InvocationInfoProxy.getInstance().setUserDataSource(nc5xDs);
			List<SsoSystemNode> nodes = new ArrayList<SsoSystemNode>();

			FuncRegisterVO[] vos = CRUDHelper.getCRUDService().queryVOs(" isnull(isenable, 'Y')='Y'", FuncRegisterVO.class, null, null);
			if(vos != null && vos.length > 0){
				for(FuncRegisterVO regVO:vos)
					nodes.add(funcRegister2SystemNode(regVO));
				return nodes.toArray(new SsoSystemNode[0]);
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
		}finally{
			InvocationInfoProxy.getInstance().setUserDataSource(oldDs);
		}
		return null;
	}
	/**
	 * ��NC��ݷ�ʽת��ΪPortal���ɽڵ�
	 * @param regVO
	 * @return
	 */
	private SsoSystemNode shortcut2SystemNode(CustomMenuShortcutVO regVO){
		SsoSystemNode node = new SsoSystemNode();
		node.setNodeCode(regVO.getFuncId());
		node.setNodeName(regVO.getIconText());
		node.setNodeId(regVO.getPrimaryKey());
		return node;
	}
	/**
	 * NC���ܽڵ�ת��ΪPortal���ɽڵ�
	 * @param regVO
	 * @return
	 */
	private SsoSystemNode funcRegister2SystemNode(FuncRegisterVO regVO){
		SsoSystemNode node = new SsoSystemNode();
		node.setNodeCode(regVO.getFuncode());
		node.setNodeName(regVO.getFun_name());
		node.setNodeId(regVO.getPrimaryKey());
		if(regVO.getFun_property() != null && regVO.getFun_property().equals(FunRegisterConst.LFW_FUNC_NODE)){
			node.setNodeType(0);
		}else{
			node.setNodeType(1);
		}
		return node;
	}
	
	@Override
	public SsoSystemNode[] getUserNodes(SSOProviderVO provider,
			PtCredentialVO credential) throws CredentialValidateException {
		String cuserid =credential.getCredentialReference().getValue("ncuserpk");
		//
		String oldDs = LfwRuntimeEnvironment.getDatasource();
		String nc5xDs = provider.getValue(Nc5xIntConstent.NC5XDs);
		try {
			InvocationInfoProxy.getInstance().setUserDataSource(nc5xDs);
			List<SsoSystemNode> nodes=new ArrayList<SsoSystemNode>();

			//CustomMenuShortcutVO[] vos = 
			List<CustomMenuShortcutVO> list = (List<CustomMenuShortcutVO>)CRUDHelper.getCRUDService().query("select cCustomMenuShortcutID, userId, fun_code,  iconText, iconId from sm_custom_menu_shortcut ,sm_funcregister where  funcid = cfunid and  userId='"+ cuserid +"'",  new ArrayListProcessor() {
					private static final long serialVersionUID = -7977064364672208452L;

					public Object processResultSet(ResultSet rs) throws SQLException {
						List<CustomMenuShortcutVO> list = new ArrayList<CustomMenuShortcutVO>();
						while (rs.next()) {
							CustomMenuShortcutVO customMenuShortcut = new CustomMenuShortcutVO();
							//
							String cCustomMenuShortcutID = rs.getString(1);
							customMenuShortcut.setCCustomMenuShortcutID(cCustomMenuShortcutID == null ? null : cCustomMenuShortcutID.trim());
							//
							String userId = rs.getString(2);
							customMenuShortcut.setUserId(userId == null ? null : userId.trim());
							//
							String funcId = rs.getString(3);
							customMenuShortcut.setFuncId(funcId == null ? null : funcId.trim());
							//
							String iconText = rs.getString(4);
							customMenuShortcut.setIconText(iconText == null ? null : iconText.trim());
							//
							String iconId = rs.getString(5);
							customMenuShortcut.setIconPath(iconId == null ? null : iconId.trim());
							//
							if(iconText == null || iconText.trim().length() <=0){
								String funcode = funcId;
								String funName = NCLangResOnserver.getInstance().getStrByID(IProductCode.PRODUCTCODE_FUNCODE, "D"+funcode.trim());
								customMenuShortcut.setIconText(funName);
							}
							list.add(customMenuShortcut);
						}
						return list;
					}
				});

			if(ToolKit.notNull(list)){
				for(CustomMenuShortcutVO regVO : list){
					nodes.add(shortcut2SystemNode(regVO));
				}
				return nodes.toArray(new SsoSystemNode[0]);
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
		}finally{
			InvocationInfoProxy.getInstance().setUserDataSource(oldDs);
		}
		
		return null;
	}
}
