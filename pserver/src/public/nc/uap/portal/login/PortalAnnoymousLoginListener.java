package nc.uap.portal.login;

import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.login.listener.AuthenticationUserVO;

/**
 * Portal������½������¼�
 * @author licza
 *
 */
public class PortalAnnoymousLoginListener extends PortalLoginListener {

	public PortalAnnoymousLoginListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}

	@Override
	protected void openTargetUrl(AuthenticationUserVO userVO) {
		String targetUrl = getTargetUrl();
		String execScript = "parent.document.loaction.href='"+targetUrl+"';";
		getGlobalContext().addExecScript(execScript);
	}

}
