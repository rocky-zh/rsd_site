package nc.uap.portal.integrate.message;

import nc.uap.portal.integrate.message.vo.PtMessagecategoryVO;

/**
 * ��Ϣ���ĳ���
 * 
 * @author licza
 * 
 */
public class MCConstant {
	public static final PtMessagecategoryVO TYPE_NOTICE = new PtMessagecategoryVO("notice", null, "֪ͨ��Ϣ", "Notice",null);
	public static final PtMessagecategoryVO TYPE_TASK = new PtMessagecategoryVO("task", null, "��������", "Task",null);
	public static final PtMessagecategoryVO TYPE_WARING = new PtMessagecategoryVO("warning", null, "Ԥ����Ϣ", "Warning",null);
	public static final PtMessagecategoryVO TYPE_SENT = new PtMessagecategoryVO("sent", null, "�ѷ�����Ϣ", "Sent",null);
	public static final PtMessagecategoryVO TYPE_TRASH = new PtMessagecategoryVO("trash", null, "��ɾ����Ϣ", "Trash",null);
	/** ��Ϣ���Ĳ����չ�� **/
	public static final String MC_EXPOINT = "messagecenter";
	/** ��Ϣ״̬:δ�� **/
	public static final String STATE_NEW = "0";
	/** ��Ϣ״̬:�Ѷ� **/
	public static final String STATE_READ = "1";
	/** ��Ϣ״̬:��ɾ�� **/
	public static final String STATE_TRASH = "-1";
	/** ��Ϣ״̬:����ɾ�� **/
	public static final String STATE_DELETE = "-2";
	
	/** ������״̬:�����Ѵ��� **/
	public static final String STATE_WFL_CREATE = "3";
	
	/** ������״̬:����ִ���� **/
	public static final String STATE_WFL_DOING = "4";

	/** ������״̬:����ִ����� **/
	public static final String STATE_WFL_DONE = "5";
	
	/** �ռ��� **/
	public static final String MSG_INBOX = "in";
	/** ������ **/
	public static final String MSG_TRASH = "trash";
	/** ˽�� **/
	public static final String PERSON_MESSAGE = "personmessage";
	/**
	 * ������Ϣ
	 */
	public static final String TASK_MESSAGE = "taskmessage";
	
	/** �Ƿ�����Ѷ���Ϣ **/
	public static final String INCLUDE_READED_MESSAGE = "INCLUDE_READED_MESSAGE";
	/** ��Ϣ��ѯ���� **/
	public static final String MESSAGE_QRY_PARAM = "Message_Qry_Param";
	

}
