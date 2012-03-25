package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtPageVO;

/**
 * PortalPage��ѯ��
 * 
 * @author licza
 * @since 2010-6-21
 */
public interface IPtPortalPageQryService {
	/**
	 * ���һ��ҳ���Ƿ����
	 * @param module 
	 * @param pagename
	 * @return
	 * @throws PortalServiceException
	 */
	public Boolean isPortalPageExist(String module,String pagename) ;
	/**
	 * �������� ��ѯPortalҳ��
	 * @param clause
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPageVO[] queryPortalPageVOs(String clause) throws PortalServiceException;
	
	/**
	 * �����������ҳ�沼��VO
	 * 
	 * @param Page����
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPageVO getPortalPageVOByPK(String pk) throws PortalServiceException;

	/**
	 * ������ݿ��е�ҳ�沼��VO
	 * 
	 * @return PageVOArray
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getPageVOList() throws PortalServiceException;

	/**
	 * ����û���ҳ�沼��VO
	 * 
	 * @param pk_user
	 * @return ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getUserPages(String pk_user) throws PortalServiceException;

	/**
	 * ���ģ���ϵͳ��ҳ�沼��VO
	 * 
	 * @param module ģ����
	 * @return ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getSystemPages(String module) throws PortalServiceException;

	/**
	 * ���ģ��ļ���PortalPage
	 * 
	 * @param module ģ����
	 * @param parentid ҳ�沼��VOԭ��
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getGroupPages(String module, String parentid) throws PortalServiceException;

	/**
	 * ���ģ��ļ���ҳ�沼��VO
	 * 
	 * @return ���м��ŵ�ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getGroupPages() throws PortalServiceException;

	/**
	 * ���ģ���ϵͳҳ�沼��VO
	 * 
	 * @return ϵͳ��ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getSystemPages() throws PortalServiceException;

	/**
	 * ����û����е�ҳ�沼��VO
	 * 
	 * @return ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getUserPages() throws PortalServiceException;

	/**
	 * ��ù������͵�Portalҳ��
	 * 
	 * @param sbean �û���½��Ϣ
	 * @throws PortalServiceException
	 */
	public PtPageVO[] getAdminPages(Integer level) throws PortalServiceException;
	
	/**
	 * �����ݿ��в��Ҽ����µ�ҳ�沼��VO
	 * @param pk_group
	 * @return
	 */
	public PtPageVO[] getGroupsPage(String pk_group);
}
