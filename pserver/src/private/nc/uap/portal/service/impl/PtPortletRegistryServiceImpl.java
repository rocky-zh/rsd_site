package nc.uap.portal.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBException;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.cache.ClusterCacheInvocationProxy;
import nc.uap.portal.cache.ClusterCacheMessage;
import nc.uap.portal.cache.PortalCacheManager;
import nc.uap.portal.cache.impl.PortalCacheClusterHandle;
import nc.uap.portal.constant.CacheKeys;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.PortletDisplayCategory;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IPtPortletRegistryService;
import nc.uap.portal.util.PortalPageDataWrap;
import nc.uap.portal.util.PortletDataWrap;
import nc.uap.portal.util.PtUtil;
import nc.uap.portal.vo.PtPortletVO;


/**
 * ע��Portal����
 * @author licza
 *
 */
public class PtPortletRegistryServiceImpl implements IPtPortletRegistryService {

	/**
	 * ��portlet�������ü��ص�������
	 * @throws PortalServiceException 
	 * @throws JAXBException 
	 */
	public void loadPortlets() throws PortalServiceException {
//		String dsName = LfwRuntimeEnvironment.getDatasource();
//		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.PORTLETS_CACHE, dsName);
//		IPtPortletQryService portletQry = NCLocator.getInstance().lookup(IPtPortletQryService.class);
//		PtPortletVO[] systemPortletVOs = portletQry.getSystemPortlet();
//		Map<String, PortletDefinition> systemPortlets = PortletDataWrap.getSystemPortlets(systemPortletVOs);
//		//ϵͳPortlet
//		cache.put(CacheKeys.SYSTEM_PORTLETS_CACHE, systemPortlets);
//		PtPortletVO[] groupPortletVOs = portletQry.getGroupPortlets();
//		//����
//		Map<String, Map<String, PortletDefinition>> groupPortlets = PortletDataWrap.getGroupsPortlets(groupPortletVOs);
//		cache.put(CacheKeys.GROUP_PORTLETS_CACHE, groupPortlets);
		//�û��Զ���
//		PtPortletVO[] userPortletVOs = portletQry.getUserDiyPortlets();
//		Map<String, Map<String, PortletDefinition>> userPortlets = PortletDataWrap.getUsersDiyPortlets(userPortletVOs);
//		cache.put(CacheKeys.USER_DIY_PORTLETS_CACHE, userPortlets);

//		PtPortletPreferencesVO[] preferenceVOs = portletQry.getPortletPreferences();
//		Map<String, Map<String, Map<String, Preferences>>> preferences;
//		try {
//			preferences = PortletDataWrap.getUsersPortletPreferences(preferenceVOs);
//			cache.put(CacheKeys.USER_PORTLET_PREFERENCE_CACHE, preferences);
//		} catch (JAXBException e) {
//			Logger.error(e);
//			throw new PortalServiceException(e);
//		}
	}

	/**
	 * ���������µ�Portlet
	 * @param pageVOs
	 */
	@SuppressWarnings("unchecked")
	public void cachePrepareUpdatePages(Set<PtPortletVO> portlets) {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_PORLET_CACHE, dsName);
		Set<PtPortletVO> pageVOsCache = (Set<PtPortletVO>) cache.get(CacheKeys.DEPLOY_PORLET_UPDATE_CACHE);
		if (pageVOsCache == null)
			pageVOsCache = new HashSet<PtPortletVO>();
		pageVOsCache.addAll(portlets);
		cache.put(CacheKeys.DEPLOY_PORLET_UPDATE_CACHE, pageVOsCache);
	}

	/**
	 * �ӻ������ҵ�ģ���ʵ��·��
	 * @param moduleName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findModuleFolder(String moduleName) {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_MODULE_FOLER_CACHE, dsName);
		Map<String, String> moduleFolders = (Map<String, String>) cache.get(CacheKeys.DEPLOY_MODULE_FOLER_CACHE);
		if (moduleFolders == null)
			return null;
		return moduleFolders.get(moduleName);
	}

	/**
	 * �ӻ������ҵ�ģ���ʵ��·��
	 * @param moduleName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findModuleFolders() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_MODULE_FOLER_CACHE, dsName);
		Map<String, String> moduleFolders = (Map<String, String>) cache.get(CacheKeys.DEPLOY_MODULE_FOLER_CACHE);
		return moduleFolders;
	}

	/**
	 * ��ģ���ʵ��·�����뻺���� 
	 * @param moduleName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addModuleFolder(String moduleName, String moduleFolder) {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_MODULE_FOLER_CACHE, dsName);
		Map<String, String> moduleFolders = (Map<String, String>) cache.get(CacheKeys.DEPLOY_MODULE_FOLER_CACHE);
		if (moduleFolders == null) {
			moduleFolders = new HashMap<String, String>();
		}
		moduleFolders.put(moduleName, moduleFolder);
		cache.put(CacheKeys.DEPLOY_MODULE_FOLER_CACHE, moduleFolders);
	}

	/**
	 * ���Master��Module��ʼ��״̬
	 * @param moduleName
	 * @return
	 */
	public Boolean getModuleInitializeStatus() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		LfwLogger.debug("��ȡPortalserver��ʼ��״̬; ����Դ : " + dsName);
		ILfwCache cache = LfwCacheManager.getStrongCache(CacheKeys.DEPLOY_MODULE_STATUS, dsName);
		Boolean status = Boolean.parseBoolean((String) cache.get(CacheKeys.DEPLOY_MODULE_STATUS));
		return status != null && status;
	}

	/**
	 * ���Master��Module��ʼ�����
	 * @param moduleName
	 * @return
	 */
	public void setModuleInitializeStatus() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		 
	}

	@Override
	public void updateGroupCache(String pk,Boolean fire) {
		try {
			PtPortletVO[] vos = PortalServiceUtil.getPortletQryService().getGroupPortlets(pk);
			if(!PtUtil.isNull(vos)){
				Map<String, PortletDefinition> cacheMap = new ConcurrentHashMap<String, PortletDefinition>();
				for(PtPortletVO vo : vos){
					String key = PortalPageDataWrap.modModuleName(vo.getModule(), vo.getPortletid());
					cacheMap.put(key, PortletDataWrap.warpDefinition(vo));
				}
				((Map<String, Map<String, PortletDefinition>>)PortalCacheManager.getGroupPortletsCache()).put(pk, cacheMap);
			}
			/**
			 * ֪ͨ��������
			 */
			if(fire){
				ClusterCacheMessage ccm = new ClusterCacheMessage(ClusterCacheMessage.TYPE_PORTLET,CacheKeys.GROUP_PORTLETS_CACHE,PortalCacheClusterHandle.class.getSimpleName(),pk);
				ClusterCacheInvocationProxy.fire(ccm);
			}
		} catch (PortalServiceException e) {
			LfwLogger.error("����Portlet����ͬ��ʧ��",e);
		}
	} 
	
	/**
	 * ���Portlet���黺��
	 * @return
	 */
	public Map<String, PortletDisplayCategory> getPortletDisplayCache(){
		return PortalCacheManager.getPortletDisplays();
	}
}
