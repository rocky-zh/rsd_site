package nc.uap.portal.cache;

/**
 * Portal����֪ͨ��Ϣ������
 * @author licza
 *
 */
public interface IPortalCacheClusterHandle {
	public static final String PID = "CacheClusterHandle";
	/**
	 * ����֪ͨ��Ϣ
	 * @param message
	 */
	public void doIt(ClusterCacheMessage message);
}
