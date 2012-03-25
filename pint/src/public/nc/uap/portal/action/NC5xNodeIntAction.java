package nc.uap.portal.action;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Param;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.lfw.util.JsURLEncoder;
import nc.uap.portal.constant.WebKeys;
import nc.uap.portal.exception.UserAccessException;
import nc.uap.portal.inte.PintServiceFactory;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.system.ProviderFetcher;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.util.PtUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * ����
 * @author licza
 *
 */
@Servlet(path = "/nc5x")
public class NC5xNodeIntAction extends BaseAction {
	
	/**
	 * ��ת���ڵ�
	 * @param funcode
	 * @param systemcode
	 */
	@Action
	public void fwd(@Param(name="funcode") String funcode, @Param(name="systemcode") String systemcode){
		String globalPath = LfwRuntimeEnvironment.getRootPath();
		String openNodeScriptUrl = globalPath + "/html/frame/nc5xNode.js";
		print("<html><head>");
		print("<script src='" + openNodeScriptUrl + "'></script>");
		print("<script src='/lfw/frame/script/basic/BrowserSniffer.js'></script>");
		print("<script>");
		print("if(IS_IE && !IS_IE9){window.$ = document.getElementById;}else{function $(id) {	return document.getElementById(id);	}}");
		print("window.globalPath = '" + globalPath + "';");
		print("</script>");
		print("</head>");
		print("<body onload=\"openNCNode('" + funcode + "','" + systemcode + "');\"></body>");
		print("<html>");
	}
	
	@Action
	public void execNCAppletFunction(){
		String param = request.getParameter("param");
		
		String globalPath = LfwRuntimeEnvironment.getRootPath();
		String openNodeScriptUrl = globalPath + "/html/frame/nc5xNode.js";
		print("<html><head>");
		print("<script src='" + openNodeScriptUrl + "'></script>");
		print("<script src='/lfw/frame/script/basic/BrowserSniffer.js'></script>");
		print("<script>");
		print("if(IS_IE && !IS_IE9){window.$ = document.getElementById;}else{function $(id) {	return document.getElementById(id);	}}");
		print("window.globalPath = '" + globalPath + "';");
		print("</script>");
		print("</head>");
		print("<body onload=\"execNCAppletFunction('nc.client.portal.PortalInNCClient', 'openMsgPanel', 'notice;" + param + "', 'nc57');\"></body>");
		print("<html>");
	}
	
	
	/**
	 * ������
	 */
	@Action
	public void setdomain(){
		addExecScript("document.domain = '" + request.getLocalName() + "'");
	}
	
	/**
	 * ��ȡNC�ĵ�¼�����Դ��,���ظ��ͻ��˽���ִ��
	 * @param screenHeight
	 * @param screenWidth
	 * @param systemcode
	 * @param gateUrl
	 */
	@Action
	public void fetch(@Param(name="screenHeight")String screenHeight, @Param(name="screenWidth")String screenWidth, @Param(name="systemcode")String systemcode, @Param(name="gateUrl")String gateUrl) {
		try { 
			// �Ӵ�������ȴ�ncĬ��ȫ����ʾ
			if(screenHeight != null && !screenHeight.equals(""))
				request.setAttribute("screenHeight", screenHeight);
			if(screenWidth != null && !screenWidth.equals(""))
				request.setAttribute("screenWidth", screenWidth);
			
			// ��õ�ǰportal��¼�û�
			LfwSessionBean sb = LfwRuntimeEnvironment.getLfwSessionBean();
			if(sb == null)
				throw new UserAccessException("�û�δ��½!");
			String userId = sb.getUser_code();
			if(systemcode == null || systemcode.trim().equals(""))
			{	
				LfwLogger.debug("===FetchNCPageSourceAction��:��req�л�ȡsystemcodeΪ��,����Ĭ��ֵNC!");
				systemcode = "NC";
			}
			
			// �������������gateUrl��ֱ�������url,�����ظ�����getGateUrl���е����¼
			if(PtUtil.isNull(gateUrl))
			{	
				LfwLogger.debug("===FetchNCPageSourceAction��:��ǰ��¼�û�Ϊ=" + userId);
				PtCredentialVO credential = PintServiceFactory.getSsoQryService().getCredentials(
											userId, null, systemcode, Integer.valueOf(WebKeys.PORTLET_SHARE_APPLICATION));
				if(credential == null)
					throw new LfwRuntimeException("���û�û�н���NCϵͳ��ƾ֤!");
				// �����½nc
				IWebAppLoginService loginService = ProviderFetcher.getInstance().getAuthService(systemcode);
				SSOProviderVO provider = ProviderFetcher.getInstance().getProvider(systemcode);
				gateUrl = loginService.getGateUrl(request, null, credential, provider);
			}
			
			LfwLogger.debug("===FetchNCPageSourceAction��:gateUrl=" + gateUrl);
			
			// ץȡnc��ҳ��Դ��
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(gateUrl);
			int statusCode = httpClient.executeMethod(getMethod);
			LfwLogger.debug("===FetchNCPageSourceAction��:ץȡ��NCҳ��Դ�뷵�ص�״̬��,statusCode=" + statusCode);
			String reponseStr = getMethod.getResponseBodyAsString();
			LfwLogger.debug("===FetchNCPageSourceAction��:ץȡ��NCҳ��Դ��,\n" + reponseStr);
			// ����Դ����ͻ���չʾ
			print(reponseStr);
		} catch(Exception e) { 
			LfwLogger.error("===FetchNCPageSourceAction��:��ȡNCҳ��Դ��ʱ���ִ���:" + e.getMessage(), e);
			print(JsURLEncoder.encode("ERROR:" + e.getMessage(), "UTF-8"));
		}
	}
}
