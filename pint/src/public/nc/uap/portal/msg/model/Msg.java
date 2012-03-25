package nc.uap.portal.msg.model;

import java.io.Serializable;


/**
 * �Ż���Ϣ
 * 
 * @author licza
 * 
 */
public class Msg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	/** �û����� **/
	private String usercode;
	/** �û����� **/
	private String username;
	/** ��Ϣ״̬ **/
	private String state;
	/** ���ȼ� **/
	private String priority;
	/** ���� **/
	private String title;
	/** ���� **/
	private String content;

	/** �����˱��� **/
	private String sendercode;
	/** ���������� **/
	private String sendername;
	/** ������� **/
	private String msgcategory;
	/** ϵͳ���� **/
	private String systemcode;
	/** ����ʱ�� **/
	private String sendtime;
	/** �Ķ�ʱ�� **/
	private String readtime;
	/** ��Ϣ���� **/
	private String msgtype;
	/** ��Ϣ������ʾֵ **/
	private String typename;

	/**
	 * ��չ��
	 */
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendercode() {
		return sendercode;
	}

	public void setSendercode(String sendercode) {
		this.sendercode = sendercode;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getMsgcategory() {
		return msgcategory;
	}

	public void setMsgcategory(String msgcategory) {
		this.msgcategory = msgcategory;
	}

	public String getSystemcode() {
		return systemcode;
	}

	public void setSystemcode(String systemcode) {
		this.systemcode = systemcode;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getReadtime() {
		return readtime;
	}

	public void setReadtime(String readtime) {
		this.readtime = readtime;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

}
