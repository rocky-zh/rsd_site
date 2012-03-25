package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtPageVO;

/**
 * ҳ�����
 * 
 * @author licza
 * 
 */
public interface IPtPageService {
	/**
	 * ����һ��ҳ��
	 * 
	 * @param vo
	 * @throws PortalServiceException
	 */
	String add(PtPageVO vo) throws PortalServiceException;

	/**
	 * ����һ��ҳ��
	 * 
	 * @param vo
	 * @throws PortalServiceException
	 */
	String[] add(PtPageVO[] vo) throws PortalServiceException;

	/**
	 * ���ݸ�ҳ�洴��һ�ݿ���
	 * 
	 * @param pk_org
	 * @param parentid
	 * @throws PortalServiceException
	 */
	String doCopy(String pk_org, String parentid) throws PortalServiceException;

	/**
	 * ����һ��ҳ��
	 * 
	 * @param vo
	 * @throws PortalServiceException
	 */
	void update(PtPageVO vo) throws PortalServiceException;

	/**
	 * ����һ��ҳ��
	 * 
	 * @param vo
	 * @throws PortalServiceException
	 */
	void update(PtPageVO[] vo) throws PortalServiceException;

	/**
	 * ɾ��һ��ҳ��
	 * @param pk
	 * @throws PortalServiceException
	 */
	void delete(String pk) throws PortalServiceException;
	/**
	 * ɾ��һ��ҳ��
	 * @param pk
	 * @throws PortalServiceException
	 */
	void delete(String[] pk) throws PortalServiceException;
	
	/**
	 * �����û�ҳ�沼��
	 * 
	 * @param portalPageVO ҳ�沼��VO
	 * @throws PortalServiceException
	 */
	void updateLayout(PtPageVO portalPageVO) throws PortalServiceException;
	
	/**
	 * ͬ���û���Portalҳ��
	 * @param pk_group
	 * @throws PortalServiceException
	 */
	void sync(String pk_group) throws PortalServiceException;
	
	/**
	 * Ӧ�ü����µ�ҳ�浽�����û�
	 * @param pk_portalpage
	 * @throws PortalServiceException
	 */
	void applyPageLayout(String pk_portalpage)throws PortalServiceException;
}
