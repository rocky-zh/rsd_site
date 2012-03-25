package nc.uap.portal.util;

import javax.portlet.PortletPreferences;

import nc.uap.portal.om.Page;
import nc.uap.portal.om.Portlet;


/**
 * Portalҳ����Ⱦʱ�򻷾�
 * <pre>������Portalҳ�����Portlet����Խ</pre>
 * @author licza
 *
 */
public class PortalRenderEnv {
	private static ThreadLocal<RenderContext> threadLocal = new ThreadLocal<RenderContext>(){
		protected RenderContext initialValue() {
	       return new RenderContext();
		}
	};
	/**
	 * AJAX���� 
	 */
	public static final String RENDER_REQ_TYPE_AJAX = "ajax";
	/**
	 * Get����
	 */
	public static final String RENDER_REQ_TYPE_GET = "get";
	/**
	 * ���õ�ǰҳ
	 * @param page
	 */
	public static void setCurrentPage(Page page){
		threadLocal.get().currentPage = page;
	}
	
	/**
	 * ��õ�ǰҳ
	 * @return
	 */
	public static Page getCurrentPage(){
		return threadLocal.get().currentPage;
	}
	/**
	 * �����û�����ҳ
	 * @param page
	 */
	public static void setPages(Page[] page){
		threadLocal.get().pages = page;
	}
	/**
	 * ����û�����ҳ��
	 * @return
	 */
	public static Page[] getPages(){
		return threadLocal.get().pages;
	}
	/**
	 * ��õ�ǰPortlet
	 * @return
	 */
	public static Portlet getCurrentPortlet(){
		return threadLocal.get().currentPortlet;
	}
	/**
	 * ���õ�ǰPortlet
	 * @param currentPortlet
	 */
	public static void setCurrentPortlet(Portlet currentPortlet){
		threadLocal.get().currentPortlet = currentPortlet;
	}
	
	/**
	 * ��õ�ǰPortlet����
	 * @return
	 */
	public static PortletPreferences getCurrentPortlePreferences(){
		return threadLocal.get().currentPortlePreferences;
	}
	/**
	 * ���õ�ǰPortlet����
	 * @param currentPortlePreferences
	 */
	public static void  setCurrentPortlePreferences(PortletPreferences currentPortlePreferences){
		threadLocal.get().currentPortlePreferences = currentPortlePreferences;
	}
	/**
	 * ���õ�ǰ��Ⱦ����
	 * @param renderType
	 */
	public static void setPortletRenderType(String renderType){
		threadLocal.get().renderType = renderType;
	}
	/**
	 * ��õ�ǰ��Ⱦ����
	 * @return
	 */
	public static String getPortletRenderType(){
		return threadLocal.get().renderType;
	}
	
	public static String getCurrentTheme() {
		return threadLocal.get().currentTheme;
	}
	public static void setCurrentTheme(String currentTheme) {
		threadLocal.get().currentTheme = currentTheme;
	}
	public static String getCurrentPortletTemplet() {
		return threadLocal.get().currentPortletTemplet;
	}
	public static void setCurrentPortletTemplet(String currentPortletTemplet) {
		threadLocal.get().currentPortletTemplet = currentPortletTemplet;
	}
	public static String getCurrentPortletTempletModule() {
		return threadLocal.get().currentPortletTempletModule;
	}
	public static void setCurrentPortletTempletModule(String currentPortletTempletModule) {
		threadLocal.get().currentPortletTempletModule = currentPortletTempletModule;
	}
	
	
}
/**
 * ����Portalҳ����Ϣ����
 * @author licza
 *
 */
class RenderContext{
	/**
	 * ��ǰҳ��
	 */
	protected Page currentPage;
	/**
	 * ��ǰ����ҳ��
	 */
	protected Page[] pages;
	/**
	 * ��ǰPortlet
	 */
	protected Portlet currentPortlet;
	/**
	 * ��ǰҳ����ʽ
	 */
	protected String currentTheme;
	/**
	 * ��ǰPortletƤ����ʽ
	 */
	protected String currentPortletTemplet;
	/**
	 * ��ǰPortletƤ������ģ��
	 */
	protected String currentPortletTempletModule;
	/**
	 * ��ǰPortlet����
	 */
	protected PortletPreferences currentPortlePreferences;
	/**
	 * ��Ⱦ��ʽ
	 */
	protected String renderType;
	
}