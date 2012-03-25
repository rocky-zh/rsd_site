package nc.uap.portal.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.container.portlet.PortletSessionImpl;

/**
 * ����IframePortlet����ҳ���session�Ĳ���
 * 
 * @author licza
 * 
 */
public class PortletSessionUtil {
	private static final String ANCHOR_NAME = "$portletWind";

	/**
	 * ���ɴ�ê�����ҳ��URL
	 * 
	 * @param frameURL
	 * @param winID
	 * @return
	 */
	public static String makeAnchor(String frameURL, String winID) {
		StringBuffer sb = new StringBuffer(frameURL);
		if (frameURL.indexOf("?") != -1) {
			sb.append("&");
		} else {
			sb.append("?");
		}
		sb.append(ANCHOR_NAME);
		sb.append("=");
		sb.append(winID);
		
		if(!frameURL.startsWith("http")){
			sb.append("&token=").append(LfwRuntimeEnvironment.getLfwSessionBean().getSsotoken());
		}
		
		String host = PortalUtil.getServerUrl() + "/" + PortalEnv.getPortalName();
		//������
		sb.append("&_h3ra=").append(host);
		return sb.toString();
	}

	/**
	 * ��Portlet�л��sessionֵ
	 * 
	 * @param name
	 * @return
	 */
	public static Object get(String name) {
		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		HttpSession session = request.getSession();
		return session.getAttribute(createPortletScopedId(name));
	}

	/**
	 * Creates portlet-scoped ID for the specified attribute name
	 * 
	 * @param name
	 * @return
	 */
	protected static String createPortletScopedId(String name) {
		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		String winID = request.getParameter(ANCHOR_NAME);
		StringBuffer buffer = new StringBuffer();
		buffer.append(PortletSessionImpl.PORTLET_SCOPE_NAMESPACE);
		buffer.append(winID);
		buffer.append(PortletSessionImpl.ID_NAME_SEPARATOR);
		buffer.append(name);
		return buffer.toString();
	}
	
	
}
