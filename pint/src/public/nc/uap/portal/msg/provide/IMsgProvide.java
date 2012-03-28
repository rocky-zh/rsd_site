package nc.uap.portal.msg.provide;

import java.util.List;

import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.portal.msg.model.Msg;
import nc.uap.portal.msg.model.MsgBox;
import nc.uap.portal.msg.model.MsgCategory;
import nc.vo.pub.lang.UFDateTime;

/**
 * ��Ϣ�����ߣ�����ϵͳ�ṩ��Ϣ�Ĳ���ӿ�
 * 
 * @author licza
 * @since V6.1
 */
public interface IMsgProvide {
	/**
	 * ��չ�����
	 */
	public static final String PID = "MSG_PROVIDE";

	/**
	 * �����Ϣ
	 * 
	 * @param category ����ID
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param box ��Ϣ��
	 * @param pi ��ҳ��Ϣ
	 * @return
	 */
	public Msg[] query(String category, UFDateTime start, UFDateTime end,
			MsgBox box, PaginationInfo pi, FromWhereSQL whereSql);

	/**
	 * ���֧�ֵķ����б�
	 * 
	 * @return
	 */
	public List<MsgCategory> getCategory();

	/**
	 * ������Ϣ���������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Msg get(String id);

	/**
	 * �������Ϣ������,�޷�����ʾȫ������
	 * 
	 * @return
	 */
	public Integer getNewMessageCount(String category);

	/**
	 * ���֧�ֵĲ�������
	 * 
	 * @param id
	 * @return
	 */
	public IMsgCmd getCmd(String id);
	
	/**
	 * ����Ϣ�����ʼ��֮ǰִ�еĲ���
	 * ������ĳ���Ƿ���ʾ,�����ƵȵȲ���
	 */
	public void beforeCategoryInit(MsgCategory currentCategory);
	
	/**
	 * ����Ϣ�����ʼ��֮��ִ�еĲ���
	 */
	public void afterCategoryInit(MsgCategory currentCategory);
	
}
