package nc.uap.portal.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.servlet.LfwRequestListener;
import nc.uap.portal.constant.WebKeys;
/**
 * Request���������������servlet 2.4����
 * @author dengjt
 * 2006-6-7
 */
public class PortalRequestListener extends LfwRequestListener {
	private static final String DEFAULT_MAIN_PATH = "/c";
	private static final String DEFAULT_IMAGE_PATH = "/file";
	private static final String DEFAULT_FRAME_PATH = "/frame";
	
	public void requestDestroyed(ServletRequestEvent reqEvent) {
		super.requestDestroyed(reqEvent);
	}

	public void requestInitialized(ServletRequestEvent reqEvent) {
		HttpServletRequest request = (HttpServletRequest) reqEvent.getServletRequest();
		String path = request.getServletPath();
		if(path != null && (path.endsWith(".gif") || path.endsWith(".png") || path.endsWith(".css") || path.endsWith(".js")))
			return;
		super.requestInitialized(reqEvent);
		ServletContext ctx = reqEvent.getServletContext();
//		//ֻ�е�ǰ����û�����ù�����Դ��������Portal����Դ
//		if(request.getAttribute(WebKeys.NC_RUNTIME_DS) == null){
//			String portalDataSource = PortalDsnameFetcher.getPortalDsName();
//			LfwRuntimeEnvironment.setDatasource(portalDataSource);
//		}
//		
		String rootPath = (String) request.getAttribute(WebKeys.ROOT_PATH);
		if(ctx.getAttribute(WebKeys.MAIN_PATH) == null){
			//main path, ָPortalServlet��ӳ��·��
			ctx.setAttribute(WebKeys.MAIN_PATH, rootPath + DEFAULT_MAIN_PATH);
			ctx.setAttribute(WebKeys.FRAME_PATH, rootPath + DEFAULT_FRAME_PATH);
			ctx.setAttribute(WebKeys.IMAGE_PATH, rootPath + DEFAULT_IMAGE_PATH);
		}
		
		request.setAttribute(WebKeys.MAIN_PATH, ctx.getAttribute(WebKeys.MAIN_PATH));
		request.setAttribute(WebKeys.FRAME_PATH, ctx.getAttribute(WebKeys.FRAME_PATH));
		request.setAttribute(WebKeys.IMAGE_PATH, ctx.getAttribute(WebKeys.IMAGE_PATH));
		// �ӹ��ڳ�����ȡsession
		//SecurityUtil.extractSession(request.getSession().getId());
	}
	
	@Override
	protected void initLogger() {
		Logger.init("ncportal");
	}

	@Override
	protected void resetLogger() {
		super.resetLogger();
	}
}
