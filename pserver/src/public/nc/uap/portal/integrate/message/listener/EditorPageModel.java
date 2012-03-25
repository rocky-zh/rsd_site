package nc.uap.portal.integrate.message.listener;

import javax.servlet.http.HttpServletRequest;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.model.PageModel;

/**
 * ��Ϣ�༭��ҳ��ģ��
 * 
 * @author licza
 * 
 */
public class EditorPageModel extends PageModel {
	@Override
	protected void initPageMetaStruct() {
		// �������м���WebSession��
		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		String cmd = request.getParameter("cmd");
		String pk_message =  request.getParameter("pk_message");
		WebSession ses = getWebContext().getWebSession();
		ses.setAttribute("cmd", cmd);
		ses.setAttribute("pk_message", pk_message);
	}
}
