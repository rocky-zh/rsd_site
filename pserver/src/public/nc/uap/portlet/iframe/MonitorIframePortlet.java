package nc.uap.portlet.iframe;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.ProcessEvent;
/**
 * ����switchIframeContentEvent�¼���IframePortlet
 * <br/>����TriggerIframePortlet��ͬʹ��
 * <br/>�Զ��崥����PortletʱӦ��switchIframeContentEvent����support-publish-event
 * <br/><b>������һ��Page����������MonitorIframePortlet</b>
 * @author licza
 *
 */
public class MonitorIframePortlet extends BaseIframePortlet{
	/**
	 * �������Iframe�е�����¼�
	 * @param request
	 * @param response
	 */
	@ProcessEvent(name="switchIframeContentEvent")
	public void ProcessClickUrl(EventRequest request,EventResponse response){
		String url=	(String)request.getEvent().getValue();
		response.setRenderParameter(SRC_PARAM, url);
		response.setRenderParameter("if_src_type", "src");
	}
	
	
	@ProcessEvent(name="doMaxEvent")
	public void ProcessDoMax(EventRequest request,EventResponse response){
		addExecScript(response, "getContainer('#" + request.getWindowID() + "').doMax();addResizeHanlder2FrmPortlet('"+request.getWindowID()+"');");
	}

	/**
	 * ����ű��¼�
	 */
	public void processAction(ActionRequest request, ActionResponse response) {
		String action = request.getParameter("frameUrl");
		response.setEvent("execScriptEvent", action);
	}
}
