package nc.uap.portal.user.entity;

import java.io.Serializable;

/**
 * Portal�û�
 * 
 * @author licza
 * @since NC6.1
 */
public interface IUserVO extends Serializable {
	

	/** �����֤��ʽ - ��̬���롣 */
	public static final Integer AUTHENMODE_STATICPWD = Integer.valueOf(0);

	/** �����֤��ʽ - CA�� */
	public static final Integer AUTHENMODE_CAAUTHEN = Integer.valueOf(1);
	
//	// �����û�
	public final static Integer USERTYPE_GROUP = Integer.valueOf(1);
//	// ��˾�û�
//	public final static Integer USERTYPE_CORP = 1;
	// ϵͳ����Ա
	public final static Integer USERTYPE_SYSADMIN = Integer.valueOf(2);
	// ���Ź���Ա
	public final static Integer USERTYPE_GROUPADMIN = Integer.valueOf(0);
//	// ��ͨ����Ա
//	public final static Integer USERTYPE_ADMIN = 4;
	/**
	 * ����û�����
	 * 
	 * @return
	 */
	String getPk_user();
	
	/**
	 * ����û�����
	 * @return
	 */
	String getPk_group();
	
	/**
	 * �������
	 * @return
	 */
	String getLangcode();
	
	/**
	 * ��õ�¼��
	 * @return
	 */
	String getUserid();
	/**
	 * ����û�����
	 * @return
	 */
	String getUsername();
	/**
	 * ����û�����
	 * @return
	 */
	String getPassword();
	/**
	 * ����û�����
	 * @return
	 */
	Integer getUsertype();
	
}
