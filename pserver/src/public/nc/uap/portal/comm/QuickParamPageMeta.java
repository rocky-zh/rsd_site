package nc.uap.portal.comm;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.model.PageModel;
/**
 * ������Ĳ����ŵ�websession��
 * @author licza
 *
 */
public class QuickParamPageMeta extends PageModel{

	@Override
	protected void initPageMetaStruct() {
		// �������м���WebSession��
		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		Enumeration enu = request.getParameterNames();
		WebSession ses = getWebContext().getWebSession();
		if(enu != null){
			while(enu.hasMoreElements()){
				String en = (String) enu.nextElement();
				String val = request.getParameter(en);
				ses.setAttribute(en, val);
			}
		}
	}

}
