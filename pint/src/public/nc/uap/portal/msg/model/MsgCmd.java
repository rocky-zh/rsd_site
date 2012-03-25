package nc.uap.portal.msg.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ��Ϣ��������
 * 
 * @author licza
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgCmd", propOrder = { "id", "i18nname", "title" })
public class MsgCmd implements Serializable {

	private static final long serialVersionUID = -977745852378793077L;
	/**
	 * ����id
	 */
	@XmlAttribute
	String id;
	/**
	 * �����������ʾֵ
	 */
	@XmlAttribute
	String title;
	/**
	 * ����Ĺ��ʻ���ʾֵ
	 */
	@XmlAttribute
	String i18nname;

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
