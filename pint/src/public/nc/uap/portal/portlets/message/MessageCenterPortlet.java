package nc.uap.portal.portlets.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import nc.uap.portal.msg.model.MsgCategory;
import nc.uap.portal.msg.provide.IMsgProvide;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portlet.base.FreeMarkerPortlet;

/**
 * ��Ϣ����Portlet
 * @author licza
 *
 */
public class MessageCenterPortlet extends FreeMarkerPortlet {

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("WINDOW_ID", request.getWindowID());
		
		PluginManager pm = PluginManager.newIns();
		/**
		 * ����������Ϣ���
		 */
		List<PtExtension> plugins = pm.getExtensions(IMsgProvide.PID);
		List<MsgCategory> categorys = new ArrayList<MsgCategory>();
		if (plugins != null && !plugins.isEmpty()) {
			for (PtExtension plugin : plugins) {
				/**
				 * ��ȡ��Ϣ���ʵ��
				 */
				IMsgProvide provide = plugin.newInstance(IMsgProvide.class);
				categorys.addAll(provide.getCategory());
			}
		}
		root.put("CATEGORY", categorys);
		print(renderHtml(root), response);
	}

}
