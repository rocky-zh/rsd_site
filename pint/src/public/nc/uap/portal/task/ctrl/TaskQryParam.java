package nc.uap.portal.task.ctrl;

import java.io.Serializable;

import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.vo.pub.lang.UFDate;

/**
 * �����ѯ����.
 * @author licza
 *
 */
public class TaskQryParam implements Serializable {

	public static final String STATUS_UNREAD = "UnRead";
	public static final String STATUS_READED = "Readed";
	public static final String STATUS_END = "End";
	
	public static final String ID_TASK = "c001";
	public static final String ID_DELIVERTASK = "c002";
	/**
	 * 
	 */
	private static final long serialVersionUID = -3579446829360237958L;

	/**
	 * ��Ҫ����
	 */
	String id;
	String status;
	String system;
	/**
	 * ��ѯ����
	 */
	String wheresql;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * �����Ҫ����
	 */
	public void reset() {
		 
		wheresql = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String category) {
		this.id = category;
		reset();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		reset();
	}

	public String getWheresql() {
		return wheresql;
	}

	public void setWheresql(String wheresql) {
		this.wheresql = wheresql;
	}

	@Override
	public String toString() {
		return "DSKEY_" + this.hashCode();
	}

	public String getInfo(){
		return "id:"+id+" status:"+status+" system:"+system+" wheresql:"+wheresql ;
	}
	
}