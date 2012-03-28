package nc.uap.portal.integrate.othersystem.nc;
 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bcmanage.bs.IBusiCenterManageService;
import nc.bcmanage.vo.BusiCenterVO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.bbd.func.IFuncRegisterQueryService;
import nc.itf.uap.sf.ICustomMenuQueryService;
import nc.login.bs.INCLoginService;
import nc.login.bs.INCUserQueryService;
import nc.login.bs.LoginVerifyBean;
import nc.login.vo.ILoginConstants;
import nc.login.vo.ISystemIDConstants;
import nc.login.vo.LoginRequest;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.authfield.ComboExtAuthField;
import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.lfw.login.authfield.PasswordExtAuthField;
import nc.uap.lfw.login.authfield.TextExtAuthField;
import nc.uap.portal.integrate.IWebAppFunNodesService;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.exception.PortletLoginException;
import nc.uap.portal.integrate.funnode.SsoSystemNode;
import nc.uap.portal.integrate.system.PortletRuntimeEnv;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.sm.UserVO;
import nc.vo.sm.cmenu.CustomMenuShortcutVO;
import nc.vo.sm.funcreg.FunRegisterConst;
import nc.vo.sm.funcreg.FuncRegisterVO;
import nc.vo.sm.login.LoginFailureInfo;

import org.apache.commons.lang.StringUtils;


/**
 * @author yzy
 * @update lkp ����lfw�ĵ�¼��ʽ
 * @author gd 2008-07-02 ϸ������ṹ,������ϸ����־��¼
 * @version NC5.6
 * @since NC5.0 
 */
public class NcLogin implements IWebAppLoginService ,IWebAppFunNodesService{
	  
	private static final String NULL_STRING = "NULL";
	public static Map<Integer,String> LOGIN_RSL_MAP  ;
	static{
		LOGIN_RSL_MAP = new HashMap<Integer,String>();
		LOGIN_RSL_MAP.put(0, "��¼�ɹ�");
		LOGIN_RSL_MAP.put(1, "��ݲ��Ϸ�");
		LOGIN_RSL_MAP.put(2, "���ƴ���");
		LOGIN_RSL_MAP.put(3, "�������");
		LOGIN_RSL_MAP.put(4, "�û�������");
		LOGIN_RSL_MAP.put(5, "�û�������");
		LOGIN_RSL_MAP.put(6, "�û�δ����Ч����");
		LOGIN_RSL_MAP.put(7, "�û��ѵ�ʧЧ����");
		LOGIN_RSL_MAP.put(8, "�ﵽ�û�������");
		LOGIN_RSL_MAP.put(9, "�û�δ����");
		LOGIN_RSL_MAP.put(10, "�û���ͣ��");
		
		LOGIN_RSL_MAP.put(21, "ҵ��������Ч");
		LOGIN_RSL_MAP.put(22, "ҵ�����ı�����");
		LOGIN_RSL_MAP.put(23, "ҵ�����Ļ�δ����Ч����");
		LOGIN_RSL_MAP.put(24, "ҵ�������ѵ�ʧЧ����");
		LOGIN_RSL_MAP.put(25, "ҵ�����Ĳ�����");
	}
	
	public String getGateUrl(HttpServletRequest req, HttpServletResponse res, PtCredentialVO credential, SSOProviderVO provider)
			throws CredentialValidateException {
		try {
			String gateUrl = provider.getGateUrl();
			String ncUrl = provider.getValue("runtimeUrl");
			
			Logger.debug("===NCLogin��getGateUrl����:��ȡԭʼgateUrl=" + gateUrl);
			Logger.debug("===NCLogin��getGateUrl����:��ȡNC��runtimeUrl=" + ncUrl);
			
			// ��ƾ֤�л�õ�¼��Ϣ
			String usercode = credential.getUserid();
			String bc = credential.getCredentialReference().getValue("accountcode");
			String pkcorp = credential.getCredentialReference().getValue("pkcorp");
			String language = credential.getCredentialReference().getValue("language");
			String key = req.getSession().getId();
			StringBuffer parameters = new StringBuffer("ssoKey=" + key);
			parameters.append("&busiCenter=" + bc);
 			// ���û����ƺ��������URL����,ȷ�������ַ��ܹ�ͨ��
			parameters.append("&userCode=" + URLEncoder.encode(usercode, "UTF-8"));
			parameters.append("&langCode=" + language);
			
			Logger.debug("===NCLogin��getGateUrl����:��ȡ��credential��Ϣ,usercode=" + usercode + ";accountcode=" + bc
					+ ";pkcorp=" + pkcorp + ";language=" + language + ";key=" + key);
			// �û���Ϣ��֤
			verifyUserInfo(req, credential, provider);
			// NC��½��Ϣע��
			Logger.debug("===NCLogin��getGateUrl����:���ɵ�ע�������Ϣ=" + parameters.toString());
			String registeResult = ncRegiste(parameters.toString(), provider.getValue("registryUrl"));
			if(registeResult != null && registeResult.startsWith("Error"))
				throw new PortletLoginException(registeResult);
			
			// ����NCϵͳ
			String screenWidth,screenHeight;
			boolean hasSize = false;
			screenWidth = (String)req.getAttribute("screenWidth");
			screenHeight = (String)req.getAttribute("screenHeight");
			gateUrl += "?ssoKey=" + key + "&clienttype=portal";
			if(screenWidth != null && !screenWidth.trim().equals("")){
				gateUrl = gateUrl + "&width=" + screenWidth;
				hasSize = true;
			}
			if(screenHeight != null && !screenHeight.trim().equals("")){
				gateUrl = gateUrl + "&height=" + screenHeight;
				hasSize = true;
			}
			
			Logger.debug("===NCLogin��getGateUrl����:��ȡ���յĵ�¼NC��gateUrl=" + gateUrl);
			if(hasSize)
				return gateUrl;
			
			return  LfwRuntimeEnvironment.getRootPath() + "/html/frame/nc.jsp?fwd=" + URLEncoder.encode(gateUrl, "UTF-8");
			
		} catch (IOException e) {
			Logger.error("===NCLogin��getGateUrl����:��¼��������,�����ɷ�����û���������������Ӳ�ͨ!",e); 
			throw new CredentialValidateException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "NcLogin-000000")/*��¼NC��������,�����ɷ�����û�����������������޷��ﵽ!*/);
		} catch (BusinessException e) {
			Logger.error(e, e);
			throw new CredentialValidateException("У��NC��¼ʧ��:" + e.getMessage());
		}
	}

	public PtCredentialVO credentialProcess(HttpServletRequest req, SSOProviderVO provider)
			throws CredentialValidateException {
		try {
			// ����ƾ֤VO
			PtCredentialVO credentialVO = new PtCredentialVO();
			String userId = (String)req.getAttribute("userid");
			String password =  (String)req.getAttribute("password");
			if(password == null)
				password = "";
			credentialVO.setUserid(userId);
			credentialVO.setPassword(password);
			// ����������Ϣ��ƾ֤Reference��
			String accountcode =  (String)req.getAttribute("accountcode");
			credentialVO.getCredentialReference().setValue("accountcode", accountcode);
			credentialVO.getCredentialReference().setValue("pkcorp", (String)req.getAttribute("pkcorp"));
			credentialVO.getCredentialReference().setValue("language", (String)req.getAttribute("language"));
			
			String ncUrl = provider.getValue("runtimeUrl");
			NCLocator locator = NCLocator.getInstance(PortletRuntimeEnv.getInstance().getNcProperties(ncUrl));
			
			// �û���Ϣ��֤
			verifyUserInfo(req, credentialVO, provider);
			// ͨ�����ײ�������Դ
			IBusiCenterManageService service = (IBusiCenterManageService)locator.lookup(IBusiCenterManageService.class.getName());
			BusiCenterVO bcVO = service.getBusiCenterByCode(accountcode);
			String dataSource = bcVO.getDataSourceName();
			
			credentialVO.getCredentialReference().setValue("datasource", dataSource);
			INCUserQueryService ncuqs = (INCUserQueryService)locator.lookup(INCUserQueryService.class.getName());
			
			// ͨ���û���Ϣ�����û���ID
			UserVO ncUser = ncuqs.findUserVO(dataSource, userId);
			credentialVO.getCredentialReference().setValue("ncgrouppk", ncUser.getPk_group());
			credentialVO.getCredentialReference().setValue("ncuserpk", ncUser.getPrimaryKey());
			return credentialVO;
		} catch (BusinessException e) {
			Logger.error(e, e);
			throw new CredentialValidateException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "NcLogin-000002", null, new String[]{e.getMessage()})/*��������NCϵͳ��ƾ֤ʧ��:{0}*/, e);
		}
	}
	
	public String verifyUserInfo(HttpServletRequest req, PtCredentialVO credentialVO, SSOProviderVO providerVO) throws CredentialValidateException
	{
		
		 
		// У��
		String ncUrl = providerVO.getValue("runtimeUrl");
		NCLocator locator = NCLocator.getInstance(PortletRuntimeEnv.getInstance().getNcProperties(ncUrl));
		String language = credentialVO.getCredentialReference().getValue("language");
		String accountcode = credentialVO.getCredentialReference().getValue("accountcode");
		String pkcorp = credentialVO.getCredentialReference().getValue("pkcorp");
		String userId = credentialVO.getUserid();
		String password = credentialVO.getPassword();
		String workdate = req.getParameter("workdate");
		UFDate tick = new UFDate(new Date().getTime());
		INCLoginService loginService = (INCLoginService)locator.lookup(INCLoginService.class.getName());
		LoginRequest loginReq = new LoginRequest();
		loginReq.setBusiCenterCode(accountcode);
		loginReq.setLangCode(language);
		loginReq.setUserCode(userId);
		loginReq.setUserPWD(password);
		
		LoginVerifyBean verifyBean = new LoginVerifyBean(ISystemIDConstants.NCSYSTEM);
		verifyBean.setStaticPWDVerify(true);
		
		if(workdate == null || "".equals(workdate))
			workdate = tick.toString();
		try {
			int verifyResult = loginService.verify(loginReq, verifyBean);
			if (verifyResult != ILoginConstants.USER_IDENTITY_LEGAL) {
				if(LOGIN_RSL_MAP.containsKey(verifyResult))
					throw new CredentialValidateException(LOGIN_RSL_MAP.get(verifyResult));
				else
					throw new CredentialValidateException("δ֪����");
			}
		} catch (BusinessException e) {
			Logger.error(e, e);
			throw new CredentialValidateException(e.getMessage());
		}
		return null;
	}

	/**
	 * ��NCϵͳע���¼ƾ֤
	 */
	private String ncRegiste(String parameters, String registrUrl)
			throws IOException {
		
		
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
		if (is != null)
			is.close();
		return returnFlag;
	}

	/**
	 * �û����Բ���sso-provider.xml������AccountCode��Ϣ,���������,����õ�ǰ������
	 * NC������������Ϣ
	 */
	public   ExtAuthField[] getCredentialFields(HttpServletRequest req, SSOProviderVO provider, nc.uap.portal.user.entity.IUserVO userVO, PtCredentialVO credential) throws CredentialValidateException {
	    Map<String, String> userInputMap = getUserInputValue(req);
	    
		//��ȡ�Ѿ����ڵ�credential
		//��������
		ExtAuthField[] fields = new ExtAuthField[4];
		fields[0] = new ComboExtAuthField("ϵͳ", "accountcode", true);
		fields[1] = new TextExtAuthField("�û�", "userid", true);
		fields[2] = new PasswordExtAuthField("����", "password", false);
		fields[3] = new ComboExtAuthField("����", "language", true);
		//fields[5] = new DateExtAuthField("����", "workdate", true); 
		fields[0].setRequired(true);
		fields[1].setRequired(true);
		fields[2].setRequired(true);
		fields[3].setRequired(true);
		
		initAccountCodeField(((ComboExtAuthField)fields[0]), userInputMap, provider, userVO, credential);
		initLanageField(userInputMap, (ComboExtAuthField)fields[3], credential);
		return fields;
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
    private String initAccountCodeField(ComboExtAuthField accountField, Map<String, String> userInputMap, SSOProviderVO provider, nc.uap.portal.user.entity.IUserVO userVO, PtCredentialVO credential)
    {
    	String defaultAccount = null;
	    	 
	    String runtimeUrl = provider.getValue("runtimeUrl");
		//String accountCode = !userInputMap.get("accountcode").equals(NULL_STRING) ? userInputMap.get("accountcode") : userVO == null ? null : (String)userVO.getUserReference("accountCode");
	    String accountCode = userInputMap.get("accountcode");
		if(accountCode == null || accountCode.trim().equals(""))
		{
			if(credential != null)
			{
				accountCode = credential.getCredentialReference().getValue("accountcode");
			}
		}
		
		String accountStr = provider.getValue("AccountCode");
		if(accountStr == null || accountStr.equals(""))
			return null;
		
		// ��ȡsso-provider.xml�����õ�
		String[] xmlAccountValues = accountStr.split(",");
		Logger.debug("===NcLogin��initAccountCodeField����:��provider�л�ȡ���õ��������ױ������=" + xmlAccountValues.length);
	
		// ���sso-provider.xml��û������AccountCode���ԣ���ʹ�ñ�����NC����������
		Map<String, String> allAccounts = new HashMap<String, String>();
		BusiCenterVO[] ncAccounts =  null;
		try {
			IBusiCenterManageService accountService = (IBusiCenterManageService)NCLocator.getInstance(
	                   								PortletRuntimeEnv.getInstance().getNcProperties(runtimeUrl)).lookup(IBusiCenterManageService.class.getName());
		    ncAccounts = accountService.getBusiCenterVOs();
		}
		catch(Exception e) {
		    Logger.error("===NcLogin��initAccountCodeField����:��ȡ����������Ϣʱ����," + e.getMessage());	
		}
		
		if(ncAccounts != null)
		{
			for(int i = 0;i < ncAccounts.length; i++)
			   allAccounts.put(ncAccounts[i].getCode(), ncAccounts[i].getName());
		}
		
		if(xmlAccountValues == null || xmlAccountValues.length == 0)
			xmlAccountValues = allAccounts.keySet().toArray(new String[0]);
		
		if (xmlAccountValues != null) {
			int selected = -1;
			String[][] options = new String[xmlAccountValues.length][2];
			for (int i = 0; i < xmlAccountValues.length; i++) {
				options[i][0] = xmlAccountValues[i];
				options[i][1] = allAccounts.containsKey(xmlAccountValues[i]) ? allAccounts.get(xmlAccountValues[i]) : xmlAccountValues[i];
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
    
//    /**
//     * ��ʼ����˾����
//     * ���ȼ����û������ > ������Ϣ
//     * 
//     * @param corpField
//     */
//    private void initCorpField(RefExtAuthField corpField, String defaultAccount, SSOProviderVO provider, PtCredentialVO credential, Map<String, String> userInputMap){
//    	
//    	Logger.debug("===NCLogin��initCorpField����:����NcLogin��initCorpField����,defaultAccount=" + defaultAccount);
//    	corpField.setPageMeta("reference.portalnccorp.pagemeta");
//    	corpField.setPath("reference/ncCorpRefTree.jsp");
//    	corpField.setReadDs("corpds");
//    	corpField.setReadFields("pk_corp");
//    	corpField.setWriteFields("pkcorp");
//		 
//    	try	{
//	    		String runtimeUrl = provider.getValue("runtimeUrl");
//				Logger.debug("===NCLogin��initCorpField����:���runtimeUrl=" + runtimeUrl);
//				   
//				IConfigFileService accountService = (IConfigFileService)NCLocator.getInstance(
//			                   PortletRuntimeEnv.getInstance().getNcProperties(runtimeUrl)).lookup(IConfigFileService.class.getName());
//				Logger.debug("===NCLogin��initCorpField����:�ɹ����IConfigFileService�ķ���!" );
//				   
//				String defaultDs = accountService.getAccountByCode(defaultAccount).getDataSourceName();   
//				Logger.debug("===NCLogin��initCorpField����:��������" + defaultAccount + "���Ĭ������Դ=" + defaultDs);
//				   
//				// ���ù�˾���յ�ȱʡ����Դ
//				corpField.setDatasource(defaultDs);
//			}
//    		catch(Exception e) {
//				Logger.error("===NCLogin��initCorpField����:��ȡĬ������Դʱ���ִ���,��Ӱ��ʹ��," + e.getMessage(), e);
//			}
//			
//			String pkcorp = !userInputMap.get("pkcorp").equals(NULL_STRING) ? userInputMap.get("pkcorp") : null;
//			if(pkcorp == null && credential != null)
//				pkcorp = credential.getCredentialReference().getValue("pkcorp");
//			
//			Logger.debug("===NCLogin��initCorpField����:��ȡpkcorp��Ĭ��ֵ=" + pkcorp);
//			corpField.setDefaultValue(pkcorp);		
//    }

//    /**
//     * ��ʼ���û���/������Ϣ������ԭ���û����� > ����ƾ֤ > ��ǰ��¼�û���Ϣ��NCUserProvider�������
//     * 
//     * @param userIdField
//     * @param pwdField
//     * @param credential
//     * @param user
//     * @param isLoginProvider
//     */
//    private void initUserAndPwdField(Map<String, String> userInputMap, TextExtAuthField userIdField, PasswordExtAuthField pwdField, PtCredentialVO credential, PtUserVO user)
//    {
//    	String userId = !userInputMap.get("userid").equals(NULL_STRING) ? userInputMap.get("userid") : null;
//    	String password = !userInputMap.get("password").equals(NULL_STRING) ? userInputMap.get("password") : null;
//    	
//    	if(userId == null && credential != null)
//    		userId = credential.getUserid();
//    	if(password == null && credential != null)
//    		password = credential.getPassword();
//
//    	Logger.debug("===NCLogin��initUserAndPwdField����:��ȡȱʡ�û���/����=" + userId + "/" + password);
//    	
//    	userIdField.setDefaultValue(userId);
//    	pwdField.setDefaultValue(password);
//    }

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
//	    if(!userInputMap.get("language").equals(NULL_STRING))
//	    	lanaguage = userInputMap.get("language");
	    
//	    if(userInputMap.get("language").equals(NULL_STRING) && credential != null)
	    if(credential != null)
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

	public String getNodeGateUrl(HttpServletRequest req,
			HttpServletResponse res, String nodeId, PtCredentialVO credential,
			SSOProviderVO provider) throws CredentialValidateException {
		
		String gateUrl = provider.getGateUrl();
		String ncUrl = provider.getValue("runtimeUrl");
		
		Logger.debug("===NCLogin��getGateUrl����:��ȡԭʼgateUrl=" + gateUrl);
		Logger.debug("===NCLogin��getGateUrl����:��ȡNC��runtimeUrl=" + ncUrl);
		
		// ��ƾ֤�л�õ�¼��Ϣ
		String usercode = credential.getUserid();
		String accountcode = credential.getCredentialReference().getValue("accountcode");
		String pkcorp = credential.getCredentialReference().getValue("pkcorp");
		String language = credential.getCredentialReference().getValue("language");
		String key = req.getSession().getId();
		StringBuffer parameters = new StringBuffer("ssoKey=" + key);
		//?userCode=wb&busiCenter=ptagg
		parameters.append("&busiCenter=" + accountcode);
			// ���û����ƺ��������URL����,ȷ�������ַ��ܹ�ͨ��
		try {
			parameters.append("&userCode=" + URLEncoder.encode(usercode, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		Logger.debug("===NCLogin��getGateUrl����:��ȡ��credential��Ϣ,usercode=" + usercode + ";accountcode=" + accountcode
				+ ";pkcorp=" + pkcorp + ";language=" + language + ";key=" + key);
		// �û���Ϣ��֤
		verifyUserInfo(req, credential, provider);
		// NC��½��Ϣע��
		Logger.debug("===NCLogin��getGateUrl����:���ɵ�ע�������Ϣ=" + parameters.toString());
		String registeResult = null;
		try {
			registeResult = ncRegiste(parameters.toString(), provider.getValue("registryUrl"));
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		if(registeResult != null && registeResult.startsWith("Error"))
			throw new CredentialValidateException(registeResult);
		
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
		
		gateUrl += "?ssoKey=" + key + "&clienttype=portal&width=" + clientWidth + "&height=" + clientHeight;
		gateUrl += "&uiloader=nc.uap.lfw.applet.NodeUILoader&nodeid=" + nodeId;
		Logger.debug("===NCLogin��getNodeGateUrl����:��ȡ���յĵ�¼NC��gateUrl=" + gateUrl);
		return gateUrl;
	}

	@Override
	public SsoSystemNode[] getAllFunNodes(SSOProviderVO provider){
		NCLocator locator = getLocator(provider);
		String currentDs = LfwRuntimeEnvironment.getDatasource();
		String[][] bcs = getAliveDataSourceName(provider);
		IFuncRegisterQueryService frq = (IFuncRegisterQueryService)locator.lookup(IFuncRegisterQueryService.class.getName());
		List<SsoSystemNode> nodes = new ArrayList<SsoSystemNode>();
		
		if(bcs != null){
			try {
				for(String[] rmtDs : bcs){
					InvocationInfoProxy.getInstance().setUserDataSource(rmtDs[0]);
					try {
						FuncRegisterVO[] vos = frq.queryAllNCFunction(false);
						if(vos != null && vos.length > 0)
							for(FuncRegisterVO regVO:vos){
								SsoSystemNode node = funcRegister2SystemNode(regVO);
								node.setNodeName("[���ף�" + rmtDs[1] + "]" + node.getNodeName());
								nodes.add(node);
							}
					} catch (Exception e) {
						LfwLogger.error(e.getMessage(),e);
					}
				}
			} catch (Exception e) {
				LfwLogger.error(this.getClass().getName()+"#getAllFunNodes===���NCȫ�����ܽڵ��쳣:"+e.getMessage() , e);
			}finally{
				InvocationInfoProxy.getInstance().setUserDataSource(currentDs);
			}
			
		}
		return nodes.toArray(new SsoSystemNode[0]);
	}

	@Override
	public SsoSystemNode[] getUserNodes(SSOProviderVO provider,PtCredentialVO credential) {
		String cuserid =credential.getCredentialReference().getValue("ncuserpk");
	    String currentDs = LfwRuntimeEnvironment.getDatasource();
	    NCLocator locator = getLocator(provider);
	    /**
	     * ҵ������
	     */
	    String bc = credential.getCredentialReference().getValue("accountcode");
		try {
			/**
			 * ���NC������Դ
			 */
			String rmtDs = locator.lookup(IBusiCenterManageService.class).getBusiCenterByCode(bc).getDataSourceName();
			InvocationInfoProxy.getInstance().setUserDataSource(rmtDs);
			ICustomMenuQueryService cmqs = locator.lookup(ICustomMenuQueryService.class);
			/**
			 * ��ѯ����
			 */
			CustomMenuShortcutVO conditionVO = new CustomMenuShortcutVO();
			conditionVO.setUserId(cuserid);
			CustomMenuShortcutVO[]  shortcuts = cmqs.queryShortcutsByVO(conditionVO, new Boolean(true));
			if(shortcuts != null && shortcuts.length > 0){
				List<SsoSystemNode> nodes=new ArrayList<SsoSystemNode>();
				for(CustomMenuShortcutVO regVO:shortcuts){
					nodes.add(shortcut2SystemNode(regVO));
				}
				return nodes.toArray(new SsoSystemNode[0]);
			}
		} catch (Exception e) {
			LfwLogger.error(this.getClass().getName() + "��ѯ���ܽڵ����!", e);
		}finally{
			/**
			 * �ѱ��ص�DataSource���û���
			 */
			InvocationInfoProxy.getInstance().setUserDataSource(currentDs);
		}
		return null;
	}
	/**
	 * ��û������Դ����
	 * @return
	 */
	protected String[][] getAliveDataSourceName(SSOProviderVO provider){
		Set<String[]> avliableDs = new HashSet<String[]>();
		try {
			BusiCenterVO[] bcs = getLocator(provider).lookup(IBusiCenterManageService.class).getBusiCenterVOs();
			if(bcs != null){
				for(BusiCenterVO bc : bcs){
					if(isBcAlive(bc))
						avliableDs.add(new String[]{bc.getDataSourceName(), bc.getName()});
				}
			}
		} catch (Exception e) {
			LfwLogger.error(this.getClass().getName()+"#getAliveDataSourceName===NC��û����Դ���Ƴ����쳣:" + e.getMessage() , e);
		}
		if(avliableDs.isEmpty())
			avliableDs.add(new String[]{"design", "design"});
		return avliableDs.toArray(new String[0][0]);
	}
	/**
	 * ��ñ�����NC��Locator
	 * @return
	 */
	protected NCLocator getLocator(SSOProviderVO provider){
		String ncUrl = provider.getValue("runtimeUrl");
		NCLocator locator = NCLocator.getInstance(PortletRuntimeEnv.getInstance().getNcProperties(ncUrl));
		return locator;
	}
	/**
	 * ���ϵͳ�Ƿ�
	 * @param bc
	 * @return
	 */
	private boolean isBcAlive(BusiCenterVO bc){
		UFDate now = new UFDate();
		return !bc.isLocked() && StringUtils.isNotBlank(bc.getDataSourceName()) && bc.getEffectDate().before(now) && bc.getExpireDate().after(now);
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
	
	 
//    /**
//     * ��ʼ����¼���ڣ�����û����룬���û�����Ϊ׼�����û�У����¼��ǰʱ��
//     * @param field
//     * @param req
//     */
//    private void initWorkdate(DateExtAuthField field, HttpServletRequest req)
//    {
//    	UFDate tick = new UFDate(new Date().getTime());
//    	String workdate = req.getParameter("workdate");
//    	if(workdate != null && !"".equals(workdate))
//    		field.setDefaultValue(workdate);
//    	else
//    		field.setDefaultValue(tick.toString());
//    }
}
