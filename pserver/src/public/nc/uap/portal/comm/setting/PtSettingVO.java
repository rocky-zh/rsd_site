package nc.uap.portal.comm.setting;

import java.io.Serializable;

/**
 * Portal���ö���
 * @author licza
 *
 */
public class PtSettingVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476554037066923957L;
	private String id;
	/** ���� **/
	private String title;
	/** ���ʻ����� **/
	private String i18nname;
	/** ���� **/
	private String url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public PtSettingVO(String id, String title, String i18nname, String url) {
		super();
		this.id = id;
		this.title = title;
		this.i18nname = i18nname;
		this.url = url;
	}
	public PtSettingVO(String id, String title, String i18nname, String url,
			String categoryid) {
		super();
		this.id = id;
		this.title = title;
		this.i18nname = i18nname;
		this.url = url;
	}
	
}
