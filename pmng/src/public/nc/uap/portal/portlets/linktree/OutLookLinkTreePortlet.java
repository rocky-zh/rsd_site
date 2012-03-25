package nc.uap.portal.portlets.linktree;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.itf.ICpMenuQry;
import nc.uap.cpb.org.menuitem.MenuRoot;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.menu.util.LinkGroupDataWarp;
import nc.uap.portal.util.PtUtil;
import nc.uap.portlet.iframe.TriggerIframePortlet;
/**
 * OutLookʽ������Portlet
 * @author licza
 * @version 6.1
 */
public class OutLookLinkTreePortlet extends TriggerIframePortlet {

	static final String LINK_GROUP = "linkgroup";
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		StringBuffer html = new StringBuffer();

		String linkgroup = request.getPreferences().getValue("linkgroup", "0000AA10000000002SBA");
		if(PtUtil.isNull(linkgroup)){
			return;
		}
		/**
		 * ����ID
		 */
		String portletWindowId = request.getWindowID();
		MenuRoot[] roots = getRoot(linkgroup);
		
		/**
		 * ��Ⱦ��Ŀ¼
		 */
		if(roots != null && roots.length > 0){
			/**
			 * ��Ⱦͷ��
			 */
			html.append(LinkGroupDataWarp.renderHead(portletWindowId));
			for(MenuRoot root : roots) 
				html.append(LinkGroupDataWarp.renderTree(root.doGetNodeArray(), root.getTitle()));
			/**
			 * ��Ⱦ�ײ�
			 */
			html.append(LinkGroupDataWarp.renderFoot());
		}else{
			html.append("δ��ѯ��������Ϣ!");
		}
		/**
		 * �������Ӧ
		 */
		print(html, response);
	}
	
	/**
	 * ��ø�Ŀ¼
	 * @param linkgroup
	 * @return
	 */
	private MenuRoot[] getRoot(String linkgroup){
		try {
			ICpMenuQry menuQry = NCLocator.getInstance().lookup(ICpMenuQry.class);

			 return menuQry.getMenuRoot(linkgroup);
		} catch (Exception e1) {
			LfwLogger.error("��ѯ��Ŀ¼ʧ��",e1);
		}
		return new MenuRoot[0];
	}
}
