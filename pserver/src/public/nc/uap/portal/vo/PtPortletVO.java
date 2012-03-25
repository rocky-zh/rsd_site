package nc.uap.portal.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * Portletʵ��
 * @author licza
 *
 */
public class PtPortletVO extends SuperVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8714557187311712336L;
	//primary key
	private String pk_portlet;
	private String fk_portaluser;

	//fields
	private String portletid;
	private String info;
	private String preferences;
	private String portletclass;
	private String displayname;
	//�汾
	private String version;
	/**�°汾**/
	private String newversion;
	/** ����Ȩ�޿��� **/
	private UFBoolean undercontrol;
	
	public String getNewversion() {
		return newversion;
	}

	public void setNewversion(String newversion) {
		this.newversion = newversion;
	}

	private Integer expirecache;
	// portlet����
	private String memo;
	// ������˾(since NC5.6)
	private String pk_group;
	private String initparams;
	// �Ƿ���
	private UFBoolean shareflag;
	// �Ƿ񼤻�
	private UFBoolean activeflag;
	/** Portlet�������� **/
	private String setting;
 

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	//��PortletId
	private String parentid;
	private String module;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public UFBoolean getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(UFBoolean activeflag) {
		this.activeflag = activeflag;
	}

	private String supportlocales;
	private String supportmodes;
	private String resourcebundle;

	public String getFk_portaluser() {
		return fk_portaluser;
	}

	public void setFk_portaluser(String fk_portaluser) {
		this.fk_portaluser = fk_portaluser;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPk_portlet() {
		return pk_portlet;
	}

	public void setPk_portlet(String pk_portlet) {
		this.pk_portlet = pk_portlet;
	}

	public String getPortletclass() {
		return portletclass;
	}

	public void setPortletclass(String portletclass) {
		this.portletclass = portletclass;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	@Override
	public String getTableName() {
		return "pt_portlet";
	}

	@Override
	public String getPKFieldName() {
		return "pk_portlet";
	}

	public String getPortletid() {
		return portletid;
	}

	public void setPortletid(String portletid) {
		this.portletid = portletid;
	}

	public Integer getExpirecache() {
		return expirecache;
	}

	public void setExpirecache(Integer expireCache) {
		this.expirecache = expireCache;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInitparams() {
		return initparams;
	}

	public void setInitparams(String initparams) {
		this.initparams = initparams;
	}

	public String getSupportlocales() {
		return supportlocales;
	}

	public void setSupportlocales(String supportlocales) {
		this.supportlocales = supportlocales;
	}

	public String getSupportmodes() {
		return supportmodes;
	}

	public void setSupportmodes(String supportmodes) {
		this.supportmodes = supportmodes;
	}

	public String getResourcebundle() {
		return resourcebundle;
	}

	public void setResourcebundle(String resourcebundle) {
		this.resourcebundle = resourcebundle;
	}

	public String getPk_group() {
		return pk_group;
	}

	public void setPk_group(String pk_group) {
		this.pk_group = pk_group;
	}

	public UFBoolean getShareflag() {
		return shareflag;
	}

	public void setShareflag(UFBoolean shareflag) {
		this.shareflag = shareflag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public UFBoolean getUndercontrol() {
		return undercontrol;
	}

	public void setUndercontrol(UFBoolean undercontrol) {
		this.undercontrol = undercontrol;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

}
