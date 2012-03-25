package nc.uap.portal.task.ctrl;

import java.util.HashMap;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.itf.ICpMenuQry;
import nc.uap.cpb.org.menuitem.MenuRoot;
import nc.uap.lfw.core.comp.WebPartContentFetcherImpl;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.portal.util.freemarker.FreeMarkerTools;

public class TaskListFetcherImpl extends WebPartContentFetcherImpl {
	/**
	 * ������ڰ󶨵�������
	 */
	public static final String TASK_LIST_LINK_GROUP = "0000z010000000000005";
	/**
	 * �������ģ���ļ�
	 */
	public static final String TPL = "nc/uap/portal/task/tpl/taskList.ftl";
	/**
	 * ��ʾ�����б�
	 */
	 
	
	@Override
	public String fetchHtml(UIMeta um, PageMeta pm, LfwWidget view) {
		ICpMenuQry menuQry = NCLocator.getInstance().lookup(ICpMenuQry.class);
		MenuRoot[] linkRoots = null;
		Map<String, Object> root = new HashMap<String, Object>();
		try {
			linkRoots = menuQry.getMenuRoot("0000z010000000000002");
			root.put("LINK_ROOT", linkRoots);
			return FreeMarkerTools.contextTemplatRender(TPL, root);
			 
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return "";
	}
}
