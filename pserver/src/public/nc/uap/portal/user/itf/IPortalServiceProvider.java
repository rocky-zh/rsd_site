package nc.uap.portal.user.itf;

import nc.uap.lfw.login.itf.ILoginHandler;
import nc.uap.lfw.login.itf.ILoginSsoService;
import nc.uap.portal.deploy.itf.IPtAfterPageDeployService;
import nc.uap.portal.deploy.itf.IPtAfterPortletDeployService;
import nc.uap.portal.deploy.vo.PtSessionBean;

/**
 * Portal�û������ṩ��
 * 
 * @author licza
 * 
 */
public interface IPortalServiceProvider {
	/**
	 * ��ȡPortal�û���ѯ����
	 * 
	 * @return
	 */
	IPtUserQryService getUserQry();

	/**
	 * ��õ�¼�������
	 * 
	 * @return
	 */
	ILoginHandler<PtSessionBean> getLoginHandler();

	/**
	 * ��õ����¼�������
	 * 
	 * @return
	 */
	ILoginHandler<PtSessionBean> getSSOLoginHandler();

	/**
	 * ��õ����½����
	 * 
	 * @return
	 */
	ILoginSsoService<PtSessionBean> getLoginSsoService();


	IPtAfterPageDeployService getPageDeployService();

	IPtAfterPortletDeployService getPortletDeployService();

	IPortalPowerService getPortalPowerService();
}
