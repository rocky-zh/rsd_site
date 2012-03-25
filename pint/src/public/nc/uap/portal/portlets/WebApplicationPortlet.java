package nc.uap.portal.portlets;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.constant.WebKeys;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.inte.Constants;
import nc.uap.portal.inte.PintServiceFactory;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.system.ProviderFetcher;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.user.entity.IUserVO;
import nc.uap.portal.util.PortletSessionUtil;
import nc.uap.portlet.iframe.BaseIframePortlet;
import nc.vo.ml.NCLangRes4VoTransl;

/**
 * <b>ϵͳ����Portlet</b> ������½ƾ֤<br/>
 * @author licza
 * @since NC 6.0
 */
public class WebApplicationPortlet extends BaseIframePortlet {
	protected String systemCode = null;
	// ��portlet��ID
	protected String portletId;
	// ������
	protected String sharelevel = null;
	protected IWebAppLoginService loginService;
	protected String funcode = null;
	
	/**��֤ҳ��**/
	protected static final String AUTH_PAGE= "/core/uimeta.jsp?pageId=credential&model=nc.portal.sso.pagemodel.CredentialEditPageModel";
 
	/**
	 * ��ʼ������
	 * @param request
	 */
	public void init(PortletRequest request) {
		PortletPreferences preference = request.getPreferences();
		sharelevel = preference.getValue("share_level", String.valueOf(WebKeys.PORTLET_SHARE_APPLICATION));
		portletId = request.getWindowID();
		systemCode  = preference.getValue("system_code", null);
		funcode = preference.getValue("funcode", null);
		if (Logger.isDebugEnabled())
			Logger.debug("===WebApplicationPortlet��init����:WebApplicationPortlet����,systemCode=" + systemCode + ",sharelevel=" + sharelevel + ",portletId="
					+ portletId);
		// ���gateUrl
		if (systemCode == null) {
			Logger.warn("===WebApplicationPortlet��init����:portletId=" + portletId + ",systemCode is null!");
			throw new NullPointerException("portlet������Ϣ��û���ҵ����õ�ϵͳ����!");
		}
	}
	@Override
	protected void doDispatch(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		init(request);
		super.doDispatch(request, response);
	}
	@Override
	protected void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		try {
			Logger.debug("===WebApplicationPortlet��doEdit����:����WebApplicationPortlet��doEdit����,�����û���Ϣ¼��ҳ!");
			// ��õ�ǰportal��¼�û�
			portletId = request.getWindowID();
			SSOProviderVO provider = ProviderFetcher.getInstance().getProvider(getSystemCode());
			if(provider == null){
				LfwLogger.error("û������" + getSystemCode());
				response.getWriter().write("û������" + getSystemCode());
				return;
			}
			HttpServletRequest httpRequest = LfwRuntimeEnvironment.getWebContext().getRequest();
			request.setAttribute(HEIGHT_PARAM, "0");
			// ת����֤��ҳ��
			String contextPath=httpRequest.getContextPath();
			request.setAttribute(SRC_PARAM, PortletSessionUtil.makeAnchor(contextPath + AUTH_PAGE + "&portletId="+portletId+"&systemCode="+getSystemCode()+"&sharelevel=" + sharelevel,portletId ));
			include(request, response);
		} catch (Exception e) {
			Logger.error("===WebApplicationPortlet��doEdit����:ϵͳδ��ȷ���û��м��û������!", e);
			throw new PortletException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "WebApplicationPortlet-000000", null,
					new String[] { e.getMessage() })/* ����������ϵͳδ��ȷ���û��м��û������:{0} */);
		}
	}

	@Override
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		PortletSession portletSession = request.getPortletSession();
		try {
			portletId = request.getWindowID();
			Logger.debug("===WebApplicationPortlet��doView����:����WebApplicationPortlet��doView����!");
			HttpServletRequest httpReq = LfwRuntimeEnvironment.getWebContext().getRequest();
			HttpServletResponse httpResp = LfwRuntimeEnvironment.getWebContext().getResponse();
			PtCredentialVO credential = getCredentialVO(httpReq);
			String params = request.getParameter("_param");
			if (credential == null) {
				Logger.debug("===WebApplicationPortlet��doView����:û�л�ø��û���ƾ֤��Ϣ,��������ƾ֤ҳ��.");
				// ���û��ƾ֤,ת��EDITģʽ,���û�����������Ϣ�Ի��ƾ֤
				doEdit(request, response);
			} else {
				Logger.debug("===WebApplicationPortlet��doView����:�ɹ���ȡ�û�����ƾ֤,���ø�ƾ֤��ȡ��¼URL��Ϣ.");
				request.setAttribute("SYSTEM_CODE", getSystemCode());
				request.setAttribute("SYSTEM_NAME", getSystemName());
				SSOProviderVO provider = ProviderFetcher.getInstance().getProvider(getSystemCode());
				// ����ǰҳ��ֱ������õ�request������
				httpReq.setAttribute("screenWidth", httpReq.getSession().getAttribute("screenWidth"));
				httpReq.setAttribute("screenHeight", httpReq.getSession().getAttribute("screenHeight"));
				httpReq.setAttribute("$portletId", portletId);
				
				String gateUrl = null;
				if(funcode == null || funcode.length() == 0)
					gateUrl = getAuthService().getGateUrl(httpReq, httpResp, credential, provider);
				else
					gateUrl = getAuthService().getNodeGateUrl(httpReq, httpResp,funcode, credential, provider);
				Logger.debug("===WebApplicationPortlet��doView����:��ȡ���û��ĵ�¼ϵͳgateURL=" + gateUrl);
				request.setAttribute(WebKeys.APPLICATION_PORTLET_URL_KEY, gateUrl);
				request.getPortletSession().setAttribute("portletWindowState", request.getWindowState().toString());
				request.setAttribute(SRC_PARAM, gateUrl + (params == null ? "" : params) );
				request.setAttribute(HEIGHT_PARAM, "0");
				request.setAttribute(SRC_TYPE, "scr");
				include(request, response);
			}
		} catch (Exception e) {
			Logger.error("��ȷ��������ϵͳ��ȷ����������!", e);
			portletSession.setAttribute(Constants.CREDENTIAL_PROCESS_ERROR, "��¼��������,����Դ���ø��Ļ����û���Ϣ������!");
			doEdit(request, response);
		}
	}

	protected PtCredentialVO getCredentialVO(HttpServletRequest req) throws PortalServiceException {
		// ��õ�ǰportal��¼�û�
		IUserVO userVO = ((PtSessionBean) LfwRuntimeEnvironment.getLfwSessionBean()).getUser();
		String userId = userVO.getUserid();
		Logger.debug("===WebApplicationPortlet��getCredentialVO����:��ǰ��¼�û�=" + userId);
		// viewǰ�ж��û��Ƿ��е�¼���Ӧ�õ�ƾ֤
		PtCredentialVO credential = PintServiceFactory.getSsoQryService().getCredentials(userId, portletId, getSystemCode(), new Integer(sharelevel));
		if (Logger.isDebugEnabled()) {
			if (credential == null)
				Logger.debug("===WebApplicationPortlet��getCredentialVO����:credentialΪnull!!!userId=" + userId + ",portletId=" + portletId + ",systemCode="
						+ getSystemCode());
		}
		return credential;
	}

	@Override
	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
		super.processAction(request, response);
	}

	protected String getSystemCode() {
		return systemCode;
	}

	/**
	 * ��ȡ��֤������
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	protected IWebAppLoginService getAuthService() throws PortalServiceException {
			loginService = ProviderFetcher.getInstance().getAuthService(getSystemCode());
		return loginService;
	}

	protected String getSystemName() {
		return getProvider().getSystemName();
	}

	protected SSOProviderVO getProvider() {
		return ProviderFetcher.getInstance().getProvider(getSystemCode());
	}
	/**
	 * ����include����
	 */
	protected void include(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		PortletContext context = getPortletContext();
		if(request.getAttribute(SRC_TYPE) == null)
			request.setAttribute(SRC_TYPE, "src");
		PortletRequestDispatcher requestDispatcher = context.getRequestDispatcher(getFramePage());
		requestDispatcher.include(request, response);
	}
	
}
