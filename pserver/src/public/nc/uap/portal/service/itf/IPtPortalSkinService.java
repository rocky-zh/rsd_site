package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServerRuntimeException;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtSkinVo;

/**
 * ��ʽ���ӡ��޸�
 * 
 * @author dingrf
 * 
 */
public interface IPtPortalSkinService {
	/**
	 * ����һ����ʽ
	 * 
	 * @param vo
	 * @return
	 * @throws PortalServerRuntimeException
	 */
	public String add(PtSkinVo vo) throws PortalServiceException;
	/**
	 * ɾ����ʽ
	 * 
	 * @param vo
	 * @return
	 * @throws PortalServerRuntimeException
	 */
	public void delete(String module,String themeid,String type) throws PortalServiceException;

	/**
	 * ����һ����ʽ
	 * 
	 * @param vo
	 * @throws PortalServerRuntimeException
	 */
	public void add(PtSkinVo[] vo) throws PortalServiceException;
	
	/**
	 * ����һ����ʽ
	 * 
	 * @param vo
	 * @throws PortalServerRuntimeException
	 */
	public void update(PtSkinVo vo) throws PortalServiceException;
}
