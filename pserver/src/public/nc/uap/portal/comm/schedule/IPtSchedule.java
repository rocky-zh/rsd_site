package nc.uap.portal.comm.schedule;

/**
 * Portal������Ȳ���ӿ�
 * 
 * <pre>
 * ��Portal��ע�����������Ҫʵ�ִ˽ӿ�
 * </pre>
 * 
 * @author licza
 * 
 */
public interface IPtSchedule {
	/**
	 * �����չ��
	 */
	public static final String PID = "PtSchedule";

	/**
	 * ���õ��ȵ�ʱ���
	 * 
	 * <pre>
	 * �Ժ������
	 * </pre>
	 * 
	 * @return
	 */
	public long getTimeSpan();

	/**
	 * ��ʼִ��
	 */
	public void go();

	/**
	 * ��ñ��
	 * 
	 * @return
	 */
	public String getID();

}
