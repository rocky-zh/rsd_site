package nc.uap.portal.integrate;

import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.funnode.SsoSystemNode;
import nc.uap.portal.integrate.system.SSOProviderVO;

/**
 * Portal���ɹ��ܽڵ�ӿ�
 * 
 * @author licza
 * 
 */
public interface IWebAppFunNodesService {
	/**
	 * ����û����ܽڵ�
	 * 
	 * @param credential
	 * @param provider
	 * @return
	 */
	public SsoSystemNode[] getUserNodes(SSOProviderVO provider,PtCredentialVO credential) throws CredentialValidateException;

	/**
	 * ������нڵ�
	 * 
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public SsoSystemNode[] getAllFunNodes(SSOProviderVO provider);
}
