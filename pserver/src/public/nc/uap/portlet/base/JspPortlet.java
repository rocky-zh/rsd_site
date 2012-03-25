package nc.uap.portlet.base;

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * ����jsp�ܹ���portletͨ��ʵ�֡�ֻ��Ҫʵ��portlet.xml�����ļ�����Ӧ��view-jsp�� jspҳ�棬�Ϳ��Գ�Ϊһ����׼portlet
 * 
 * @author dengjt
 */
public class JspPortlet extends PtBasePortlet {
	protected String editJSP;
	protected String helpJSP;
	protected String viewJSP;

	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		editJSP = getInitParameter("edit-jsp");
		helpJSP = getInitParameter("help-jsp");
		viewJSP = getInitParameter("view-jsp");
	}

	public void doEdit(RenderRequest req, RenderResponse res)
			throws IOException, PortletException {
		if (editJSP != null) {
			include(editJSP, req, res);
		} else {
			super.doEdit(req, res);
		}
	}

	public void doHelp(RenderRequest req, RenderResponse res)
			throws IOException, PortletException {
		include(helpJSP, req, res);
	}

	public void doView(RenderRequest req, RenderResponse res)
			throws IOException, PortletException {
		include(viewJSP, req, res);
	}
}
