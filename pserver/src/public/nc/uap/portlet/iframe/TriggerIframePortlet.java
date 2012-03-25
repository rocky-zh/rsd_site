package nc.uap.portlet.iframe;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * IframePortlet�¼�����Portlet
 * <br/>����switchIframeContentEvent,�����ļ���Portlet��MonitorIFramePortlet��������
 * @author licza
 *
 */
public class TriggerIframePortlet extends BaseIframePortlet {

	/**
	 * Ĭ�ϵ�Action����ʽ
	 * �Զ���Actionʱ��Ҫ���ش˷���
	 * 
	 * @param request
	 * @param response
	 */
	public void processAction(ActionRequest request, ActionResponse response) {
		String action = request.getParameter(ActionRequest.ACTION_NAME);
		if("doMaxAction".equals(action)){
			response.setEvent("doMaxEvent", "doMax");
		}else{
			String url = request.getParameter("frameUrl");
			response.setEvent("switchIframeContentEvent", url);
		}
	}
	
	 
 }

