package nc.uap.portal.cache;

/**
 * ��֪ͨ�Ļ���
 * 
 * @version NC6.1
 * @since NC6.0
 * @author licza
 * 
 */
public interface INotifyAbleCache {
	/**
	 * ���������ռ�
	 * 
	 * @return
	 */
	String getNameSpace();

	/**
	 * �����
	 * 
	 * @return
	 */
	String getKey();

	/**
	 * ���컺������
	 * 
	 * @return
	 */
	Object build();
}
