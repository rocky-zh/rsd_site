package nc.uap.portal.service.itf;



/**
 * Portal������Ϣע�����
 * @author licza
 *
 */
public interface IPtPortalConfigRegistryService {
	/**
	 * ��ʼ��Portal������Ϣ���㻺��
	 */
	public void init();
	
	/**
	 * ���Portal������Ϣ
	 * @param key
	 * @return
	 */
	public String get(String key);
 
 }
