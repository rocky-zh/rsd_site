package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtAnnoyVO;
import nc.uap.portal.vo.PtProtalConfigVO;

/**
 * Portal������Ϣ��ѯ����
 * 
 * @author licza
 * 
 */
public interface IPtConfigQryService {
	/**
	 * ���һ��Portal����
	 * 
	 * @param key
	 * @return
	 * @throws PortalServiceException
	 */
	public PtProtalConfigVO getPortalConfig(String key)
			throws PortalServiceException;

	/**
	 * ������е�Portal������Ϣ
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public PtProtalConfigVO[] getAllPortalConfig()
			throws PortalServiceException;

	/**
	 * ֧�������û�
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public Boolean isSupportAnnoyMousUser() throws PortalServiceException;

	/**
	 * ������������û�
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public PtAnnoyVO getAnnoymousUser() throws PortalServiceException;

}
