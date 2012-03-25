package nc.uap.portal.integrate.message.listener;

import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.portal.integrate.message.MCConstant;
import nc.uap.portal.util.PortletSessionUtil;
/**
 * ��Ϣ����ҳ��ģ��
 * @author licza
 *
 */
public class MessagePageModel extends PageModel{

	@Override
	protected void initPageMetaStruct() {
		//�������е�portal�еĲ�������WebSession��
		String parentid = (String) PortletSessionUtil.get("parentid");
		String syscode =(String) PortletSessionUtil.get("syscode");
		String pluginid =(String) PortletSessionUtil.get("pluginid");
		WebSession  ses = getWebContext().getWebSession();
		ses.setAttribute("pluginid", pluginid);
		ses.setAttribute("parentid",parentid);
		ses.setAttribute("syscode",syscode);
		getWebContext().getWebSession().setAttribute(MCConstant.INCLUDE_READED_MESSAGE, Boolean.TRUE);
	}

}
