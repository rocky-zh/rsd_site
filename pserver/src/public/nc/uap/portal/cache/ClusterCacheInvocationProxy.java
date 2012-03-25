package nc.uap.portal.cache;

import nc.bs.framework.server.ServerConfiguration;
import nc.uap.portal.service.PortalServiceUtil;

/**
 * �ػ���֪ͨ
 * 
 * @author licza
 * 
 */
public class ClusterCacheInvocationProxy {

	/**
	 * ֪ͨ�ظ��»���
	 * 
	 * @param key
	 * @param pluginid
	 * @param value
	 */
	public static void fire(ClusterCacheMessage ccm) {
		ccm.setServer(getLocalServerName());
		PortalServiceUtil.getClusterSender().sendMessage(ccm);
	}

	/**
	 * ��õ�ǰ��������
	 * 
	 * @return
	 */
	private static final String getLocalServerName() {
		ServerConfiguration cfg = ServerConfiguration.getServerConfiguration();
		return cfg.getServerName();
	}

}
