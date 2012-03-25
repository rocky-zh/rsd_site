package nc.uap.portal.deploy.vo;

import java.io.Serializable;

import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.om.Display;
import nc.uap.portal.om.Page;
import nc.uap.portal.plugins.model.PtPlugin;

/**
 * Portalģ����Ϣ
 * <p>
 * ����ʱʹ��
 * </p>
 * 
 * @author licza
 * 
 */
public class PortalDeployDefinition implements Serializable {
	private static final long serialVersionUID = -1274059181881560697L;
	/**
	 * ģ����
	 */
	private String module;
	/** ��� **/
	private PtPlugin plugin;
	/** ����ģ�� **/
	private String[] dependsModule;

	/** ģ����ʾ���� **/
	private String title;
	/** Portlet���� **/
	private PortletApplicationDefinition portletDefineModule;
	/** PML���� **/
	private Page[] pages;
	/** ����Ա�ڵ� **/
	/** Portlet���� **/
	private Display display;
	
	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

 

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private PortalModule portalModule;

	public Page[] getPages() {
		return pages;
	}

	public void setPages(Page[] pages) {
		this.pages = pages;
	}

	public PortletApplicationDefinition getPortletDefineModule() {
		return portletDefineModule;
	}

	public void setPortletDefineModule(PortletApplicationDefinition portletDefineModule) {
		this.portletDefineModule = portletDefineModule;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public PortalModule getPortalModule() {
		return portalModule;
	}

	public void setPortalModule(PortalModule portalModule) {
		this.portalModule = portalModule;
	}

	public PtPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(PtPlugin plugin) {
		this.plugin = plugin;
	}

	public String[] getDependsModule() {
		return dependsModule;
	}

	public void setDependsModule(String[] dependsModule) {
		this.dependsModule = dependsModule;
	}

	@Override
	public String toString() {
		return "Module:" + getModule();
	}

}
