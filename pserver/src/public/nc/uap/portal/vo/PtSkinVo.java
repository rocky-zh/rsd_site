package nc.uap.portal.vo;

import nc.vo.pub.SuperVO;

/**
 * ��ʽ����
 * 
 * @author dingrf
 *
 */
public class PtSkinVo extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4899063681316761083L;

	/**
	 * pk
	 */
	private String pk_skin;
	
	/**
	 * ģ������
	 */
	private String modulename;
	
	/**
	 * ����id
	 */
	private String themeid;
	
	/**
	 * ��ʽ����
	 */
	private String type;
	
	/**
	 * ��ʽid
	 */
	private String skinid;
	
	/**
	 * ��ʽ����
	 */
	private String skinname;
	
	
	public String getPk_skin() {
		return pk_skin;
	}

	public void setPk_skin(String pk_skin) {
		this.pk_skin = pk_skin;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getThemeid() {
		return themeid;
	}

	public void setThemeid(String themeid) {
		this.themeid = themeid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSkinid() {
		return skinid;
	}

	public void setSkinid(String skinid) {
		this.skinid = skinid;
	}

	public String getSkinname() {
		return skinname;
	}

	public void setSkinname(String skinname) {
		this.skinname = skinname;
	}

	@Override
	public String getPKFieldName() {
		return "pk_skin";
	}

	@Override
	public String getTableName() {
		return "pt_skin";
	}

}
