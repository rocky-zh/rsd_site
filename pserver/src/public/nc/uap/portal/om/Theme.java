package nc.uap.portal.om;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ����
 * 
 * @author licza
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Theme", propOrder = { "id", "title", "i18nName", "lfwThemeId" })
public class Theme implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1220699553425287196L;
	/**
	 * ���
	 */
	@XmlAttribute(name = "id")
	protected String id;
	/**
	 * ����
	 */
	@XmlAttribute(name = "title")
	protected String title;
	/**
	 * �������
	 */
	@XmlAttribute(name = "i18nName")
	protected String i18nName;

	/***
	 * LFW�������
	 */
	@XmlElement(name = "lfw-theme-id")
	protected String lfwThemeId;

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

	public String getI18nName() {
		return i18nName;
	}

	public void setI18nName(String name) {
		i18nName = name;
	}

	public String getLfwThemeId() {
		return lfwThemeId;
	}

	public void setLfwThemeId(String lfwThemeId) {
		this.lfwThemeId = lfwThemeId;
	}

}
