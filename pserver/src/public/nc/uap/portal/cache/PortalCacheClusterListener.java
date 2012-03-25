package nc.uap.portal.cache;

import nc.bs.framework.cluster.itf.AbstractClusterListener;
import nc.bs.framework.cluster.itf.ClusterMessage;
import nc.bs.framework.server.ServerConfiguration;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;

import org.apache.commons.lang.StringUtils;

/**
 * Portal����֪ͨ��Ϣ������
 * @author licza
 *
 */
public class PortalCacheClusterListener extends AbstractClusterListener{
	@Override
	public void onMessage(ClusterMessage message) {
		ClusterCacheMessage ccm = null;
		try {
			ccm = (ClusterCacheMessage) message;
		} catch (ClassCastException e) {
			LfwLogger.error("===PortalCacheClusterListener#onMessage== ��ȡ��Ϣ�Ĺ��̷�������.",e);
		}
		/**
		 * ����
		 */
		if(getIsMasterOSingle())
			return;
		
		if(ccm != null && ccm.getPluginid() != null){
			/**
			 * ����
			 */
			if(StringUtils.equals(ccm.getServer(), getServerName())) 
				return;
			
			String pluginid = null;
			try {
				pluginid = ccm.getPluginid();
				PtExtension ex = PluginManager.newIns().getExtension(pluginid);
				if(ex != null)
					ex.newInstance(IPortalCacheClusterHandle.class).doIt(ccm);
			} catch (CpbBusinessException e) {
				LfwLogger.error("===PortalCacheClusterListener#onMessage== �����ȡʧ��!���ID:" + pluginid,e);
			}
		}
	}

	 
	/**
	 * ��÷�������
	 * @return
	 */
	private String getServerName(){
		final ServerConfiguration configuration = ServerConfiguration.getServerConfiguration();
		return configuration.getServerName();
	}
	/**
	 * �Ƿ񵥵�
	 * @return
	 */
	private boolean getIsMasterOSingle(){
		final ServerConfiguration configuration = ServerConfiguration.getServerConfiguration();
		return configuration.isMaster() || configuration.isSingle();
	}
}
