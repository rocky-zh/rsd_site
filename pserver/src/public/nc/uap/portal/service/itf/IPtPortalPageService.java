package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtPageVO;

public interface IPtPortalPageService {
	/**
	 * ����ҳ�沼��VO
	 * 
	 * @param portalPageVO ҳ�沼��VO
	 */
	public void add(PtPageVO portalPageVO) throws PortalServiceException;

	/**
	 * ɾ��ҳ�沼��VO
	 * 
	 * @param pk_portalpage ҳ�沼��VO����
	 */
	public void delete(String pk_portalpage) throws PortalServiceException;

	/**
	 * ɾ��ҳ�沼��VO
	 * 
	 * @param module ҳ��module����
	 * @param pagename ҳ�沼������ 
	 */
	public void delete(String module,String pagename) throws PortalServiceException;
	
	/**
	 * ����ҳ�沼��VO
	 * 
	 * @param portalPageVO ҳ�沼��VO
	 */
	public void update(PtPageVO portalPageVO) throws PortalServiceException;

	/**
	 * ����һ��ҳ�沼��VO
	 * 
	 * @param pagesVos ҳ�沼��VO
	 * @return ����ҳ�沼��VO������
	 * @throws PortalServiceException
	 */
	public String[] addPortalPage(PtPageVO[] pagesVos) throws PortalServiceException;

	/**
	 * �޸�һ��ҳ�沼��VO
	 * 
	 * @param pagesVos ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public void updatePortalPage(PtPageVO[] pagesVos) throws PortalServiceException;

	/**
	 * �����û�ҳ�沼��VO
	 * 
	 * @param pk_user �û�����
	 * @param pk_group ���ű���
	 * @param newPages ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public void addUserNewPages(String pk_user, String pk_group, PtPageVO[] newPages) throws PortalServiceException;

	/**
	 * �����û�ҳ�沼��
	 * 
	 * @param portalPageVO ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	public void updateLayout(PtPageVO portalPageVO) throws PortalServiceException;
	/**
	 * ͬ���û���Portalҳ��
	 * @param pk_group
	 * @throws PortalServiceException
	 */
	public void sync(String pk_group) throws PortalServiceException;
	
	/**
	 * ɾ������ҳ�漰�����ڴ˼��ŵ��û���ҳ��
	 * @param pk_portalpage
	 * @throws PortalServiceException
	 */
	public void delGroupPage(String pk_portalpage)throws PortalServiceException;
	
	/**
	 * Ӧ�ü����µ�ҳ�浽�����û�
	 * @param pk_portalpage
	 * @throws PortalServiceException
	 */
	public void applyPageLayout(String pk_portalpage)throws PortalServiceException;
}
