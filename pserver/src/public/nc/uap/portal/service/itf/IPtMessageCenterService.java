package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.vo.PtMessageVO;

public interface IPtMessageCenterService {
	/**
	 * ����һ����Ϣ
	 * 
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	public String add(PtMessageVO vo) throws PortalServiceException;

	/**
	 * ����һ����Ϣ
	 * 
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] add(PtMessageVO[] vo) throws PortalServiceException;
	/**
	 * ����Ϣ
	 * @param pk
	 * @throws PortalServiceException
	 */
	public void read(String pk) throws PortalServiceException;
	/**
	 * ɾ����Ϣ
	 * @param pk
	 * @throws PortalServiceException
	 */
	public void delete(String[] pk ,boolean onwer) throws PortalServiceException;
	
	/**
	 * ����Ϣɾ����"��ɾ��"�ļ���
	 * @param pk
	 * @throws PortalServiceException
	 */
	public void trash(String[] pk ,boolean onwer) throws PortalServiceException;
	

}
