package nc.uap.portal.action;

import java.util.HashMap;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.itf.ICpMenuQry;
import nc.uap.cpb.org.menuitem.MenuRoot;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.portal.util.freemarker.FreeMarkerTools;

/**
 * �������Servlet
 * 
 * @author licza
 * 
 */
@Servlet(path = "/task")
public class TaskListAction extends BaseAction {
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
	@Action
	public void list() {
		
		ICpMenuQry menuQry = NCLocator.getInstance().lookup(ICpMenuQry.class);
		MenuRoot[] linkRoots = null;
		Map<String, Object> root = new HashMap<String, Object>();
		try {
			linkRoots = menuQry.getMenuRoot("0000z010000000000002");
			root.put("LINK_ROOT", linkRoots);
			String html = FreeMarkerTools.contextTemplatRender(TPL, root);
			print(html);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
}
