package nc.uap.portal.comm.setting;

import java.io.Serializable;

/**
 * Portal���÷���
 * @author licza
 *
 */
public class PtSetingCategoryVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6374068403465579400L;
	private String id;
	/** ���� **/
	private String title;
	/** ���ʻ����� **/
	private String i18nname;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getI18nname() {
		return i18nname;
	}
	public void setI18nname(String i18nname) {
		this.i18nname = i18nname;
	}

}
