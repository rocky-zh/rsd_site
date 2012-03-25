package nc.uap.portal.om;

import java.io.Serializable;


/**
 * portalPage ҳ��.
 * 
 * @author licza
 * 
 */
public class PageBase implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ҳ������(�ļ���)
	 */
	private String pagename;
	/**
	 * ҳ��ģ�� 
	 */
	private String module;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	/** ģ��.*/
	private String template;
	/** ����.*/
	private String title;
	/** �汾*/
	private String version;
	/**ͼ��**/
	private String icon;
	/** Ƥ��.*/
	private String skin;
	/** ֻ�� **/
	private Boolean readonly = true;
	/**����.**/
	private Layout layout;
	/**����**/
	private Integer level=new Integer(0);
	/**ҳǩ**/
	private String lable;
	/**���**/
	private Integer ordernum=new Integer(0);
	/**�Ƿ���ҳ**/
	private Boolean isdefault=false;
	/** ����Ȩ�޿��� **/
	private Boolean undercontrol = false;
	/** ����ҳ��״̬ **/
	private Boolean keepstate = false;
	/**ǿ����ʾ��ҳǩ��**/
	private Boolean forceshow = false;
	/** �����豸 **/
	private String devices;
	
	/**
	 * ��Դ��
	 */
	private String resourcebundle;
	
	/**
	 * ��������
	 */
	private String linkgroup;
	
	public Boolean getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Boolean isdefault) {
		this.isdefault = isdefault;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLable() {
			return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
 

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public Boolean getForceshow() {
		return forceshow;
	}

	public void setForceshow(Boolean forceshow) {
		this.forceshow = forceshow;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getUndercontrol() {
		return undercontrol;
	}

	public void setUndercontrol(Boolean undercontrol) {
		this.undercontrol = undercontrol;
	}

	public Boolean getKeepstate() {
		return keepstate;
	}

	public void setKeepstate(Boolean keepstate) {
		this.keepstate = keepstate;
	}

	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}

	public String getResourcebundle() {
		return resourcebundle;
	}

	public void setResourcebundle(String resourcebundle) {
		this.resourcebundle = resourcebundle;
	}

	public String getLinkgroup() {
		return linkgroup;
	}

	public void setLinkgroup(String linkgroup) {
		this.linkgroup = linkgroup;
	}
	
}
