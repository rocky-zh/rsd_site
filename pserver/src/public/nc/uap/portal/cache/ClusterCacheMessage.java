package nc.uap.portal.cache;

import nc.bs.framework.cluster.itf.AbstractClusterMessage;

/**
 * Portal����֪ͨ��Ϣ
 * 
 * @author licza
 * 
 */
public class ClusterCacheMessage extends AbstractClusterMessage {
	public static final String TYPE_PAGE = "page";
	public static final String TYPE_PORTLET = "portlet";
	private String type;
	private String server;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3360554250482304662L;
	/**
	 * �����
	 */
	private String cacheKey;
	/**
	 * ���ID
	 */
	private String pluginid;
	/**
	 * ����
	 */
	private String param;


 
	public ClusterCacheMessage(String type, String cacheKey, String pluginid,
			String param) {
		super();
		this.type = type;
		this.cacheKey = cacheKey;
		this.pluginid = pluginid;
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public String getPluginid() {
		return pluginid;
	}

	public String getParam() {
		return param;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

}
