package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtPageVO;

/**
 * ҳ���ѯ����
 * 
 * @author licza
 * 
 */
public interface IPtPageQryService {
	/**
	 * �����������ҳ��
	 * 
	 * @param pk
	 * @return
	 * @throws PortalServiceException
	 */
	PtPageVO getPageByPk(String pk) throws PortalServiceException;

	/**
	 * �����û�������ѯҳ��
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	PtPageVO[] getPageByUser(String pk_user) throws PortalServiceException;

	/**
	 * ����ģ���ѯϵͳ��ҳ��
	 * 
	 * @param module
	 * @return
	 * @throws PortalServiceException
	 */
	PtPageVO[] getSysPagesByModule(String module) throws PortalServiceException;

	/**
	 * ����ҳ�漶���ѯҳ��
	 * 
	 * @param level
	 * @return
	 * @throws PortalServiceException
	 */
	PtPageVO[] getPageByLevel(Integer level) throws PortalServiceException;

	/**
	 * ���ݼ��Ų�ѯҳ��
	 * 
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	PtPageVO[] getPageByGroup(String pk_group) throws PortalServiceException;
}
