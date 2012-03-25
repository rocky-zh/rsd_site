package nc.uap.portal.cache;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * Portal�������
 * 
 * @author licza
 * 
 */
public class PortalCacheProxy {
	private static PortalCacheProxy pxy;
	
	/**
	 * �������췽��
	 */
	private PortalCacheProxy() {
		
	}
	
	/**
	 * ����һ������������ʵ��
	 * @return
	 */
	public static PortalCacheProxy newIns(){
		if(pxy == null){
			synchronized (PortalCacheProxy.class) {
				if(pxy == null)
					pxy = new PortalCacheProxy();
			}
		}
		return pxy;
	}
	
	/**
	 * ��ȡ����
	 * 
	 * @param c
	 * @return
	 */
	public Object get(INotifyAbleCache c) {
		ILfwCache cache = LfwCacheManager.getStrongCache(c.getNameSpace(),
				PortalCacheManager.getDs());
		Object o = cache.get(c.getKey());
		if (o == null) {
			try {
				o = c.build();
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
			}
			if (o == null)
				throw new NullPointerException(c.getClass().getName()
						+ "�����������ɲ��ɹ�!");
			cache.put(c.getKey(), o);
		}
		return o;
	}

	/**
	 * ��ȡ����
	 * 
	 * @param <T>
	 * @param c
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(INotifyAbleCache c, Class<T> t) {
		return (T) get(c);
	}

	/**
	 * ֪ͨ�������
	 * 
	 * @param c
	 */
	public void nodify(INotifyAbleCache c) {
		PortalCacheManager.notify(c.getNameSpace(), c.getKey());
	}
	
	/**
	 * ������Ҫ���»��ɵ�service
	 * @param <T>
	 * @param c
	 * @param t
	 * @return
	 */
	public <T> T locator(INotifyAbleCache c, Class<T> t){
		try {
			return NCLocator.getInstance().lookup(t);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
			throw new LfwRuntimeException(e.getMessage());
		}finally{
			this.nodify(c);
		}
	}
}
