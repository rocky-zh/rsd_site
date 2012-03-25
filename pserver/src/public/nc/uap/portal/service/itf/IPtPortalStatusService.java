package nc.uap.portal.service.itf;

import nc.uap.portal.deploy.PortalDeployMessage;

/**
 * Portal״̬����
 * 
 * @author licza
 * 
 */
public interface IPtPortalStatusService {
	/**
	 * ���portal�����������״̬
	 */
	void signPortalCoreStartComplete(PortalDeployMessage msg);
	
	/**
	 * portal�����Ƿ��������
	 * @return
	 */
	boolean isPortalCoreStartComplete();
	/**
	 * ���Portal��������ʱ�����Ļ�����Ϣ
	 * @return
	 */
	PortalDeployMessage getDeployMessage();
}
