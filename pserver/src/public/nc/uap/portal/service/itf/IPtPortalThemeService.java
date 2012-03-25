package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServerRuntimeException;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtThemeVO;

/**
 * �������ӡ��޸�
 * 
 * @author licza
 * 
 */
public interface IPtPortalThemeService {
	/**
	 * ����һ������
	 * 
	 * @param vo
	 * @return
	 * @throws PortalServerRuntimeException
	 */
	public String add(PtThemeVO vo) throws PortalServiceException;

	/**
	 * ����һ������
	 * 
	 * @param vo
	 * @throws PortalServerRuntimeException
	 */
	public void add(PtThemeVO[] vo) throws PortalServiceException;

	/**
	 * ����һ������
	 * 
	 * @param vo
	 * @throws PortalServerRuntimeException
	 */
	public void update(PtThemeVO vo) throws PortalServiceException;
}
