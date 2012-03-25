package nc.uap.portal.portlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import nc.uap.portal.integrate.funnode.IFunIntegrationProvider;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portlet.base.FreeMarkerPortlet;

/**
 * �ҵĹ���Portlet
 * @author licza
 *
 */
public class MyFunctionPortlet extends FreeMarkerPortlet {

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("WINDOW_ID", request.getWindowID());
		List<IFunIntegrationProvider> funs = new ArrayList<IFunIntegrationProvider>();
		
		PluginManager pm = PluginManager.newIns();
		/**
		 * ����������Ϣ���
		 */
		List<PtExtension> plugins = pm.getExtensions(IFunIntegrationProvider.PID);
		if (plugins != null && !plugins.isEmpty()) {
			for (PtExtension plugin : plugins) {
				/**
				 * ��ȡ��Ϣ���ʵ��
				 */
				IFunIntegrationProvider fun = plugin.newInstance(IFunIntegrationProvider.class);
				if(fun.isVisibility())
					funs.add(fun);
			}
		}
		root.put("FUNS", funs);
		print(renderHtml(root), response);
	}

}
