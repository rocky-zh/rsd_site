package nc.uap.portal.integrate.itf;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.constant.WebKeys;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.sso.itf.ISSOQueryService;
import nc.uap.portal.integrate.system.ProviderFetcher;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.plugins.impl.DynamicalPluginImpl;
import nc.uap.portal.user.entity.IUserVO;

/**
 * ���ɽӿ�ʵ��
 * 
 * <pre>
 * �����Ǽ��ɽӿ�Ĭ�ϵ�ʵ��
 * </pre>
 * 
 * @author licza
 * 
 */
public abstract class AbstractIntegrate extends DynamicalPluginImpl implements IBaseIntegrate {
	 
 

	@Override
	public String getSystemCode() {
		return getId();
	}

	@Override
	public String getSystemName() {
		return getTitle();
	}
	/**
	 * ��ȡsso��ѯ����
	 * 
	 * @return
	 */
	protected ISSOQueryService getSsoQryService() {
		return NCLocator.getInstance().lookup(ISSOQueryService.class);
	}

	/**
	 * ��ȡ��֤������
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	protected IWebAppLoginService getAuthService() throws PortalServiceException {
		IWebAppLoginService loginService = ProviderFetcher.getInstance()
				.getAuthService(getSystemCode());
		return loginService;
	}

	/**
	 * ��ȡSSOProviderVO
	 * 
	 * @return
	 */
	protected SSOProviderVO getProvider() {
		return ProviderFetcher.getInstance().getProvider(getSystemCode());
	}

	/**
	 * ��ȡ�û�ƾ֤
	 * 
	 * @param req
	 * @return
	 * @throws PortalServiceException
	 */
	public PtCredentialVO getCredentialVO()  {
		// ��õ�ǰportal��¼�û�
		IUserVO userVO = ((PtSessionBean) LfwRuntimeEnvironment.getLfwSessionBean()).getUser();
		String userId = userVO.getUserid();
		Logger.debug("��ǰ��¼�û�=" + userId);
		// �ж��û��Ƿ��е�¼���Ӧ�õ�ƾ֤
		PtCredentialVO credential = null;
		try {
			credential = getSsoQryService().getCredentials(userId,this.getClass().getName(), getSystemCode(), getSharelevel());
		} catch (PortalServiceException e) {
			throw new LfwRuntimeException("���credential�����쳣:" + e.getMessage() , e);
		}
		if (credential == null) {
			Logger.debug("���credentialΪnull! Userid"	+ userId);
		}
		return credential;
	}

	/**
	 * �����½ʧ��,������֤����
	 * @param portletWindId Ҫˢ�µ�Portlet����
	 * @return
	 */
	public String printAuthFailTemplet(String portletWindId) {
		StringBuffer content = new StringBuffer();
		if(!isGiveUp()){
			content.append("<script>$(function(){");
			content.append("showAuthDialog('");
			content.append(getSystemName());
			content.append("','");
			content.append(getSystemCode());
			content.append("','");
			content.append(portletWindId);
			content.append("','");
			content.append(getSharelevel());
			content.append("');");
			content.append("})</script>");
		}
		return content.toString();
	}


	@Override
	public Integer getSharelevel() {
		return WebKeys.PORTLET_SHARE_APPLICATION;
	}
	
	@Override
	public boolean isIntegrateSystem() {
		return false;
	}
	
	@Override
	public boolean isGiveUp(){
		try {
			PtCredentialVO cd = getCredentialVO();
			/**
			 * �û�����ȡ����ϵͳ�Ĺ���,����Ϊ:
			 * 1.�Ǽ���ϵͳ
			 * 2.ƾ�ݲ�Ϊ�� 
			 * 3.ƾ�ݵ�����Ϊ��
			 */
			return !isIntegrateSystem() && cd != null && cd.getPk_credential() == null;
		} catch (Exception e) {
			LfwLogger.warn(e.getMessage());
		}
		return false;
	}
}
