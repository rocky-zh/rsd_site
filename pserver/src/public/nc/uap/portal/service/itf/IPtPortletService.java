package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtPortletVO;
import nc.uap.portal.vo.PtPreferenceVO;

/**
 * Portlet����&�޸�
 * @author licza
 *
 */
public interface IPtPortletService {
	/**
	 * ����һ��PortletVO
	 * @param pages 
	 * @return ����Pages������
	 * @throws PortalServiceException
	 */
	String[] addPortlets(PtPortletVO[] portlets) throws PortalServiceException;

	/**
	 * �޸�һ��PortletVO
	 * @param pages
	 * @throws PortalServiceException
	 */
	void updatePortlets(PtPortletVO[] portlets) throws PortalServiceException;
	
	/**
	 * ͬ���û���Portlet
	 * @param pk_group
	 * @throws PortalServiceException
	 */
	void sync(String pk_group) throws PortalServiceException;
	/**
	 * ����һ��Portlet����
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	String addPreference(PtPreferenceVO vo)throws PortalServiceException;
	/**
	 * �޸�һ��Portlet����
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	void updatePreference(PtPreferenceVO vo)throws PortalServiceException;
	/**
	 * ɾ��һ��Portlet
	 * @param pk
	 * @throws PortalServiceException
	 */
	void delete(String pk) throws PortalServiceException;
	/**
	 * ɾ��һ��Portlet
	 * @param pk
	 * @throws PortalServiceException
	 */
	void delete(String[] pk) throws PortalServiceException;

}
