package nc.uap.portal.container.service.impl;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.container.exception.PortletContainerException;
import nc.uap.portal.container.portlet.PortletRequestImpl;
import nc.uap.portal.container.portlet.PortletResponseImpl;
import nc.uap.portal.container.portlet.PortletWindow;
import nc.uap.portal.container.service.itf.FilterManager;
import nc.uap.portal.container.service.itf.PortletInvokerService;
import nc.uap.portal.servlet.PortletServlet;
import nc.uap.portal.util.PortalPageDataWrap;

/**
 * ���á��ַ�Portlet����
 * 
 * @author licza
 */
public class DefaultPortletInvokerService implements PortletInvokerService {

	/**
	 * ���ù�����Portlet.
	 * 
	 * @param context ����������
	 * 
	 * @param request
	 * 
	 * @param response
	 * @see PortletServlet
	 * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest,javax.portlet.ActionResponse)
	 */
	public void action(PortletWindow portletWindow, ActionRequest request, ActionResponse response, FilterManager filterManager) throws IOException,
			PortletException, PortletContainerException {
		if (LfwLogger.isDebugEnabled()) {
			LfwLogger.debug("Performing Action Invocation");
		}
		invoke(portletWindow, request, response, filterManager, PortletInvokerService.METHOD_ACTION);
	}

	/**
	 * ������Ⱦ��Portlet.
	 * 
	 * @param request
	 * @param response
	 * @see PortletServlet
	 * @see javax.portlet.Portlet#render(javax.portlet.RenderRequest,javax.portlet.RenderResponse)
	 */
	public void render(PortletWindow portletWindow, RenderRequest request, RenderResponse response, FilterManager filterManager) throws IOException,
			PortletException, PortletContainerException {
		if (LfwLogger.isDebugEnabled()) {
			LfwLogger.debug("Performing Render Invocation");
		}
		invoke(portletWindow, request, response, filterManager, PortletInvokerService.METHOD_RENDER);
	}

	/**
	 * �����¼���Portlet����.
	 * 
	 * @param request .
	 * @param response .
	 * @see PortletServlet
	 * @see javax.portlet.Portlet#render(javax.portlet.RenderRequest,javax.portlet.RenderResponse)
	 */
	public void event(PortletWindow portletWindow, EventRequest request, EventResponse response, FilterManager filterManager) throws IOException,
			PortletException, PortletContainerException {
		if (LfwLogger.isDebugEnabled()) {
			LfwLogger.debug("Performing Render Invocation");
		}
		invoke(portletWindow, request, response, filterManager, PortletInvokerService.METHOD_EVENT);
	}

	/**
	 * ������Դ��Portlet����.
	 * 
	 * @param request resource request.
	 * @param response resource response.
	 * @see PortletServlet
	 * @see javax.portlet.Portlet#resource(javax.portlet.ResourceRequest,javax.portlet.ResourceResponse)
	 */
	public void serveResource(PortletWindow portletWindow, ResourceRequest request, ResourceResponse response, FilterManager filterManager) throws IOException,
			PortletException, PortletContainerException {
		if (LfwLogger.isDebugEnabled()) {
			LfwLogger.debug("Performing Resource Invocation");
		}
		invoke(portletWindow, request, response, filterManager, PortletInvokerService.METHOD_RESOURCE);
	}

	public void load(PortletWindow portletWindow, PortletRequest request, PortletResponse response) throws IOException, PortletException,
			PortletContainerException {
		if (LfwLogger.isDebugEnabled()) {
			LfwLogger.debug("Performing Load Invocation.");
		}
		invoke(portletWindow, request, response, PortletInvokerService.METHOD_LOAD);
	}

	private final void invoke(PortletWindow portletWindow, PortletRequest request, PortletResponse response, Integer methodID) throws PortletException,
			IOException, PortletContainerException {

		invoke(portletWindow, request, response, null, methodID);
	}

	/**
	 * ִ�е���.
	 * 
	 * @param request portlet request
	 * @param response portlet response
	 * @param portletWindow portlet window
	 * @param methodID ������־
	 * @throws PortletException .
	 * @throws IOException .
	 */
	protected final void invoke(PortletWindow portletWindow, PortletRequest request, PortletResponse response, FilterManager filterManager, Integer methodID)
			throws PortletException, IOException, PortletContainerException {
		ServletContext servletContext = LfwRuntimeEnvironment.getServletContext();
		String uri = "/" + portletWindow.getPortletDefinition().getPortletName() + ".pt";
		if (LfwLogger.isDebugEnabled()) {
			LfwLogger.debug("Dispatching to portlet servlet at: " + uri);
		}
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher(uri);
		if (dispatcher != null) {
			HttpServletRequest containerRequest = ((PortletRequestImpl) request).getServletRequest();
			HttpServletResponse containerResponse = ((PortletResponseImpl) response).getServletResponse();
			try {
				containerRequest.setAttribute(PortletInvokerService.METHOD_ID, methodID);
				containerRequest.setAttribute(PortletInvokerService.PORTLET_REQUEST, request);
				containerRequest.setAttribute(PortletInvokerService.PORTLET_RESPONSE, response);
				containerRequest.setAttribute(PortletInvokerService.FILTER_MANAGER, filterManager);
				containerRequest.setAttribute(PortletInvokerService.TARGET_PORTLET, PortalPageDataWrap.modModuleName(portletWindow.getId().getModule(), portletWindow.getPortletDefinition().getPortletName()));

				if (methodID.equals(PortletInvokerService.METHOD_RESOURCE)) {
					dispatcher.forward(containerRequest, containerResponse);
				} else {
					dispatcher.include(containerRequest, containerResponse);
				}

			} catch (javax.servlet.UnavailableException ex) {
				String message = "Portlet unavailable";
				LfwLogger.error(message, ex);
				throw new javax.portlet.UnavailableException(message);

			} catch (javax.servlet.ServletException ex) {
				String message = ("invoke fail");
				LfwLogger.error(message);
				if (ex.getRootCause() != null && ex.getRootCause() instanceof PortletException) {
					throw (PortletException) ex.getRootCause();
				} else if (ex.getRootCause() != null) {
					throw new PortletException(ex.getRootCause());
				} else {
					throw new PortletException(ex);
				}

			} finally {
				containerRequest.removeAttribute(PortletInvokerService.METHOD_ID);
				containerRequest.removeAttribute(PortletInvokerService.PORTLET_REQUEST);
				containerRequest.removeAttribute(PortletInvokerService.PORTLET_RESPONSE);
				containerRequest.removeAttribute(PortletInvokerService.FILTER_MANAGER);
				containerRequest.removeAttribute(PortletInvokerService.TARGET_PORTLET);
			}
		} else {
			String msg = ("dispatcher fail");
			LfwLogger.error(msg);
			throw new PortletException(msg);
		}
	}
}
