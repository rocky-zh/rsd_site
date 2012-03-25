package nc.uap.portal.integrate.message.itf;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.portal.plugins.impl.DynamicalPluginImpl;

/**
 * ��Ϣ��������
 * 
 * @author licza
 * 
 */
public abstract class AbstractPortalMessageExchage extends DynamicalPluginImpl
 implements IPortalMessage {
	
	/**
	 * ���Portal��ǰ�û�����
	 * 
	 * @return
	 */
	public String getPkUser() {
		LfwSessionBean ses = LfwRuntimeEnvironment.getLfwSessionBean();
		if (ses != null)
			return ses.getPk_user();
		throw new LfwRuntimeException("session time out!");
	}
}
