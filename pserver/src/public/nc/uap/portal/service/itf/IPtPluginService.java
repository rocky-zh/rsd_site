package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

/**
 * �������
 * 
 * @author licza
 * @since 2010��9��10��10:53:18
 */
public interface IPtPluginService {
	/**
	 * ����һ����չ��
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	public void add(PtExtensionPoint ex) throws PortalServiceException;

	/**
	 * ����һ����չ��
	 * 
	 * @param exs
	 * @throws PortalServiceException
	 */
	public void add(PtExtensionPoint[] exs) throws PortalServiceException;

	/**
	 * �޸�һ����չ��
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	public void update(PtExtensionPoint ex) throws PortalServiceException;

	/**
	 * �޸�һ����չ��
	 * 
	 * @param exs
	 * @throws PortalServiceException
	 */
	public void update(PtExtensionPoint[] exs) throws PortalServiceException;

	/**
	 * ɾ��һ����չ��
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	public void delete(PtExtensionPoint ex) throws PortalServiceException;

	/**
	 * ɾ��һ����չ��
	 * 
	 * @param exs
	 * @throws PortalServiceException
	 */
	public void delete(PtExtensionPoint[] exs) throws PortalServiceException;

	/**
	 * ����һ����չ
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	public void add(PtExtension ex) throws PortalServiceException;

	/**
	 * ����һ����չ
	 * 
	 * @param exs
	 * @throws PortalServiceException
	 */
	public void add(PtExtension[] exs) throws PortalServiceException;

	/**
	 * �޸�һ����չ
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	public void update(PtExtension ex) throws PortalServiceException;

	/**
	 * �޸�һ����չ
	 * 
	 * @param exs
	 * @throws PortalServiceException
	 */
	public void update(PtExtension[] exs) throws PortalServiceException;

	/**
	 * ɾ��һ����չ
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	public void delete(PtExtension ex) throws PortalServiceException;

	/**
	 * ɾ��һ����չ
	 * 
	 * @param exs
	 * @throws PortalServiceException
	 */
	public void delete(PtExtension[] exs) throws PortalServiceException;
	/**
	 * ����һ��ģ���µĲ��
	 * @param moduleName
	 * @throws PortalServiceException
	 */
	public void clearModule(String moduleName)  throws PortalServiceException;
}
