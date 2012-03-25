package nc.uap.portal.integrate.funnode;

import java.net.URLEncoder;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.integrate.exception.CredentialValidateException;
import nc.uap.portal.integrate.itf.AbstractIntegrate;
import nc.uap.portal.integrate.system.SSOProviderVO;

/**
 * ���������ݸ�����
 * 
 * @author licza
 * 
 */
public class LinkGrupDataWarper {
	public static final String FWD_URL = "/pt/integr/nodes/forward";
	
	/**
	 * �ڵ���ʾģ��
	 * 
	 * @return
	 * @throws CredentialValidateException
	 */
	public static String printTemplet(String systemCode,String funName ,SsoSystemNode[] nodes) {
		StringBuffer content = new StringBuffer();
		content.append("<dt><span>");
		content.append(funName);
		content.append("</span></dt>\r\n<dd><ul>");
		if (nodes != null && nodes.length > 0) {
			for (SsoSystemNode node : nodes) {
				content.append("<li><a target=\"_blank\" href=\"");
				if(systemCode == null)
					content.append(node.getNodeUrl());
				else{
					if(node.getNodeUrl() == null){
						content.append(getSsoNodeURL(LfwRuntimeEnvironment.getRootPath(), node.getNodeCode(), systemCode));
					}else{
						content.append(getSsoNodeReturnURL(LfwRuntimeEnvironment.getRootPath(), node.getNodeUrl(), systemCode));
					}
				}
				content.append("\"><img src=\"/portal/images/blackpoint.gif\" border=\"0\" />");
				content.append(node.getNodeName());
				content.append("</a></li>");
			}
		}
		content.append("</ul></dd>");
		return content.toString();
	}
	/**
	 * ��ü��ɽڵ�URL
	 * @param context
	 * @param nodeid
	 * @param systemCode
	 * @return
	 */
	public static String getSsoNodeURL(String context,String nodeid,String systemCode ){
		StringBuffer sb = new StringBuffer(context);
		sb.append(FWD_URL).append("?node=").append(nodeid).append( "&systemCode=").append(systemCode);
		return sb.toString();
	}
	
	/**
	 * ��ü���ת��URL
	 * @param context
	 * @param url
	 * @param systemCode
	 * @return
	 */
	public static String getSsoNodeReturnURL(String context,String url,String systemCode ){
		StringBuffer sb = new StringBuffer(context);
		sb.append("/pt/integr/nodes/redirect").append( "?systemCode=").append(systemCode).append("&returnurl=");
		try {
			sb.append(URLEncoder.encode(url, "utf-8"));
		} catch (Exception e) {
			LfwLogger.error("���뼯�ɽڵ�URLʱ����ִ���",e);
		}
		
		return sb.toString();
	}
	/**
	 * ��ӡ����й��ܽڵ�����
	 * 
	 * @return
	 * @throws CredentialValidateException
	 */
	public static String print(SsoSystemNode[] nodes,SSOProviderVO  provider ,String portletWindId) {
		StringBuffer content = new StringBuffer();
		content.append(printTemplet(provider.getSystemCode(),provider.getSystemName(), nodes));
		return content.toString();
	}
	/**
	 * �����½ʧ��,������֤����
	 * @param portletWindId Ҫˢ�µ�Portlet����
	 * @return
	 */
	public static String printAuthFailTemplet(String systemName ,String systemcode,String portletWindId, int sharelevel) {
		AbstractIntegrate ai = new AbstractIntegrate(){};
		ai.init(systemcode, null, systemName);
		StringBuffer content = new StringBuffer();
		if(!ai.isGiveUp()){
			content.append("<script>$(function(){");
			content.append("showAuthDialog('");
			content.append(systemName);
			content.append("','");
			content.append(systemcode);
			content.append("','");
			content.append(portletWindId);
			content.append("','");
			content.append( sharelevel);
			content.append("');");
			content.append("})</script>");
		}
		return content.toString();
	}
}
