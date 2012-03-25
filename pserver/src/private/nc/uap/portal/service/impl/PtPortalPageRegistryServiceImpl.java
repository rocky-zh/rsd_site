package nc.uap.portal.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.portal.cache.PortalCacheManager;
import nc.uap.portal.constant.CacheKeys;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.Page;
import nc.uap.portal.service.itf.IPtPortalPageRegistryService;
import nc.uap.portal.util.PortalPageDataWrap;
import nc.uap.portal.util.PtUtil;
import nc.uap.portal.vo.PtPageVO;

/**
 * ע���û���ҳ�沼������������
 * 
 * @author licza
 * 
 */
public class PtPortalPageRegistryServiceImpl implements IPtPortalPageRegistryService {
	@Override
	public void loadPortalPages() throws PortalServiceException {
	//		String dsName = LfwRuntimeEnvironment.getDatasource();
	//		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.PORTTAL_PAGES_CACHE, dsName);
	//		IPtPortalPageQryService pageQry = NCLocator.getInstance().lookup(IPtPortalPageQryService.class);
	//		
	//		/**
	//		 * ��ʼ��ϵͳPortalҳ�� 
	//		 */
	//		PtPortalPageVO[] systemPages = pageQry.getSystemPages();
	//		Map<String, Page> systemPortalPages = PortalPageDataWrap.getSystemPortalPages(systemPages);
	//		cache.put(CacheKeys.SYSTEM_PAGES_CACHE, systemPortalPages);
	//
	//		/**
	//		 * ��ʼ������Portalҳ�� 
	//		 */
	//		PtPortalPageVO[] groupPages = pageQry.getGroupPages();
	//		Map<String, Map<String, PtPortalPageVO>> groupPortalPages = PortalPageDataWrap.getGroupsPortalPages(groupPages);
	//		cache.put(CacheKeys.GROUP_PAGES_CACHE, groupPortalPages);
	//
	//		/**
	//		 * ��ʼ������PortalPage����������
	//		 */
	//		Map<String, Map<String, String>> groupPortalPagesPk = PortalPageDataWrap.getGroupsPortalPagesPK(groupPages);
	//		cache.put(CacheKeys.GROUP_PAGES_PK_CACHE, groupPortalPagesPk);
	//		
	////		/**
	////		 * ���������û��Զ���Portalҳ�� ����
	////		 * ������ �û���½��׷��
	////		 */
	////		// 
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cachePrepareUpdatePages(Set<PtPageVO> pageVOs) {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_PAGES_CACHE, dsName);
		Set<PtPageVO> pageVOsCache = (Set<PtPageVO>) cache.get(CacheKeys.DEPLOY_PAGES_UPDATE_CACHE);
		if (pageVOsCache == null)
			pageVOsCache = new HashSet<PtPageVO>();

		pageVOsCache.addAll(pageVOs);
		cache.put(CacheKeys.DEPLOY_PAGES_UPDATE_CACHE, pageVOsCache);
	}

	@Override
	public void registryUserPageCache(Page page){
		 Map<String, Page> userCacheMap = (Map<String, Page>)PortalCacheManager.getUserPageCache();
		 userCacheMap.put(PortalPageDataWrap.modModuleName(page.getModule(), page.getPagename()), page);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public PtPageVO getPreUpdatePageFromCache(String module,String pagename) {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_PAGES_CACHE, dsName);
		Set<PtPageVO> pageVOsCache = (Set<PtPageVO>) cache.get(CacheKeys.DEPLOY_PAGES_UPDATE_CACHE);
		if (PtUtil.isNull(pageVOsCache))
			return null;
		 for(PtPageVO vo : pageVOsCache){
			 if(vo.getModule().equals(module) && vo.getPagename().equals(pagename))
				 return vo;
		 }
		return null;
	}
}
