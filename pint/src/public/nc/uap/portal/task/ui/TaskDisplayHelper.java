package nc.uap.portal.task.ui;

import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.task.itf.ITaskQryTmp;

/**
 * �΄��@ʾ�o���
 * @author licza
 *
 */
public class TaskDisplayHelper {
	   /**
	   * ��ò�ѯ���
	   * @return
	   */
	public static ITaskQryTmp getTaskQry(String extensionId){
		PtExtension extension = null;
		try {
			extension = PluginManager.newIns().getExtension(extensionId);
			return extension.newInstance(nc.uap.portal.task.itf.ITaskQryTmp.class);
		} catch (Exception e) {
			throw new LfwRuntimeException("��ȡ������ʧ��:" + e.getMessage(),e);
		}
	} 
}
