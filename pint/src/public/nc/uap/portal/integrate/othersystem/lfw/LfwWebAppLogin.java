package nc.uap.portal.integrate.othersystem.lfw;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.lfw.login.vo.LfwFunNodeVO;
import nc.uap.portal.integrate.IWebAppFunNodesService;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.funnode.SsoSystemNode;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.user.entity.IUserVO;

import org.apache.commons.io.IOUtils;

/**
 * LFW��Ŀ������
 * 
 * @author licza
 * 
 */
public class LfwWebAppLogin implements IWebAppLoginService,
		IWebAppFunNodesService {
	static final String contentType = "content-type";
	static final String contentTypeValue = "application/x-java-serialized-object,charset=utf-8";
	static final String servletURL = "/registerServlet";
	/** ��̬��֤�ֶ� **/
	public static final String VERIFY_FIELDS = "verifyFields";
	/** �û����ֶ� **/
	public static final String USER_ID_FIELD = "UserIdField";
	/** �����ֶ� **/
	public static final String PWD_FIELD = "PwdField";
	/** ����Դ **/
	public static final String DS_NAME = "dsname";
	
	private static final String REGISTR_URL = "registrUrl";
	static final int TYPE_USER_FUNNODE = 3;
	static final int TYPE_ALLFUNNODE = 4;
	private static final String PUT = "PUT";
 
	/**
	 * Ҫ��֤���ֶ�
	 */
	@Override
	public PtCredentialVO credentialProcess(HttpServletRequest req,
			SSOProviderVO provider) throws CredentialValidateException {
		try {
			// ����ƾ֤VO
			PtCredentialVO credentialVO = new PtCredentialVO();
			String userId = (String) req.getAttribute(getUserIdFieldName(req));
			String password = (String) req
					.getAttribute(getUserPwdFieldName(req));
			if (password == null)
				password = "";
			credentialVO.setUserid(userId);
			credentialVO.setPassword(password);

			String[] verifyFields = getVerifyField(req);
			for (String field : verifyFields) {
				credentialVO.getCredentialReference().setValue(field,
						(String) req.getAttribute(field));
			}
			// �û���Ϣ��֤
			verifyUserInfo(req, credentialVO, provider);

			return credentialVO;
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			String msg = e.getMessage();
			if(msg == null)
				msg = "��������ϵͳ��ƾ֤ʧ��!";
			throw new CredentialValidateException(msg, e);
		}
	}

	@Override
	public ExtAuthField[] getCredentialFields(HttpServletRequest req,
			SSOProviderVO providerVO, IUserVO userVO, PtCredentialVO credential)
			throws CredentialValidateException {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		String serverUrl = getServerURL(providerVO);
		try {
			URL url = new URL(serverUrl);
			URLConnection conn = url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setChunkedStreamingMode(2048);
				((HttpURLConnection) conn).setRequestMethod("PUT");
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty(contentType, contentTypeValue);
			oos = new ObjectOutputStream(conn.getOutputStream());
			oos.writeInt(0);
			oos.writeUTF(providerVO.getValue(DS_NAME));
			oos.flush();
			oos.close();
			ois = new ObjectInputStream(conn.getInputStream());
			Object obj = ois.readObject();
			if (obj instanceof Exception) {
				throw (Exception) obj;
			}
			return (ExtAuthField[]) obj;
		} catch (Exception e) {
			LfwLogger.error(e.getMessage() + "===" + serverUrl,e);
			throw new CredentialValidateException(e);
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(ois);
		}
	}

	@Override
	public String getGateUrl(HttpServletRequest req, HttpServletResponse res,
			PtCredentialVO credential, SSOProviderVO providerVO)
			throws CredentialValidateException {
		String gateUrl = providerVO.getGateUrl();
		String key = registe(req, credential, providerVO);
			gateUrl += (gateUrl.indexOf("?") > 0 ? "&" : "?") + "ssoKey=" + key
			+ "&clienttype=portal&dsname="+providerVO.getValue("dsname");
		return gateUrl;
	}

	@Override
	public String getNodeGateUrl(HttpServletRequest req,
			HttpServletResponse res, String gateUrl, PtCredentialVO credential,
			SSOProviderVO providerVO) throws CredentialValidateException {
		String key = registe(req, credential, providerVO);
		return gateUrl  + (gateUrl.indexOf("?") > 0 ? "&" : "?")+"ssoKey=" + key + "&clienttype=portal&dsname="+providerVO.getValue("dsname") ;
		
	}

	@Override
	public String verifyUserInfo(HttpServletRequest req,
			PtCredentialVO credentialVO, SSOProviderVO providerVO)
			throws CredentialValidateException {
		try {
			String parameters = getAuthString(1, credentialVO, providerVO, req);
			URL preUrl = new URL(getServerURL(providerVO));
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
			String returnFlag = IOUtils.toString(is, "utf-8");		
			IOUtils.closeQuietly(is);
			if(returnFlag.startsWith("f:")){
				throw new SecurityException(returnFlag.substring(2));
			}
			return returnFlag;
		} catch (Exception e) {
			LfwLogger.error("===LfwWebAppLogin��verifyUserInfo����:"
					+ e.getMessage(), e);
			throw new CredentialValidateException(e.getMessage());
		}
	}

	/**
	 * ע��
	 * 
	 * @param req
	 * @param credentialVO
	 * @param providerVO
	 * @return
	 * @throws CredentialValidateException
	 */
	public String registe(HttpServletRequest req, PtCredentialVO credentialVO,
			SSOProviderVO providerVO) throws CredentialValidateException {
		String url = getServerURL(providerVO);
		String parameters = getAuthString(2, credentialVO, providerVO, req);
		try {
			URL preUrl = new URL(url);
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
			String returnFlag = IOUtils.toString(is, "utf-8");		
			IOUtils.closeQuietly(is);
			if(returnFlag.startsWith("f:")){
				throw new SecurityException(returnFlag.substring(2));
			}
			return returnFlag.substring(2);
		} catch (Exception e) {
			LfwLogger.error("===LfwWebAppLogin��registe���������쳣:" + e.getMessage() +"  " + url +" " + parameters , e);
			throw new CredentialValidateException(e.getMessage());
		}
	}

	public String getUserIdFieldName(HttpServletRequest req) {
		return req == null ? null : (String) req.getAttribute(USER_ID_FIELD);
	}

	public String getUserPwdFieldName(HttpServletRequest req) {
		return req == null ? null : (String) req.getAttribute(PWD_FIELD);
	}

	/**
	 * ���������Ҫ��֤���ֶ�
	 * 
	 * @param providerVO
	 * @return
	 */
	private String[] getVerifyField(HttpServletRequest req) {
		String verifyFields = req == null ? null : (String) req.getAttribute(VERIFY_FIELDS);
		if (verifyFields != null)
			return verifyFields.split(",");
		return new String[0];
	}

	/**
	 * �����֤��
	 * 
	 * @param credentialVO
	 * @param req
	 * @return
	 */
	private String getAuthString(int type, PtCredentialVO credentialVO,SSOProviderVO providerVO,
			HttpServletRequest req) {
		StringBuffer sb = new StringBuffer("type=");
		sb.append(type);
		sb.append("&dsname=");
		sb.append(providerVO.getValue(DS_NAME));
		String userIdField = getUserIdFieldName(req);
		if (userIdField != null) 
		{
			/**
			 * �û���
			 */
			sb.append("&");
			sb.append(getUserIdFieldName(req));
			sb.append("=");
			sb.append(credentialVO.getUserid());
			String userPwdField = getUserPwdFieldName(req);
			/**
			 * ����
			 */
			if (userPwdField != null) {
				sb.append("&");
				sb.append(getUserPwdFieldName(req));
				sb.append("=");
				sb.append(credentialVO.getPassword());
			}
			/**
			 * ��������
			 */
			String[] verifyFields = getVerifyField(req);
			if (verifyFields != null) {
				for (String field : verifyFields) {
					String value = credentialVO.getCredentialReference().getValue(field);
					sb.append("&");
					sb.append(field);
					sb.append("=");
					sb.append(value);
				}
			}
		} 
		else 
		{
			Map<String, String[]> map = credentialVO.getCredentialReference().getMap();
			if(map != null && !map.isEmpty())
				for (String key : map.keySet()) {
					String[] val = map.get(key);
					sb.append("&");
					sb.append(key);
					sb.append("=");
					sb.append((val != null && val.length > 0) ? val[0] : "");
				}
		}
		return sb.toString();
	}

	/**
	 * ��÷�������ַ
	 * 
	 * @param providerVO
	 * @return
	 */
	private String getServerURL(SSOProviderVO providerVO) {
		String serverUrl = providerVO.getValue("registrUrl");
		return serverUrl;
	}

	@Override
	public SsoSystemNode[] getAllFunNodes(SSOProviderVO provider) {
		LfwFunNodeVO[] fns = getRegFunNodes(TYPE_ALLFUNNODE, provider, null);
		return translet(fns);
	}

	@Override
	public SsoSystemNode[] getUserNodes(SSOProviderVO provider,
			PtCredentialVO credential) throws CredentialValidateException {
		verifyUserInfo(null, credential, provider);
		LfwFunNodeVO[] fns = getRegFunNodes(TYPE_USER_FUNNODE, provider, credential);
		return translet(fns);
	}

	/**
	 * ��registerServlet�л�ù��ܽڵ�
	 * 
	 * @param type
	 * @param userid
	 * @return
	 */
	public LfwFunNodeVO[] getRegFunNodes(int type, SSOProviderVO providerVO,
			PtCredentialVO credential) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		String serverURL = providerVO.getValue(REGISTR_URL);
		try {
			URL url = new URL(serverURL);
			URLConnection conn = url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setChunkedStreamingMode(2048);
				((HttpURLConnection) conn).setRequestMethod(PUT);
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty(contentType, contentTypeValue);
			oos = new ObjectOutputStream(conn.getOutputStream());
			oos.writeInt(type);
			oos.writeUTF(providerVO.getValue(DS_NAME));
			if (credential != null){
				oos.writeObject(getParamMap(credential));
			}
			oos.flush();
			oos.close();
			ois = new ObjectInputStream(conn.getInputStream());
			Object obj = ois.readObject();
			if (obj instanceof Exception) {
				throw (Exception) obj;
			}
			LfwFunNodeVO[] fns = (LfwFunNodeVO[]) obj;
			return fns;
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(ois);
		}
		return new LfwFunNodeVO[0];
	}
	
	/**
	 * ��Credential�л�ò���
	 * @param credential
	 * @return
	 */
	protected HashMap<String, String> getParamMap(PtCredentialVO credential){
		HashMap<String, String> param = new HashMap<String, String>();
		Map<String, String[]> map = credential.getCredentialReference().getMap();
		if(map != null && !map.isEmpty()){
			for (String key : map.keySet()) {
				String[] val = map.get(key);
				if(val != null && val.length > 0){
					param.put(key, val[0]);
				}
			}
		}
		return param;
	}
	
	/**
	 * ת��lfw���ܽڵ�Ϊportal���ܽڵ�
	 * 
	 * @param fns
	 * @return
	 */
	private SsoSystemNode[] translet(LfwFunNodeVO[] fns) {
		List<SsoSystemNode> ssns = new ArrayList<SsoSystemNode>();
		if (fns != null && fns.length > 0) {
			for (LfwFunNodeVO fn : fns) {
				SsoSystemNode ssn = new SsoSystemNode();
				ssn.setNodeId(fn.getPk_funnode());
				ssn.setNodeUrl(fn.getLinkurl());
				ssn.setNodeName(fn.getTitle());
				ssns.add(ssn);
			}
		}
		return ssns.toArray(new SsoSystemNode[0]);
	}
}
