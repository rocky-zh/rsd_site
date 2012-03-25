package nc.uap.portal.service;

import nc.bs.framework.cluster.itf.ClusterSender;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.util.LfwClassUtil;
import nc.uap.portal.service.itf.IEncodeService;
import nc.uap.portal.service.itf.IPortalDeployService;
import nc.uap.portal.service.itf.IPortalSpService;
import nc.uap.portal.service.itf.IPortalSpecService;
import nc.uap.portal.service.itf.IPtConfigQryService;
import nc.uap.portal.service.itf.IPtConfigService;
import nc.uap.portal.service.itf.IPtMessageCenterQryService;
import nc.uap.portal.service.itf.IPtMessageCenterService;
import nc.uap.portal.service.itf.IPtPageQryService;
import nc.uap.portal.service.itf.IPtPageService;
import nc.uap.portal.service.itf.IPtPluginQryService;
import nc.uap.portal.service.itf.IPtPluginService;
import nc.uap.portal.service.itf.IPtPortalConfigRegistryService;
import nc.uap.portal.service.itf.IPtPortalModuleQryService;
import nc.uap.portal.service.itf.IPtPortalModuleService;
import nc.uap.portal.service.itf.IPtPortalPageRegistryService;
import nc.uap.portal.service.itf.IPtPortalSkinQryService;
import nc.uap.portal.service.itf.IPtPortalSkinService;
import nc.uap.portal.service.itf.IPtPortalStatusService;
import nc.uap.portal.service.itf.IPtPortalThemeQryService;
import nc.uap.portal.service.itf.IPtPortalThemeService;
import nc.uap.portal.service.itf.IPtPortletQryService;
import nc.uap.portal.service.itf.IPtPortletRegistryService;
import nc.uap.portal.service.itf.IPtPortletService;
import nc.uap.portal.service.itf.IPtWorkDayQryService;
import nc.uap.portal.service.itf.IPtWorkDayService;
import nc.uap.portal.service.itf.PortletAppDescriptorService;
import nc.uap.portal.user.itf.IPortalServiceProvider;
/**
 * Portal���񹤾���
 * 
 * @author licza
 * 
 */
public final class PortalServiceUtil {
	/**
	 * ���������÷���ӿ�
	 */
	public static IPtWorkDayService getWorkDayService(){
		return NCLocator.getInstance().lookup(IPtWorkDayService.class);
	}

	
	
	public static IPortalSpService getPortalSpService() {
		return NCLocator.getInstance().lookup(IPortalSpService.class);
	}
	/**
	 * ���Portal����ע�����
	 * @return
	 */
	public static IPtPortalConfigRegistryService getConfigRegistryService(){
		return NCLocator.getInstance().lookup(IPtPortalConfigRegistryService.class);
	}
	
	/**
	 * ���Portal���÷���
	 * @return
	 */
	public static IPtConfigService getConfigService(){
		return NCLocator.getInstance().lookup(IPtConfigService.class);
	}
	
	/**
	 * ���Portal���ò�ѯ����
	 * @return
	 */
	public static IPtConfigQryService getConfigQryService(){
		return NCLocator.getInstance().lookup(IPtConfigQryService.class);
	}
	
	/**
	 * ���Portal�������
	 * @return
	 */
	public static IPortalDeployService getPortalDeployService() {
		return NCLocator.getInstance().lookup(IPortalDeployService.class);
	}
	
	/**
	 * ���Portalɨ�����
	 * @return
	 */
	public static IPortalSpecService getPortalSpecService() {
		return NCLocator.getInstance().lookup(IPortalSpecService.class);
	}
	
	/**
	 * ���Portlet�������
	 * @return
	 */
	public static PortletAppDescriptorService getPortletAppDescriptorService() {
		return NCLocator.getInstance().lookup(PortletAppDescriptorService.class);
	}
	
	/**
	 * ���Portal�������
	 * @return
	 */
	public static IEncodeService getEncodeService() {
		return NCLocator.getInstance().lookup(IEncodeService.class);
	}

	/**
	 * ���Portalҳ���ѯ����
	 * 
	 * @return IPtPortalPageQryService
	 */
	public static IPtPageQryService getPageQryService() {
		return NCLocator.getInstance().lookup(IPtPageQryService.class);
	}

	/**
	 * ���Portalҳ�����
	 * 
	 * @return IPtPortalPageService
	 */
	public static IPtPageService getPageService() {
		return NCLocator.getInstance().lookup(IPtPageService.class);
	}
	
	
	/**
	 * ��ò�ѯ���ܽڵ����
	 * @return
	 */
//	public static IPtPortalManagerAppsService getPortalManagerAppsService() {
//		return NCLocator.getInstance().lookup(IPtPortalManagerAppsService.class);
//	}

	/**
	 * ���Portalҳ�滺��ע�����
	 * 
	 * @return IPtPortalPageRegistryService
	 */
	public static IPtPortalPageRegistryService getRegistryService() {
		return NCLocator.getInstance().lookup(IPtPortalPageRegistryService.class);
	}

	/**
	 * ���Portlet��ѯ����
	 * @return IPtPortletQryService
	 */
	public static IPtPortletQryService getPortletQryService(){
		return NCLocator.getInstance().lookup(IPtPortletQryService.class);
	}

	/**
	 * ���Portlet����
	 * @return IPtPortletService
	 */
	public static IPtPortletService getPortletService(){
		return NCLocator.getInstance().lookup(IPtPortletService.class);
	}

	/**
	 * ���Portlet����ע�����
	 * @return IPtPortletRegistryService
	 */
	public static IPtPortletRegistryService getPortletRegistryService(){
		return NCLocator.getInstance().lookup(IPtPortletRegistryService.class);
	}

	/**
	 * �õ���Ϣ����
	 * 
	 * @return IPtMessageCenterService
	 */
	public  static IPtMessageCenterService getMessageService() {
		return NCLocator.getInstance().lookup(IPtMessageCenterService.class);
	}

	/**
	 * �õ���Ϣ��ѯ����
	 * 
	 * @return IPtMessageCenterQryService
	 */
	public static IPtMessageCenterQryService getMessageQryService() {
		return NCLocator.getInstance().lookup(IPtMessageCenterQryService.class);
	}

	/**
	 * ���Portal��Դ��ѯ����
	 * @return IPtResourceQryService
	
	public static IPtResourceQryService getResourceQryService(){
		return NCLocator.getInstance().lookup(IPtResourceQryService.class);
	}

	/**
	 * ���Portal��Դ����
	 * @return IPtResourceService
	
	public static IPtResourceService getResourceService(){
		return NCLocator.getInstance().lookup(IPtResourceService.class);
	}
	 */
	/**
	 * �õ��������
	 * @return
	 */
	public static IPtPortalThemeService getThemeService(){
		return NCLocator.getInstance().lookup(IPtPortalThemeService.class);
	}
	/**
	 * �õ������ѯ����
	 * @return
	 */
	public static IPtPortalThemeQryService getThemeQryService(){
		return NCLocator.getInstance().lookup(IPtPortalThemeQryService.class);
	}
	
	/**
	 * �õ���ʽ����
	 * @return
	 */
	public static IPtPortalSkinService getSkinService(){
		return NCLocator.getInstance().lookup(IPtPortalSkinService.class);
	}
	/**
	 * �õ���ʽ��ѯ����
	 * @return
	 */
	public static IPtPortalSkinQryService getSkinQryService(){
		return NCLocator.getInstance().lookup(IPtPortalSkinQryService.class);
	}
	
	/**
	 * ��ù����ղ�ѯ����
	 * @return
	 */
	public static IPtWorkDayQryService getWorkDayQryService(){
		return NCLocator.getInstance().lookup(IPtWorkDayQryService.class);
	}
	/**
	 * ��ò����ѯ����
	 * @return
	 */
	public static IPtPluginQryService getPluginQryService(){
		return NCLocator.getInstance().lookup(IPtPluginQryService.class);
	}
	/**
	 * ��ò������
	 * @return
	 */
	public static IPtPluginService getPluginService(){
		return NCLocator.getInstance().lookup(IPtPluginService.class);
	}
 
	/**
	 * ���ģ���ѯ����
	 * @return
	 */
	public static IPtPortalModuleQryService getModuleQryService(){
		return NCLocator.getInstance().lookup(IPtPortalModuleQryService.class);
	}
	/**
	 * ���ģ�����
	 * @return
	 */
	public static IPtPortalModuleService getModuleService(){
		return NCLocator.getInstance().lookup(IPtPortalModuleService.class);
	}
	/**
	 * ��ü�Ⱥ��Ϣ������
	 * @return
	 */
	public static ClusterSender getClusterSender(){
		return NCLocator.getInstance().lookup(ClusterSender.class);
	}
	
 
	public static IPortalServiceProvider getServiceProvider(){
		return (IPortalServiceProvider)LfwClassUtil.newInstance("nc.uap.portal.user.impl.PortalServiceProviderImpl");
	}
	
	/**
	 * ���Portal״̬����
	 * @return
	 */
	public static IPtPortalStatusService getPortalStatusService(){
		return NCLocator.getInstance().lookup(nc.uap.portal.service.itf.IPtPortalStatusService.class);
	}

	
}
