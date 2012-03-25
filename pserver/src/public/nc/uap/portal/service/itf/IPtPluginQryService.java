package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

/**
 * �����ѯ
 * 
 * @author licza
 * 
 */
public interface IPtPluginQryService {
	/**
	 * ���������չ
	 * 
	 * @param module ģ����
	 * @throws PortalServiceException
	 * @return ��չ�б�
	 */
	public PtExtension[] getAllExtension(String module) throws PortalServiceException;

	/**
	 * ���������չ
	 * 
	 * @throws PortalServiceException
	 * @return ��չ�б�
	 */
	public PtExtension[] getAllExtension() throws PortalServiceException;

	/**
	 * �����չ���µ���չ
	 * 
	 * @param point ��չ��ID
	 * @return ��չ�б�
	 * @throws PortalServiceException
	 */
	public PtExtension[] getExtensionByPoint(String point) throws PortalServiceException;

	/**
	 * ���������չ��
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public PtExtensionPoint[] getAllExtensionPoint() throws PortalServiceException;
	/**
	 * ������չ���������չ
	 * @param pk_extension
	 * @return
	 * @throws PortalServiceException
	 */
	public PtExtension getExtension(String pk_extension) throws PortalServiceException;
	
}
