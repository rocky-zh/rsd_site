package nc.uap.portal.service.itf;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.PortletDisplayCategory;
import nc.uap.portal.vo.PtPortletVO;

/**
 * Portletע�����
 * @author licza
 *
 */
public interface IPtPortletRegistryService {

	/**
	 * ��portlet�������ü��ص�������
	 * @throws PortalServiceException 
	 * @throws JAXBException 
	 */
	public void loadPortlets() throws PortalServiceException;

	/**
	 * ���������µ�Portlet
	 * @param pageVOs
	 */
	public void cachePrepareUpdatePages(Set<PtPortletVO> portlets);

	/**
	 * �ӻ������ҵ�ģ���ʵ��·��
	 * @param moduleName
	 * @return
	 */
	public String findModuleFolder(String moduleName);

	/**
	 * ��ģ���ʵ��·�����뻺���� 
	 * @param moduleName
	 * @return
	 */
	public void addModuleFolder(String moduleName, String moduleFolder);

	/**
	 * ���Master��Module��ʼ��״̬
	 * @param moduleName
	 * @return
	 */
	public Boolean getModuleInitializeStatus();

	/**
	 * ���Master��Module��ʼ�����
	 * @param moduleName
	 * @return
	 */
	public void setModuleInitializeStatus();

	/**
	 * �ӻ������ҵ�ģ���ʵ��·��
	 * @param moduleName
	 * @return
	 */
	public Map<String, String> findModuleFolders();
	
	/**
	 * ���¼��ŵ�Portal����
	 * @param pk
	 */
	public void updateGroupCache(String pk,Boolean fire);
	
	/**
	 * ���Portlet���黺��
	 * @return
	 */
	public Map<String, PortletDisplayCategory>  getPortletDisplayCache();
 }
