package nc.portal.service.itf;

import nc.uap.lfw.login.vo.LfwTokenVO;
import nc.uap.portal.exception.PortalServiceException;
import nc.vo.pub.BusinessException;

/**
 * �����û���ѯ
 * 2010-12-9 ����08:07:02  limingf
 */

public interface ILfwTokenQryService {
	
	/**
	 * ����ĳ�����µ������û�
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public LfwTokenVO[] getOnlineUserByGroupPk(String pk_group) throws PortalServiceException;
	
	/**
	 * ���Ҳ��ּ����������û�
	 * @param pk_groups
	 * @return
	 * @throws PortalServiceException
	 */
	public LfwTokenVO[] getOnlineUserByGroupPk(String[] pk_groups) throws PortalServiceException;
	
	/**
	 * �����û�pk���Ҹ��û�������Ϣ
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	//public LfwTokenVO[] getOnlineUserByUser(String pk_user) throws PortalServiceException;
	
	/**
	 * ��ȡĳ��˾�����û�����
	 * @return
	 * @throws BusinessException
	 */
	public int getOnlineUserCount(boolean isDistinct, String pk_corp) throws BusinessException;
	
//	/**
//	 * ��������Ϣ���з�ҳ��ʾ
//	 * @param pageSize
//	 * @param pageNumber
//	 * @return
//	 * @throws BusinessException
//	 */
//	public LfwTokenVO[] getOnlineUser(int pageSize, int pageNumber) throws BusinessException;
//	
//	/**
//	 * ���ݹ�˾��ȡ�����û�
//	 * @param pageSize
//	 * @param pageNumber
//	 * @param pk_group
//	 * @return
//	 * @throws BusinessException
//	 */
//	public LfwTokenVO[] getOnlineUserByGroup(int pageSize, int pageNumber, String pk_group) throws BusinessException;

}
