package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtProtalConfigVO;

/**
 * Portal���÷���
 * @author licza
 *
 */
public interface IPtConfigService {
	/**
	 * ����һ��Portal������Ϣ
	 * @param cfg
	 * @return
	 * @throws PortalServiceException
	 */
	public String add(PtProtalConfigVO cfg) throws PortalServiceException;
	/**
	 * ����һ��Portal������Ϣ
	 * @param cfg
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] add(PtProtalConfigVO[] cfg) throws PortalServiceException;
	/**
	 * ����һ��Portal������Ϣ
	 * @param cfg
	 * @throws PortalServiceException
	 */
	public void update(PtProtalConfigVO cfg) throws PortalServiceException;
	/**
	 * ����һ��Portal������Ϣ
	 * @param cfg
	 * @throws PortalServiceException
	 */
	public void update(PtProtalConfigVO[] cfg) throws PortalServiceException;
}
