package nc.uap.portal.integrate.funnode;

/**
 * ���ܼ���������
 * 
 * @author licza
 * 
 */
public interface IFunIntegrationProvider {

	public static final String PID = "FUN_INT";
	/**
	 * �Ƿ�ɼ� 
	 * @return
	 */
	Boolean isVisibility();
	
	/**
	 * ���ID
	 * 
	 * @return
	 */
	String getId();
	/**
	 * ���ͼ��
	 * @return
	 */
	String getIcon();
	
	/**
	 * ��ñ���
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * �����ϸ��Ϣ
	 * 
	 * @return
	 */
	String getDetail();

	/**
	 * ���ͳ����Ϣ
	 * 
	 * @return
	 */
	Integer getStat();

}
