package nc.uap.portal.om;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Portlet.
 * 
 * @author licza.
 * 
 */
public class Portlet implements ElementOrderly, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5283899938260883683L;
	/** PortletID.*/
	public String id;
	/**portlet名称*/
	public String name;
	/**样式*/
	public String theme;
	/**标题*/
	public String title;
	/**所在列*/
	public Integer column = 0;

	/**最大化*/
	private Boolean maxable;
	/**最小化*/
	private Boolean minable;
	/**正常*/
	private Boolean normal;

	/**渲染头部 */
	private Boolean drawhead;
	/**拖放*/
	private Boolean draggable;
	
	
	public Boolean getDraggable() {
		return draggable;
	}

	public void setDraggable(Boolean draggable) {
		this.draggable = draggable;
	}

	
	/**关闭*/
	private Boolean closeable;

	//	/**查看*/
	//	private Boolean view;
	//	/**编辑*/
	//	private Boolean edit;
	//	/**帮助*/
	//	private Boolean help;

	public Boolean getMaxable() {
		return maxable;
	}

	public void setMaxable(Boolean maxable) {
		this.maxable = maxable;
	}

	public Boolean getMinable() {
		return minable;
	}

	public void setMinable(Boolean minable) {
		this.minable = minable;
	}

	public Boolean getNormal() {
		return normal;
	}

	public void setNormal(Boolean normal) {
		this.normal = normal;
	}

	public Boolean getDrawhead() {
		return drawhead;
	}

	public void setDrawhead(Boolean drawhead) {
		this.drawhead = drawhead;
	}

	public Boolean getCloseable() {
		return closeable;
	}

	public void setCloseable(Boolean closeable) {
		this.closeable = closeable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	/**
	 * 生成可供FreeMarker传递的对象
	 * @return 
	 */
	public Map<String, Object> getSummary() {
		Map<String, Object> summary = new HashMap<String, Object>();
		summary.put("name", getName());
		summary.put("theme", getTheme());
		summary.put("id", getId());
		summary.put("title", getTitle());
		summary.put("column", getColumn());
		summary.put("maxable", getMaxable());
		summary.put("normal", getNormal());
		summary.put("drawhead", getDrawhead());
		summary.put("draggable", getDraggable());
		return summary;
	}

	/**
	 * 排序
	 */
	public int compareTo(ElementOrderly o) {
		return getColumn().compareTo(o.getColumn());
	}

	public String toString() {
		return this.getClass().getSimpleName().toLowerCase();
	}
	
}
