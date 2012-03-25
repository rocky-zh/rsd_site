package nc.uap.portal.user.itf;

import nc.uap.lfw.login.listener.AuthenticationUserVO;
import nc.uap.portal.deploy.vo.PtSessionBean;

/**
 * �û���¼���
 * 
 * @author licza
 * 
 */
public interface IUserLoginPlugin {
	public static final String ID = "loginplugin";
	/**
	 * �û���¼ǰ����
	 * @param session
	 */
	void beforeLogin(AuthenticationUserVO userInfo);
	/**
	 * �û���¼�����
	 * @param session
	 */
	void afterLogin(PtSessionBean session);
}
