package nc.uap.portal.deploy.impl;

import nc.bs.framework.cluster.itf.ClusterMessage;
import nc.bs.framework.cluster.itf.ClusterMessageFilter;
import nc.uap.portal.deploy.PortalDeployMessage;

/**
 * Portal�ӻ�������Ϣ������
 * @author licza
 *
 */
public class PortalClusterDeployMsgFilter implements ClusterMessageFilter{

	@Override
	public boolean accept(ClusterMessage message) {
		return (message instanceof PortalDeployMessage);
	}

}
