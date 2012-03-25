package test.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * ����Portlet�¼����� �¼�������
 * 
 * @author licza
 * 
 */
public class SoyStorePortlet extends GenericPortlet {
	private static final String VIEW_PAGE = "/pages/sales.jsp";

	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		response.setContentType("text/html");
		PortletContext context = getPortletContext();
		PortletRequestDispatcher requestDispatcher = context
				.getRequestDispatcher(VIEW_PAGE);
		requestDispatcher.include(request, response);
	}

	@ProcessEvent(name = "buysoy")
	public void soldSoy(EventRequest request, EventResponse response) {
		String soyTypo = (String) request.getEvent().getValue();
		if (soyTypo.equals("5")) {
			request.setAttribute("say", "5ë�Ľ���û����");
			 response.setRenderParameter("say", "5ë�Ľ���û����");
		} else if (soyTypo.equals("1")) {
			response.setRenderParameter("say", "���úý���");
		} else {
			response.setRenderParameter("say", "û����Ҫ�����ֽ���");
		}
	}

	@ProcessEvent(name = "byebye")
	public void sayByeBye(ActionRequest request, ActionResponse response) {
		response.setRenderParameter("say", "5ë�Ľ���û����");
	}
}
