package nc.uap.portal.deploy.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.service.itf.IPtPortalPageService;
import nc.uap.portal.vo.PtPageVO;

/**
 * Ԫ�ز�������
 * 
 * @author licza
 * 
 */
public interface IPtAfterPageDeployService {
	/**
	 * ����
	 * 
	 * @param eles
	 */
	void addPages(PtPageVO[] pages, IPtPortalPageService prs,
			String[] pks) throws PortalServiceException;
	

	/**
	 * ����
	 * 
	 * @param eles
	 */

	/**
	 * ɾ��
	 * 
	 * @param eles
	 */
}
