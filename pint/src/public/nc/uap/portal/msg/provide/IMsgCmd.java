package nc.uap.portal.msg.provide;

/**
 * 
 * @author licza
 *
 */
public interface IMsgCmd {
	
	/**
	 * 
	 * 
	 * @param pk
	 *            ѡ�е���Ϣ����
	 * @param cmd
	 *            ����
	 * @param ctx
	 *            ��ǰҳ��������
	 */
	public abstract void execute();
}
