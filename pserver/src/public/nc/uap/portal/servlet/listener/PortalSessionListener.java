package nc.uap.portal.servlet.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.cil.ICilService;
import nc.login.bs.IServerEnvironmentService;
import nc.login.vo.ISystemIDConstants;
import nc.uap.lfw.core.common.SessionConstant;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.servlet.dft.LfwDefaultSessionListener;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.vo.pub.BusinessException;

/**
 * Portal�ػ�������
 * @author licza
 *
 */
public class PortalSessionListener extends LfwDefaultSessionListener {

	@Override
	public void sessionDestroyed(HttpSessionEvent reqEvent) {
		HttpSession session = reqEvent.getSession();
		
		/**
		 * ע��ʱ�Ƴ�lisence����
		 */
		PtSessionBean sb = (PtSessionBean) session.getAttribute(SessionConstant.LOGIN_SESSION_BEAN);
		if(sb == null)
			LfwLogger.error("�Ự����ʱδ�ҵ���¼��Ϣ");
		else{
			String dsName = sb.getDatasource();
			String sessionID = session.getId();
			ICilService cliService = NCLocator.getInstance().lookup(ICilService.class);
			String ownModule = cliService.getProductCode(PortalEnv.PORTALMODULECODE);
			IServerEnvironmentService ses = NCLocator.getInstance().lookup(IServerEnvironmentService.class);
			
			try {
				ses.unregisterOpenNode(ISystemIDConstants.NCPORTAL, dsName, sessionID, PortalEnv.PORTALMODULECODE, ownModule);
			} catch (BusinessException e) {
				LfwLogger.error("��ע��򿪽ڵ�ʱ�����쳣��" + e.getMessage() , e);
			}
			try {
				ses.unRegisterUserSession(ISystemIDConstants.NCPORTAL, sessionID, dsName);
			} catch (BusinessException e) {
				LfwLogger.error("��ע���¼�û�ʱ�����쳣��" + e.getMessage() , e);
			}
		}
		super.sessionDestroyed(reqEvent);
	}
}
