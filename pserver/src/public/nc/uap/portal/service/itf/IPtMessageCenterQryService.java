package nc.uap.portal.service.itf;

import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.vo.PtMessageVO;

/**
 * ��Ϣ���Ĳ�ѯ����ӿ�
 * 
 * @author licza
 * @version NC 6.0
 * @since 2010-11-9 20:23:46
 */
public interface IPtMessageCenterQryService {
	/**
	 * ������Ϣ��PK��ѯ����Ϣ
	 * @param pk_message
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO getMessageByPK(String pk_message) throws PortalServiceException;
	
	
	/**
	 * �������Ϣ����
	 * 
	 * @param pk_user �û�����
	 * @param systemCodes ϵͳ����
	 * 
	 * @return ϵͳ���� ����Ϣ��
	 * @throws PortalServiceException
	 */
	public Integer  getNewMessageCounts(String pk_user, String[] systemCodes) throws PortalServiceException;

	/**
	 * ��ѯ�û�����Ϣ
	 * 
	 * @param pk_user �û�����
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getMessage(String pk_user) throws PortalServiceException;

	/**
	 * �������Ϣ
	 * 
	 * @param pk_user �û�����
	 * @param systemCodes ϵͳ����
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getMessage(String pk_user, String[] systemCodes) throws PortalServiceException;

	/**
	 * �������Ϣ
	 * 
	 * @param pk_user �û�����
	 * @param systemCodes ϵͳ����
	 * @param timespan ʱ���(1= һ�� ,2= 30���� ,3=��������)
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getMessage(String pk_user, String[] systemCodes, Integer timespan) throws PortalServiceException;

	
	/**
	 * �����Ϣ
	 * 
	 * @param pk_user �û�����
	 * @param systemCodes ϵͳ����
	 * @param timespan ʱ���(1= һ�� ,2= 30���� ,3=��������)
	 * @param states ��Ϣ״̬ (0 =δ��,1=�Ѷ�,-1=��ɾ��,-2=����ɾ��)
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getMessage(String pk_user, String[] systemCodes, Integer timespan, String states[]) throws PortalServiceException;
	
	/**
	 * �����Ϣ
	 * @param pk_user �û����� 
	 * @param systemCodes ϵͳ����
	 * @param timespan ʱ���(1= һ�� ,2= 30���� ,3=��������)
	 * @param states ��Ϣ״̬ (0 =δ��,1=�Ѷ�,-1=��ɾ��,-2=����ɾ��)
	 * @param pinfo ��ҳ��Ϣ
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getMessage(String pk_user, String[] systemCodes, Integer timespan, String states[], PaginationInfo pinfo) throws PortalServiceException;

	
	/**
	 * ����û������������Ϣ
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getTrashMessage(String pk_user, PaginationInfo pinfo) throws PortalServiceException;
	
	/**
	 * ��÷�������Ϣ 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public  PtMessageVO[] getSentMessage(String pk_user, PaginationInfo pinfo) throws PortalServiceException;
	/**
	 * ����û������������Ϣ
	 * @param pk_user
	 * @param type ʱ���(1= һ�� ,2= 30���� ,3=��������)
	 * @return
	 * @throws PortalServiceException
	 */
	public PtMessageVO[] getTrashMessage(String pk_user,Integer type, PaginationInfo pinfo) throws PortalServiceException;
	/**
	 * ��÷�������Ϣ 
	 * @param pk_user
	 * @param type ʱ���(1= һ�� ,2= 30���� ,3=��������)
	 * @return
	 * @throws PortalServiceException
	 */
	public  PtMessageVO[] getSentMessage(String pk_user,Integer type, PaginationInfo pinfo) throws PortalServiceException;
	
	/**
	 * ����ѷ�����Ϣ������
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public  Integer getSentMessageCount(String pk_user) throws PortalServiceException;
	
	/**
	 * ������������Ϣ����
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public Integer getTrashMessageCount(String pk_user) throws PortalServiceException;
}
