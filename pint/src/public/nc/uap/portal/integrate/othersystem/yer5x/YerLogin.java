package nc.uap.portal.integrate.othersystem.yer5x;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.MapProcessor;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.login.authfield.ComboExtAuthField;
import nc.uap.lfw.login.authfield.DateExtAuthField;
import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.lfw.login.authfield.PasswordExtAuthField;
import nc.uap.lfw.login.authfield.RefExtAuthField;
import nc.uap.lfw.login.authfield.TextExtAuthField;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.othersystem.nc5x.NC5xLogin;
import nc.uap.portal.integrate.othersystem.nc5x.Nc5xIntConstent;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.user.entity.IUserVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.lang.UFDate;

/**
 * ���ϱ���ϵͳ����
 * @author gd 2009-01-19
 * @version NC5.6
 * @since NC5.5
 * 
 */
public class YerLogin implements IWebAppLoginService {
	
	private static final String NULL_STRING = "NULL";
	
	@SuppressWarnings("unchecked")
	public PtCredentialVO credentialProcess(HttpServletRequest req, SSOProviderVO providerVO) throws CredentialValidateException {
		try {
			
			// ����ƾ֤VO
			PtCredentialVO credentialVO = new PtCredentialVO();
			String userId = (String)req.getAttribute("userId");
			String password = (String)req.getAttribute("password");
			credentialVO.setUserid(userId);
			credentialVO.setPassword(password);
			// ����������Ϣ��ƾ֤Reference��
			String accountcode = (String)req.getAttribute("accountcode");
			String pkcorp = (String)req.getAttribute("pkcorp");
			String workdate = new UFDate().toLocalString();
			UFDate tick = (UFDate)req.getAttribute("workdate") ;;
			if(tick != null)
				workdate = tick.toLocalString();
			credentialVO.getCredentialReference().setValue("workdate", workdate);
			credentialVO.getCredentialReference().setValue("accountcode", accountcode);
			
			// ͨ�����ײ�������Դ
			String dataSource = providerVO.getValue("datasource");
//			String accountName = accountService.getAccountByCode(accountcode).getAccountName();
			
			// ͨ����˾pk��ѯ��˾��
			
			String nc5xDs = providerVO.getValue(Nc5xIntConstent.NC5XDs);
			String oldDs = InvocationInfoProxy.getInstance().getUserDataSource();
			String corpName = null;
			try {
				InvocationInfoProxy.getInstance().setUserDataSource(nc5xDs);
				Map<String,String> obj = (Map<String,String>)CRUDHelper.getCRUDService().query("SELECT unitname FROM BD_CORP WHERE PK_CORP ='"+ pkcorp +"'", new MapProcessor());
				corpName = (String)obj.get("unitname");
			} catch (LfwBusinessException e) {
				throw new CredentialValidateException(e.getMessage(),e);
			}finally{
				InvocationInfoProxy.getInstance().setUserDataSource(oldDs);
			}
			
//			credentialVO.getCredentialReference().setValue("accountname", accountName);
			credentialVO.getCredentialReference().setValue("pkcorp", pkcorp);
			credentialVO.getCredentialReference().setValue("corpName", corpName);
			String language = "simpchn";
			credentialVO.getCredentialReference().setValue("language", language);
			credentialVO.getCredentialReference().setValue("datasource", dataSource);
			
			// �û�У��
			String ncuserpk = verifyUserInfo(req, credentialVO, providerVO);
			 
			credentialVO.getCredentialReference().setValue("ncuserpk", ncuserpk);
			return credentialVO;
		} catch (Exception e) {
			Logger.error(e, e);
			throw new CredentialValidateException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "YerLogin-000000", null, new String[]{e.getMessage()})/*��������WEB����ϵͳ��ƾ֤ʧ��:{0}*/, e);
		}
	}

	public ExtAuthField[] getCredentialFields(HttpServletRequest req, SSOProviderVO provider, IUserVO userVO, PtCredentialVO credential)
			throws CredentialValidateException {
		Logger.debug("===YerLogin��getCredentialFields����:����YerLogin��getCredentialFields()����!");
	    Map<String, String> userInputMap = getUserInputValue(req);
	    
		// ��������
		ExtAuthField[] fields = new ExtAuthField[5];
		fields[0] = new ComboExtAuthField("ϵͳ", "accountcode", true);
		fields[1] = new RefExtAuthField("��˾", "pkcorp", false);
		fields[2] = new TextExtAuthField("�û�", "userId", true);
		fields[3] = new PasswordExtAuthField("����", "password", false);
		fields[4] = new DateExtAuthField("����", "workdate", true);
		fields[0].setRequired(true);
		fields[2].setRequired(true);
		fields[3].setRequired(true);
		fields[4].setRequired(true);
		
		String defaultAccount = initAccountCodeField(((ComboExtAuthField)fields[0]), userInputMap, provider, userVO, credential);
		initCorpField(((RefExtAuthField)fields[1]), defaultAccount, provider, credential, userInputMap);
		initUserAndPwdField(userInputMap, (TextExtAuthField)fields[2], (PasswordExtAuthField)fields[3], credential, userVO);
		initWorkdate((DateExtAuthField)fields[4], req);
		return fields;
	}

	public String getGateUrl(HttpServletRequest req, HttpServletResponse res, PtCredentialVO credential, SSOProviderVO providerVO)
			throws CredentialValidateException {
		try {
			String portletId = (String) req.getAttribute("$portletId");
			String gateUrl = providerVO.getGateUrl() + "?fromPortal=1";
			String ncUrl = providerVO.getValue("runtimeUrl");
			
			Logger.debug("===YerLogin��getGateUrl����:��ȡԭʼgateUrl=" + gateUrl);
			Logger.debug("===YerLogin��getGateUrl����:��ȡNC��runtimeUrl=" + ncUrl);
			
			// ��ƾ֤�л�õ�¼��Ϣ
			String usercode = credential.getUserid();
			String password = credential.getPassword();
			String accountcode = credential.getCredentialReference().getValue("accountcode");
			String accountname = credential.getCredentialReference().getValue("accountname");
			String pkcorp = credential.getCredentialReference().getValue("pkcorp");
			String workdate = credential.getCredentialReference().getValue("workdate");
			String datasource = credential.getCredentialReference().getValue("datasource");
			String corpName = credential.getCredentialReference().getValue("corpName");
		
			// �û�У��
			verifyUserInfo(req, credential, providerVO);
			
			Map<String, String> formFieldMap = new HashMap<String, String>();
			formFieldMap.put("userId", usercode);
			formFieldMap.put("password", password);
			formFieldMap.put("workdate", workdate);
			// ��������
			formFieldMap.put("accountCode", accountname);
			formFieldMap.put("accountCodeValue", accountcode);
			formFieldMap.put("datasource", datasource);
			formFieldMap.put("corpCode", pkcorp == null ? "0001" : pkcorp);
			formFieldMap.put("corpName", corpName);
			
			Logger.debug("===YerLogin��getGateUrl����:��ȡ���յĵ�¼NC��gateUrl=" + gateUrl);
			
			req.getSession().setAttribute("fieldsMap" + portletId, formFieldMap);
			req.getSession().setAttribute("form_url" + portletId, gateUrl);
			return "/portal/html/nodes/form_view.jsp?a=1&portletId=" + portletId;
			
		} catch (Exception e) {
			Logger.error(e, e);
			throw new CredentialValidateException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "NcLogin-000001", null, new String[]{e.getMessage()})/*У��NC��¼ʧ�ܣ�{0}*/);
		}
	}
	
	public String verifyUserInfo(HttpServletRequest req, PtCredentialVO credentialVO, SSOProviderVO providerVO) throws CredentialValidateException
	{
		// У��
		return new NC5xLogin().verifyUserInfo(req, credentialVO, providerVO);
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
		String username = req.getParameter("userId") == null ? NULL_STRING : req.getParameter("userId");
		String password = req.getParameter("password") == null ? NULL_STRING : req.getParameter("password");
		String workdate = req.getParameter("workdate") == null ? NULL_STRING : req.getParameter("workdate");
		
		map.put("accountcode", accountcode);
		map.put("pkcorp", pkcorp);
		map.put("userId", username);
		map.put("password", password);
		map.put("workdate", workdate);
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
     * ��ʼ����˾����
     * ���ȼ����û������ > ������Ϣ
     * 
     * @param corpField
     */
    private void initCorpField(RefExtAuthField corpField, String defaultAccount, SSOProviderVO provider, PtCredentialVO credential, Map<String, String> userInputMap){
    	
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
    	String userId = !userInputMap.get("userId").equals(NULL_STRING) ? userInputMap.get("userId") : null;
    	String password = !userInputMap.get("password").equals(NULL_STRING) ? userInputMap.get("password") : null;
    	
    	if(userId == null && credential != null)
    		userId = credential.getUserid();
    	if(password == null && credential != null)
    		password = credential.getPassword();

    	Logger.debug("===YerLogin��initUserAndPwdField����:��ȡȱʡ�û���/����=" + userId + "/" + password);
    	
    	userIdField.setDefaultValue(userId);
    	pwdField.setDefaultValue(password);
    }
    
    /**
     * ��ʼ����¼���ڣ�����û����룬���û�����Ϊ׼�����û�У����¼��ǰʱ��
	 * @param field
	 * @param req
	 */
	 private void initWorkdate(DateExtAuthField field, HttpServletRequest req)
	 {
		 UFDate tick = new UFDate(new Date().getTime());
	 	 String workdate = req.getParameter("workdate");
	 	 if(workdate != null && !"".equals(workdate))
	 		 field.setDefaultValue(workdate);
	 	 else
	 		 field.setDefaultValue(tick.toString());
	 }

	public String getNodeGateUrl(HttpServletRequest req,
			HttpServletResponse res, String nodeId, PtCredentialVO credential,
			SSOProviderVO providerVO) throws CredentialValidateException {
//		// �����¼
//		this.getGateUrl(req, res, credential, providerVO);
//		// �ı�urlΪĳ���ڵ��url
//		String nodeUrl = (String) req.getSession().getAttribute("form_url");
//		String oldDs = InvocationInfoProxy.getInstance().getUserDataSource();
//		try {
//			String returnUrl = req.getParameter("returnUrl");
//			// ���returnUrlΪnull,˵���ǽ��е����¼�󵽴��,��ʱ�ӿ��в���ڵ��url
//			if(returnUrl == null)
//			{
//				SSOProviderVO provider = ProviderFetcher.getInstance().getProvider(providerVO.getSystemCode());
//				String ncUrl = ProviderFetcher.getInstance().getProvider(providerVO.getSystemCode()).getValue("runtimeUrl");
//				NCLocator locator = NCLocator.getInstance(PortletRuntimeEnv.getInstance().getNcProperties(ncUrl));
//				// ��ȡNC����
//				String accountStr = provider.getValue("AccountCode");
//				if(accountStr == null || accountStr.equals(""))
//					throw new LfwRuntimeException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "NcNodesProvider-000000", null, new String[]{providerVO.getSystemCode()})/*{0}ϵͳδ��sso-prop.xml���������ױ���!*/);
//				String[] accounts = accountStr.split(",");
//				String datasource = PortalUtil.fetchDatasourceName(accounts[0], locator);
//				InvocationInfoProxy.getInstance().setUserDataSource(datasource);
//				IFuncRegisterQueryService iPower = (IFuncRegisterQueryService) NCLocator.getInstance().lookup(IFuncRegisterQueryService.class.getName());
//				FuncRegisterVO vo = iPower.findFuncRegisterVOByPrimaryKey(nodeId);
//				returnUrl = vo.getClassName();
//			}
//			req.getSession().setAttribute("form_url", nodeUrl + "&returnUrl=" + URLEncoder.encode(returnUrl, "UTF-8"));
//			return "/portal/html/portlets/application/form_view.jsp?a=1";
//		} catch (Exception e) {
//			Logger.error(e, e);
//			throw new LfwRuntimeException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "YerLogin-000002")/*��ȡ�ڵ㵥���¼url�����쳣!*/, e);
//		} finally {
//			InvocationInfoProxy.getInstance().setUserDataSource(oldDs);
//		}
		return null;
	}
}
