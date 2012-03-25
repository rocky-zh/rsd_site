package nc.uap.portal.deploy.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.deploy.itf.AbstractPtDeploy;
import nc.uap.portal.deploy.itf.IPtDeploy;
import nc.uap.portal.deploy.vo.PortalDeployDefinition;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.Page;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IPtPageService;
import nc.uap.portal.util.PortalPageDataWrap;
import nc.uap.portal.vo.PtPageVO;

/**
 * Portalҳ�沿��
 * @author licza
 *
 */
public class PtPageDeploy extends AbstractPtDeploy implements IPtDeploy {
	/**
	 * ����ģ���е�ҳ�沼�������ļ�
	 * 
	 * @param pd ģ����Ϣ
	 * @throws PortalServiceException
	 * @see {@link IPtDeploy#deploy(PortalDeployDefinition)}
	 */
	public void deploy(PortalDeployDefinition define) {
		 
		String module = define.getModule();
		Page[] pages = define.getPages();
		try {
			PtPageVO[] pagevos = PortalServiceUtil.getPageQryService().getSysPagesByModule(module);
			deployPml(pages, pagevos, module);
		} catch (PortalServiceException e) {
			Logger.error("Module:" + module + " deploy fail", e);
		}
	}

	/**
	 * �������ݿ��е�PML�ļ�
	 * 
	 * @param pages ����PortalPage����
	 * @param ���ݿ���PortalPage����
	 */
	public void deployPml(Page[] pages, PtPageVO[] pagevos, String module) {

		/**
		 *  ׼���ļ�
		 *  ���ð汾
		 */
		Map<String, String> localMirror = new HashMap<String, String>();
		/**
		 * ����Portalҳ��ʵ�� 
		 */
		Map<String, Page> localCopy = new HashMap<String, Page>();
		if (pages != null && pages.length > 0) {
			for (Page page : pages) {
				localMirror.put(page.getPagename(), page.getVersion());
				localCopy.put(page.getPagename(), page);
			}
		}
		Set<PtPageVO> addPage = new HashSet<PtPageVO>();
		
		Set<PtPageVO> updatePage = new HashSet<PtPageVO>();
		
		Set<PtPageVO> updatePageCache = new HashSet<PtPageVO>();
		
		Set<String> deletePage = new HashSet<String>();
		
		if (pagevos != null && pagevos.length > 0) {
			for (PtPageVO pageVO : pagevos) {
				String localPageName = pageVO.getPagename();
				Page local = localCopy.get(localPageName);
				if (local == null) {
					/**
					 * ��Ҫɾ����Portalҳ��
					 * ���°汾����Ϊ��
					 */
					deletePage.add(pageVO.getPk_portalpage());
				} else {
					if (versionCompare(localMirror.get(localPageName), pageVO.getNewversion())) {
						/**
						 * ��Ҫ���µ�Portalҳ��
						 * �����°汾Ϊ���ذ汾
						 */
						pageVO=PortalPageDataWrap.copyPage2PageVO(localCopy.get(localPageName),pageVO);
						pageVO.setNewversion(local.getVersion());
						updatePage.add(pageVO);
					}
					/**
					 * ��¡һ��Я������Ϣ��VO�������� 
					 */
					PtPageVO pageVOCache = (PtPageVO) pageVO.clone();
					pageVOCache = PortalPageDataWrap.copyPage2PageVO(local, pageVOCache);
					updatePageCache.add(pageVOCache);
					/**
					 * �Ӿ�����ɾ�� 
					 * ѭ����Ͼ�����ʣ�µ����ݼ�Ϊ����������
					 */
					localMirror.remove(localPageName);
				}
			}
		}
		
		if (!localMirror.isEmpty()) {
			/**
			 * ��Ҫ���ӵ� 
			 */
			Set<Map.Entry<String, String>> localmirrorEntrySet = localMirror.entrySet();
			for (Map.Entry<String, String> localmirrorEntry : localmirrorEntrySet) {
				String localPageName = localmirrorEntry.getKey();
				PtPageVO ppv = PortalPageDataWrap.copyPage2PageVO(localCopy.get(localPageName), new PtPageVO());
				ppv.setModule(module);
				ppv.setNewversion(ppv.getVersion());
				addPage.add(ppv);
			}
		}
		
		/**
		 * �־û������ݿ���
		 */
		IPtPageService ps = PortalServiceUtil.getPageService();
		try {
		if (!addPage.isEmpty()) 
			ps.add(addPage.toArray(new PtPageVO[0]));
		
		if (!updatePage.isEmpty())  
			ps.update(updatePage.toArray(new PtPageVO[0]));
		
		if (!deletePage.isEmpty()) 
			ps.delete(deletePage.toArray(new String[0]));
		}catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
}
