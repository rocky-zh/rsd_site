package nc.uap.portal.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.filter.AbstractLfwLoginFilter;
import nc.uap.lfw.login.itf.ILoginSsoService;
import nc.uap.lfw.login.itf.LoginHelper;
import nc.uap.lfw.login.itf.LoginInterruptedException;
import nc.uap.lfw.login.listener.AuthenticationUserVO;
import nc.uap.lfw.login.util.LfwLoginFetcher;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.uap.portal.login.AnnoymousLoginConfig;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.user.entity.IUserVO;
import nc.uap.portal.util.PortalDsnameFetcher;
import nc.vo.pub.BusinessException;

/**
 * Portal��½������
 * 
 * @author licza
 * 
 */
public class PortalLoginFilter extends AbstractLfwLoginFilter {

	private static final String N = "N";
	private static final String P_MAXWIN = "p_maxwin";
	public static final String PORTAL_SYS_TYPE = "pt"; 

	@Override
	protected ILoginSsoService<? extends LfwSessionBean> getLoginSsoService() {
		return PortalServiceUtil.getServiceProvider().getLoginSsoService();
	}

	@Override
	protected String getSysType() {
		return PORTAL_SYS_TYPE;
	}

	/**
	 * �ж��û��Ƿ��Ѿ���½ ���������û�֧��
	 */
	protected boolean isUserLogin(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isLoginflag = hasLogin();
		/**
		 * �Ѿ���½
		 */
		if (isLoginflag)
			return isLoginflag;
		/**
		 * ����ds
		 */
		setDataSouceName();

		try {
			boolean annoySupport = AnnoymousLoginConfig.isSupportAnnoymous();
			if (!annoySupport)
				return false;
			/**
			 * ����ʹ�������û���½
			 */
			annoymousLogin();
			/**
			 * �Ƿ��½�ɹ�
			 */
			return hasLogin();
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * �Ƿ��½
	 * 
	 * @return
	 */
	private boolean hasLogin() {
		return LfwRuntimeEnvironment.getLfwSessionBean() != null;
	}

	/**
	 * ������½
	 * 
	 * @throws BusinessException
	 * @throws LoginInterruptedException
	 */
	private void annoymousLogin() throws BusinessException,
			LoginInterruptedException {
		IUserVO user = AnnoymousLoginConfig.getAnnoymous();
		AuthenticationUserVO userVO = new AuthenticationUserVO();
		userVO.setUserID(user.getUserid());
		String ePassword = user.getPassword();
		String pw = PortalServiceUtil.getEncodeService().decode(ePassword);
		userVO.setPassword(pw);
		Map<String, String> o = new HashMap<String, String>();
		o.put(P_MAXWIN, N);
		userVO.setExtInfo(o);
		getLoginHelper().processLogin(userVO);
	}

	/**
	 * ��������Դ����
	 */
	private void setDataSouceName() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		if (dsName == null)
			dsName = PortalDsnameFetcher.getPortalDsName();
		LfwRuntimeEnvironment.setDatasource(dsName);
	}

	public LoginHelper<PtSessionBean> getLoginHelper() {
		return (LoginHelper<PtSessionBean>) LfwLoginFetcher.getGeneralInstance().getLoginHelper();
	}
}
