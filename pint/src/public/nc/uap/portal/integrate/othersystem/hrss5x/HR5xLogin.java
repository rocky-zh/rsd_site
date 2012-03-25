package nc.uap.portal.integrate.othersystem.hrss5x;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.lfw.login.authfield.PasswordExtAuthField;
import nc.uap.lfw.login.authfield.TextExtAuthField;
import nc.uap.lfw.util.JsURLEncoder;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.othersystem.nc5x.NC5xLogin;
import nc.uap.portal.integrate.system.ProviderFetcher;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.service.itf.IEncodeService;
import nc.uap.portal.user.entity.IUserVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;

/**
 * HR����������֤��
 * @author gd 2009-11-26
 * @version NC5.6
 * @since NC5.6
 */
public class HR5xLogin implements IWebAppLoginService {

	public PtCredentialVO credentialProcess(HttpServletRequest req,
			SSOProviderVO providerVO) throws CredentialValidateException {
		PtCredentialVO credentialVO = new PtCredentialVO();
		String userId = (String)req.getAttribute("userid");
		String password = (String)req.getAttribute("password");
		credentialVO.setUserid(userId);
		credentialVO.setPassword(password);
		// �û�У��
		verifyUserInfo(req, credentialVO, providerVO);
		return credentialVO;
	}

	public ExtAuthField[] getCredentialFields(HttpServletRequest req,
			SSOProviderVO providerVO, IUserVO userVO, PtCredentialVO credential)
			throws CredentialValidateException {
		ExtAuthField[] fields = new ExtAuthField[2];
		fields[0] = new TextExtAuthField(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "IntegrationLoginWithForm-000002")/*�û���:*/, "userid", true);
		fields[1] = new PasswordExtAuthField(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "BOLoginWithCredential-000000")/*�û�����:*/, "password", false);
		return fields;
	}

	public String getGateUrl(HttpServletRequest req, HttpServletResponse res,
			PtCredentialVO credential, SSOProviderVO providerVO)
			throws CredentialValidateException {
		String portletId = (String) req.getAttribute("$portletId");

		// �û�У��
		verifyUserInfo(req, credential, providerVO);
		
		Map<String, String> formFieldMap = new HashMap<String, String>();
		formFieldMap.put("HRSS_LOGIN_USER_ENCODED", JsURLEncoder.encode(credential.getUserid(),"utf-8"));
		formFieldMap.put("HRSS_LOGIN_PSWD", credential.getPassword());
		String ds = providerVO.getValue("datasource");
		formFieldMap.put("HRSS_DATA_SOURCE", ds);
		
		String gateUrl = providerVO.getGateUrl();
		req.getSession().setAttribute("fieldsMap" + portletId, formFieldMap);
		req.getSession().setAttribute("form_url" + portletId, gateUrl);
		req.getSession().setAttribute("target" + portletId, "_parent");
		Logger.debug("===HR5xLogin��getGateUrl����:��ȡ���յĵ�¼��gateUrl=" + gateUrl);
		return "/portal/html/nodes/form_view.jsp?a=1&portletId=" + portletId;
	}

	public String getNodeGateUrl(HttpServletRequest req,
			HttpServletResponse res, String nodeId, PtCredentialVO credential,
			SSOProviderVO providerVO) throws CredentialValidateException {
		SSOProviderVO provider = ProviderFetcher.getInstance().getProvider("HR");
		if(provider == null)
			throw new LfwRuntimeException("δ��ȡϵͳ��ΪHR�ĵ�����Ϣ!");
		String ds = provider.getValue("datasource");
		
		IEncodeService encode = (IEncodeService)NCLocator.getInstance().lookup(IEncodeService.class);
		String gateUrl = provider.getGateUrl();
		int begin = gateUrl.indexOf("//") + 2;
		gateUrl = gateUrl.substring(begin, gateUrl.length());
		begin = gateUrl.indexOf("/");
		String ip = gateUrl.substring(0, begin);
		String nodeUrl = null;
		try {
			nodeUrl = "http://" + ip + "/hrss/urldirect.goto.d?" + "HRSSUDPARAM=" + JsURLEncoder.encode("_Huser:" + credential.getUserid() + ";_Hds:" + ds + ";_Hpwd:" + encode.encode(credential.getPassword()) + ";_Hfuncode:" + nodeId,"utf-8");
			return nodeUrl;
		} catch (BusinessException e) {
			Logger.error(e, e);
			throw new CredentialValidateException(e);
		}
	}

	public String verifyUserInfo(HttpServletRequest req,
			PtCredentialVO credentialVO, SSOProviderVO providerVO)
			throws CredentialValidateException {
//		String userId = credentialVO.getUserid();
//		String password = credentialVO.getPassword();
//		String ds = providerVO.getValue("datasource");
//		String registryUrl = providerVO.getValue("registryUrl");
//		try {
//			String param = "HRSS_LOGIN_USER_ENCODED=" + userId + "&HRSS_LOGIN_PSWD=" + password + "&HRSS_DATA_SOURCE=" + ds;
//			String result = hrRegiste(param, registryUrl);
//			if(result != null && !result.equals("0"))
//				throw new CredentialValidateException("�û������������!");
//		} catch (IOException e) {
//			Logger.error(e, e);
//			throw new CredentialValidateException("��֤ʧ��!");
//		} 
		// У��
		return new NC5xLogin().verifyUserInfo(req, credentialVO, providerVO);
	}
//
//	/**
//	 * ��HRϵͳע���¼ƾ֤
//	 */
//	private String hrRegiste(String parameters, String registrUrl) throws IOException {
//		
//		Logger.debug("===HRLogin��hrRegiste����:HR registry URL:" + registrUrl);
//		Logger.debug("===HRLogin��hrRegiste����:HR registry parameters:" + parameters);
//		
//		// ����NCע��URL
//		URL preUrl = new URL(registrUrl);
//		URLConnection uc = preUrl.openConnection();
//		// ����������������/ֵ�������������������Դ
//		uc.setDoOutput(true);
//		// ����ֻ�ܷ������õ���Ϣ
//		uc.setUseCaches(false);
//		// ����Content-Typeͷ��ָʾָ��URL�ѱ������ݵĴ���MIME����
//		uc.setRequestProperty("Content-Type",
//				"application/x-www-form-urlencoded");
//		// ����Content-Typeͷ��ָʾָ��URL�ѱ������ݵĴ���MIME����
//		uc.setRequestProperty("Content-Length", "" + parameters.length());
//		// ��ȡ���ӵ��ʵ�������
//		HttpURLConnection hc = (HttpURLConnection) uc;
//		// ��HTTP���󷽷�����ΪPOST��Ĭ�ϵ���GET��
//		hc.setRequestMethod("POST");
//		// �������
//		OutputStream os = hc.getOutputStream();
//		DataOutputStream dos = new DataOutputStream(os);
//		dos.writeBytes(parameters);
//		dos.flush();
//		dos.close();
//		
//		// ��ȡNC��ƾ֤����֤���
//		InputStream is = hc.getInputStream();
//		String returnFlag = "";
//		int ch;
//		while ((ch = is.read()) != -1) {
//			returnFlag += String.valueOf((char) ch);
//		}
//		Logger.debug("===HRLogin��hrRegiste����:result=" + returnFlag);
//		if (is != null)
//			is.close();
//		return returnFlag;
//	}
}
