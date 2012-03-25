package nc.uap.portal.service.itf;

import java.util.Set;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.Page;
import nc.uap.portal.vo.PtPageVO;

/**
 * Portalҳ�滺��ע�����
 * 
 * @author licza
 * 
 */
public interface IPtPortalPageRegistryService {
	/**
	 * ��Portalҳ�沼��������Ϣ��ӵ�������
	 * 
	 * @throws PortalServiceException Portalҳ�沼��������Ϣ�����쳣
	 */
	public void loadPortalPages() throws PortalServiceException;

	/**
	 * �����ڸ��µ�ҳ��������Ϣ��ӵ�������
	 * 
	 * @param pageVOs Portalҳ�沼��������Ϣ
	 */
	public void cachePrepareUpdatePages(Set<PtPageVO> pageVOs);
	/**
	 * ע���û�����
	 * @param userid �û����
	 * @param page ҳ��
	 */
	public void registryUserPageCache( Page page);
	
 	
 	/**
 	 * �ӻ����л�����°��Portalҳ��
 	 * @param module ģ��
 	 * @param pagename ҳ������
 	 * @return
 	 */
 	public PtPageVO getPreUpdatePageFromCache(String module,String pagename);
}
