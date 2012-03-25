package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtWeekendVO;

/**
 * ���������÷���ӿ�
 * @author licza
 *
 */
public interface IPtWorkDayService {
	/**
	 * ����һ����ĩ����VO
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	public PtWeekendVO add(PtWeekendVO vo) throws PortalServiceException;
	/**
	 * ����һ����ĩ����VO
	 * @param vo
	 * @throws PortalServiceException
	 */
	public void update(PtWeekendVO vo) throws PortalServiceException;
}
