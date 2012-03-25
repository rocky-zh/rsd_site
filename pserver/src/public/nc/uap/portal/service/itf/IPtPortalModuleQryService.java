package nc.uap.portal.service.itf;

import nc.uap.portal.container.om.ModuleApplication;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtModuleVO;

/**
 * Portalģ�����
 * 
 * @author licza
 * 
 */
public interface IPtPortalModuleQryService {
	/**
	 * ��ѯ
	 * 
	 * @param moduleId
	 * @return PtModuleVO||NULL
	 * @throws PortalServiceException
	 */
	public PtModuleVO find(String moduleId) throws PortalServiceException;

	/**
	 * �������ModuleVO
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public PtModuleVO[] findALl() throws PortalServiceException;
	/**
	 * ����ģ�������Portalģ��Ӧ��
	 * @param module
	 * @return
	 */
	public ModuleApplication getModuleAppByModuleName(String module);
	/**
	 * ����namespace���Portalģ��Ӧ��
	 * @param ns
	 * @return
	 */
	public ModuleApplication[] getModuleAppByNs(String ns);
}
