package nc.uap.portal.integrate.impl;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.lfw.login.authfield.PasswordExtAuthField;
import nc.uap.lfw.login.authfield.TextExtAuthField;
import nc.uap.portal.cache.PortalCacheManager;
import nc.uap.portal.container.om.Preference;
import nc.uap.portal.container.om.Preferences;
import nc.uap.portal.container.portlet.PortletWindow;
import nc.uap.portal.container.portlet.PortletWindowID;
import nc.uap.portal.container.portlet.PortletWindowImpl;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.exception.PortletLoginException;
import nc.uap.portal.integrate.system.Reference;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.user.entity.IUserVO;

/**
 * ���õ�����ϵͳ�ĵ�¼�����㼯�ɵĵ�����֤��
 * 
 * @author gd 2009-10-16
 * @version NC5.6
 * @since NC5.6
 */
public class IntegrationLoginWithForm implements IWebAppLoginService {
	// ϵͳ��ʶ��
	public static final String SYSTEM_CODE = "system_code";
	// �����ֶ�ǰ׺
	public static final String PASSWDFIELD_PREFIX = "passwdfield_";
	// �û�id�ֶ�ǰ׺
	public static final String USERIDFIELD_PREFIX = "useridfield_";
	// ��¼ϵͳ������url
	public static final String GATEURL = "gateUrl";
	// �ֶ�ǰ׺
	public static final String FIELD_PREFIX = "field_";
	public static final String SUCCESS_SIGN = "success_sign";
	public static final String FAILURE_SIGN = "failure_sign";
	public static final String REGISTRY_URL = "registryUrl";
	// �û���ͳһǰ׺
	public static final String USERID_PREFIX = "userid_prefix";

	public PtCredentialVO credentialProcess(HttpServletRequest req,
			SSOProviderVO providerVO) throws CredentialValidateException {
		Map<String, String> refMap = getRefMap(providerVO);
		String[] fields = checkUserIdAndPwd(refMap);

		// �û�idǰ׺
		String userIdPre = "";
		if (refMap.containsKey(USERID_PREFIX))
			userIdPre = refMap.get(USERID_PREFIX) == null ? "" : refMap.get(
					USERID_PREFIX).trim();

		// ����ƾ֤VO
		PtCredentialVO credentialVO = new PtCredentialVO();
		credentialVO.setUserid(userIdPre + req.getAttribute(fields[0]));
		credentialVO.setPassword((String)req.getAttribute(fields[1]));

		verifyUserInfo(req, credentialVO, providerVO);
		return credentialVO;
	}

	public ExtAuthField[] getCredentialFields(HttpServletRequest req,
			SSOProviderVO providerVO, IUserVO userVO, PtCredentialVO credential)
			throws CredentialValidateException {
		Map<String, String> refMap = getRefMap(providerVO);
		if (refMap.isEmpty())
			throw new LfwRuntimeException("δ�����κε����¼����Ϣ");

		String[] fields = checkUserIdAndPwd(refMap);
		ArrayList<String> credentialFields = getCredentialFields(refMap);
		ExtAuthField userName = null;
		ExtAuthField passWord = null;
		for (int i = 0; i < credentialFields.size(); i++) {
			String fieldKey = credentialFields.get(i);
			if (fieldKey.equals(fields[0]))
				userName = new TextExtAuthField("�û���", fieldKey, true);
			else if (fieldKey.equals(fields[1]))
				passWord = new PasswordExtAuthField("����",	fieldKey, true);
		}

		return new ExtAuthField[] { userName, passWord };
	}

	public String getGateUrl(HttpServletRequest req, HttpServletResponse res,
			PtCredentialVO credential, SSOProviderVO providerVO)
			throws CredentialValidateException {

		String userId = credential.getUserid();
		String password = credential.getPassword();
		// У���û���Ϣ
		verifyUserInfo(req, credential, providerVO);

		// Portlet���õ�gateUrl����sso-prop.xml�����õ�
		String formUrl = providerVO.getGateUrl();
		String portletId = (String) req.getAttribute("$portletId");
		Map<String, String> prefs = getPrefsMap(portletId);
		if (prefs.containsKey(GATEURL)) {
			String url = prefs.get(GATEURL);
			if (url != null && !url.equals(""))
				formUrl = url;
		}
		Logger.debug("===IntegrationLoginWithForm��getGateUrl����:gateUrl=" + formUrl);

		Map<String, String> formFieldMap = new HashMap<String, String>();
		Map<String, String> refMap = getRefMap(providerVO);
		ArrayList<String> credentialFields = getCredentialFields(refMap);
		for (int i = 0; i < credentialFields.size(); i++) {
			if (credentialFields.get(i).startsWith(USERIDFIELD_PREFIX))
				formFieldMap.put(credentialFields.get(i).substring(
						USERIDFIELD_PREFIX.length()), userId);
			else if (credentialFields.get(i).startsWith(PASSWDFIELD_PREFIX))
				formFieldMap.put(credentialFields.get(i).substring(
						PASSWDFIELD_PREFIX.length()), password);
			else {
				String field = credentialFields.get(i);
				String value = providerVO.getValue(field);
				if (field.startsWith(FIELD_PREFIX))
					field = field.substring(FIELD_PREFIX.length());
				formFieldMap.put(field, value);
			}
		}
		req.getSession().setAttribute("fieldsMap" + portletId, formFieldMap);
		req.getSession().setAttribute("form_url" + portletId, formUrl);
		return "/portal/html/nodes/form_view.jsp?a=1&portletId=" + portletId;
	}

	public String getNodeGateUrl(HttpServletRequest req,
			HttpServletResponse res, String nodeId, PtCredentialVO credential,
			SSOProviderVO providerVO) throws CredentialValidateException {
		// TODO Auto-generated method stub
		return null;
	}

	public String verifyUserInfo(HttpServletRequest req,
			PtCredentialVO creadentialVO, SSOProviderVO providerVO)
			throws CredentialValidateException {
		// ����ָ����Ҫ��֤�ĵ�����ϵͳ�ֶ�
		Map<String, String> refMap = getRefMap(providerVO);
		ArrayList<String> fields = getCredentialFields(refMap);
		StringBuffer param = new StringBuffer();
		for (int i = 0; i < fields.size(); i++) {
			String fieldKey = fields.get(i);
			String realKey = getRealField(fieldKey);
			String realValue = null;
			if (fieldKey.startsWith(USERIDFIELD_PREFIX))
				realValue = creadentialVO.getUserid();
			else if (fieldKey.startsWith(PASSWDFIELD_PREFIX))
				realValue = creadentialVO.getPassword();
			else
				realValue = refMap.get(fieldKey);

			try {
				// ����URL����
				param.append(realKey + "=" + URLEncoder.encode(realValue, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				Logger.error(e, e);
				throw new CredentialValidateException(e);
			}
			if (i != fields.size() - 1)
				param.append("&");
		}
		Logger.debug("===IntegrationLoginWithForm��verifyUserInfo����:param=" + param);

		// У��
		String registryUrl = providerVO.getValue("registryUrl");
		if (registryUrl == null || registryUrl.equals(""))
			throw new CredentialValidateException("δ��ȡregistryUrl,���ڵ��������н�������!");
		Logger
				.debug("===IntegrationLoginWithForm��verifyUserInfo����:registryUrl="
						+ registryUrl);

		String returnFlag = null;
		try {
			returnFlag = registe(param.toString(), registryUrl);
			Logger.debug("===IntegrationLoginWithForm��verifyUserInfo����:���ؽ��="
					+ returnFlag);
		} catch (Exception e) {
			Logger.error(e, e);
			throw new CredentialValidateException(e.getMessage());
		}

		String successFlag = refMap.get(SUCCESS_SIGN);
		String failureFlag = refMap.get(FAILURE_SIGN);
		if (returnFlag != null) {
			if ((failureFlag != null && returnFlag.indexOf(failureFlag) != -1)
					|| (successFlag != null && returnFlag.indexOf(successFlag) == -1))
				throw new CredentialValidateException("�û���֤ʧ��,�û�������������");
		} else
			throw new CredentialValidateException("δ��ȡ�κ�У�鷵��ֵ!");

		return returnFlag;
	}

	/**
	 * ��ȡportlet�ĸ��Ի���Ϣ
	 * 
	 * @param portletId
	 * @return
	 */
	private Map<String, String> getPrefsMap(String portletId) {
		// �ô�prefs�����л���,�����̨portlet��prefs�޸�,�ô��޷�������µ�prefs,����˴������Ч��û�д������
		Map<String, String> prefsMap = new HashMap<String, String>();
		PortletWindow win = new PortletWindowImpl(new PortletWindowID(portletId));
		Preferences preferences = PortalCacheManager.getPreferences(win);
		List<Preference> preferencelist = preferences.getPortletPreferences();
		if(preferencelist != null && !preferencelist.isEmpty()){
			for(Preference preference : preferencelist){
				String value = null;
				 if(preference.getValues() != null && preference.getValues().size() > 0)
				 value = preference.getValues().get(0);
				 prefsMap.put(preference.getName(), value);
			}
		}
		return prefsMap;
	}

	/**
	 * У���Ƿ��������û�id�������ֶ�
	 * 
	 * @param portletId
	 * @return
	 * @throws PortletLoginException
	 */
	private String[] checkUserIdAndPwd(Map<String, String> prefs)
			throws CredentialValidateException {
		// ����ָ���û�id�ֶ�
		Iterator<String> it = prefs.keySet().iterator();
		boolean hasUserIdField = false;
		String userIdField = null;
		while (it.hasNext()) {
			String key = it.next();
			if (key.startsWith(USERIDFIELD_PREFIX)) {
				hasUserIdField = true;
				userIdField = key;
				break;
			}
		}
		if (hasUserIdField == false)
			throw new CredentialValidateException("�û�id�ֶα�����\"useridfield_\"��ͷ!");

		// ����ָ���û������ֶ�
		boolean hasPasswordField = false;
		String passwordField = null;
		it = prefs.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (key.startsWith(PASSWDFIELD_PREFIX)) {
				hasPasswordField = true;
				passwordField = key;
				break;
			}
		}
		if (hasPasswordField == false)
			throw new CredentialValidateException("�û������ֶα�����\"passwdfield_\"��ͷ!");
		return new String[] { userIdField, passwordField };
	}

	/**
	 * ȥ��ǰ׺,���ص�����ϵͳ�������ֶ���
	 * 
	 * @param fieldKey
	 * @return
	 */
	private String getRealField(String fieldKey) {
		String realKey = fieldKey;
		if (fieldKey.startsWith(FIELD_PREFIX))
			realKey = fieldKey.substring(FIELD_PREFIX.length());
		else if (fieldKey.startsWith(USERIDFIELD_PREFIX))
			realKey = fieldKey.substring(USERIDFIELD_PREFIX.length());
		else if (fieldKey.startsWith(PASSWDFIELD_PREFIX))
			realKey = fieldKey.substring(PASSWDFIELD_PREFIX.length());
		return realKey;
	}

	/**
	 * ��ȡsso�����õ����е��㼯����Ϣ
	 * 
	 * @param providerVO
	 * @return
	 */
	private HashMap<String, String> getRefMap(SSOProviderVO providerVO) {
		HashMap<String, String> refMap = new HashMap<String, String>();
		List<Reference> refs = providerVO.getProviderReference();
		if (refs != null) {
			for (int i = 0; i < refs.size(); i++) {
				Reference ref = refs.get(i);
				refMap.put(ref.getName(), ref.getValue());
			}
		}
		return refMap;
	}

	/**
	 * �Ƿ�Ϊ����֤�ֶ�,�����ֶ�Ϊ������ڲ��ֶ�
	 * 
	 * @param fieldName
	 * @return
	 */
	private boolean isCredentialFiled(String fieldName) {
		if (!fieldName.equals(SYSTEM_CODE) && !fieldName.equals(SUCCESS_SIGN)
				&& !fieldName.equals(FAILURE_SIGN)
				&& !fieldName.equals(USERID_PREFIX)
				&& !fieldName.equals(REGISTRY_URL))
			return true;
		else
			return false;
	}

	/**
	 * ��ȡ��������Ҫ��֤�������ֶ�
	 * 
	 * @param portletId
	 * @return
	 */
	private ArrayList<String> getCredentialFields(Map<String, String> refMap) {
		// ����ָ����Ҫ��֤�ĵ�����ϵͳ�ֶ�
		ArrayList<String> fields = new ArrayList<String>();
		Iterator<String> it = refMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (isCredentialFiled(key))
				fields.add(key);
		}
		return fields;
	}

	private String registe(String parameters, String registrUrl)
			throws IOException, PortletException {
		Logger.info("===registry URL:" + registrUrl);
		Logger.info("===registry parameters:" + parameters);
		// ����BOע��URL
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
		// ��ȡBO��ƾ֤����֤���
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
}
