package nc.uap.portal.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * �����û�����VO
 * 
 * @author licza
 * 
 */
public class PtAnnoyVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2681790948736436347L;
	/** ���� **/
	private String pk_annoy;
	/** �Ƿ����� **/
	private UFBoolean isactive;
	/** �������ű��� **/
	private String pk_group;
	/** ������������ **/
	private String groupname;

	public String getPk_annoy() {
		return pk_annoy;
	}

	public void setPk_annoy(String pk_annoy) {
		this.pk_annoy = pk_annoy;
	}

	public UFBoolean getIsactive() {
		return isactive;
	}

	public void setIsactive(UFBoolean isactive) {
		this.isactive = isactive;
	}

	public String getPk_group() {
		return pk_group;
	}

	public void setPk_group(String pk_group) {
		this.pk_group = pk_group;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Override
	public String getPKFieldName() {
		return "pk_annoy";
	}

	@Override
	public String getTableName() {
		return "pt_annoy";
	}

}
