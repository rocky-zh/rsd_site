package nc.uap.portal.deploy.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
/**
 * PortalƤ������
 * @author licza
 *
 */
public class PortalskinVO extends SuperVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3682131130813192868L;

	/**����**/
	private String id;
	/**ģ��**/
	private String module;
	/**����**/
	private String title;
	/**ͼ��**/
	private String icon;
	/**�Ƿ񼤻�**/
	private UFBoolean isactive;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public UFBoolean getIsactive() {
		return isactive;
	}
	public void setIsactive(UFBoolean isactive) {
		this.isactive = isactive;
	}
	@Override
	public String getPKFieldName() {
		return "";
	}
	@Override
	public String getTableName() {
		return "";
	}
	
}
