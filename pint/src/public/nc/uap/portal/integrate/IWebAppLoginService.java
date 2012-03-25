package nc.uap.portal.integrate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.uap.lfw.login.authfield.ExtAuthField;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.user.entity.IUserVO;


 

/**
 * ���㼯�ɽӿ�
 * @author gd 
 * @version NC5.6
 * @since NC5.0
 */
public interface IWebAppLoginService {
	
	/**
	 * ��ȡ������webϵͳ��ʵ��URL
	 * @param req 
	 * @param res 
	 * @param credential ����ϵͳ��ƾ֤��Ϣ
	 * @param providerVO Ӧ�ö�Ӧ��provider,���û��Ϊnull
	 * @return ����web application��ʵ��URL
	 * @throws CredentialValidateException 
	 */
	public String getGateUrl(HttpServletRequest req, HttpServletResponse res, PtCredentialVO credential, SSOProviderVO providerVO) throws CredentialValidateException;
	
	/**
	 * �������������ϵͳ��ƾ֤����
	 * @param req
	 * @param providerVO Ӧ�ö�Ӧ��provider,���û��Ϊnull
	 * @return ���ɵ�ƾ֤
	 * @throws CredentialValidateException
	 */
	public PtCredentialVO credentialProcess(HttpServletRequest req, SSOProviderVO providerVO) throws CredentialValidateException;
	
	/**
	 * �������е�����ϵͳ��Ҫ��֤���ֶζ���
	 * @param req ��ǰ�������,���Դ��л���û�֮ǰ�������Ϣ��ΪĬ��ֵ
	 * @param providerVO Ӧ�ö�Ӧ��provider,���û��Ϊnull
	 * @param userVO ��ǰ��¼�û���UserVO
	 * @param credential ����ϵͳ��ƾ֤
	 * @param isLoginProvider ��ϵͳʹ�õ�provider�Ƿ���ϵͳ��¼��provider
	 * @return
	 * @throws CredentialValidateException
	 */
	public ExtAuthField[] getCredentialFields(HttpServletRequest req, SSOProviderVO providerVO, IUserVO userVO, PtCredentialVO credential) throws CredentialValidateException;
	
	/**
	 * �û���¼��ϢУ��,����û���¼��Ϣ���Ϸ����쳣�׳�.
	 * ƾ֤����,��ȡ��¼URLǰ����Ҫ��֤�û��ĵ����¼��Ϣ
	 * @param credentialVO
	 * @param providerVO Ӧ�ö�Ӧ��provider,���û��Ϊnull
	 * @throws CredentialValidateException
	 */
	public String verifyUserInfo(HttpServletRequest req, PtCredentialVO credentialVO, SSOProviderVO providerVO) throws CredentialValidateException;
	
	/**
	 * ��ȡ����ϵͳָ���ڵ�ĵ�½URL
	 * @param req
	 * @param res
	 * @return
	 */
	public String getNodeGateUrl(HttpServletRequest req, HttpServletResponse res, String nodeId, PtCredentialVO credential, SSOProviderVO providerVO) throws CredentialValidateException;
}
