package nc.uap.portal.task.itf;

import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.portal.task.ctrl.TaskQryParam;
import nc.uap.wfm.vo.WfmTaskVO;

/**
 * �����ѯ
 * @author licza
 *
 */
public interface ITaskQryTmp {
	public static final String PID = "TASK_QRY_PLUGIN";
	/**
	 * ��ѯ�����б�.
	 * @param param
	 * @param pinfo
	 * @return
	 */
	WfmTaskVO[] qryTaskList(TaskQryParam param, PaginationInfo pinfo);
	
	/**
	 * ���������URL
	 * @param pk_task
	 * @return
	 */
	String getTaskProcessUrl(String pk_task);
	String doMutiTaskProcess(String[] pk_task);
}
