package nc.uap.portal.menu.util;

import java.util.HashMap;
import java.util.Map;

import nc.uap.cpb.org.vos.MenuItemAdapterVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.util.freemarker.FreeMarkerTools;

/**
 * ���������ݰ�װ��
 * 
 * @author licza
 * 
 */
public class LinkGroupDataWarp {
	/** ͷ **/
	public static final String TYPE_HEAD = "head";
	/** �� **/
	public static final String TYPE_FOOT = "foot";
	/** �� **/
	public static final String TYPE_TREE = "tree";
	/** �ڵ� **/
	public static final String TYPE_NODE = "node";
	public static final String WINDOW_ID = "windowId";
	public static final String ROOT_NAME = "rootName";
	public static final String ROOT_NODE = "ROOT_NODE";
	public static final String RENDER_TYPE = "renderType";
	
	public static final String VIEW_FTL = "nc/uap/portal/portlets/linktree/linkTree.ftl";
	/**
	 * ��������Ⱦ
	 * @param vos 
	 * @param rendertype  ��ʾģʽ
	 * @param name 
	 * @return
	 */
	public static String renderLinkTreeView(MenuItemAdapterVO[] vos ,String name , String rendertype){
		Map<String,Object> root = new HashMap<String, Object>();
		if(vos != null){
			root.put(ROOT_NODE, vos);
		}
		root.put(RENDER_TYPE, rendertype);
		if(TYPE_HEAD.equals(rendertype))
			root.put(WINDOW_ID, name);
		
		if(TYPE_TREE.equals(rendertype))
			root.put(ROOT_NAME, name);
		try {
			return (FreeMarkerTools.contextTemplatRender(VIEW_FTL, root));
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage());
		}
		return null;
	}
	/**
	 * ��Ⱦ��
	 * @param vos
	 * @param title
	 * @return
	 */
	public static String renderTree(MenuItemAdapterVO[] vos ,String title){
		return renderLinkTreeView(vos, title, TYPE_TREE);
	}
	
	/**
	 * ��Ⱦ�ڵ�
	 * @param vos
	 * @return
	 */
	public static String renderNode(MenuItemAdapterVO[] vos){
		return renderLinkTreeView(vos, null, TYPE_NODE);
	}
	
	/**
	 * ��Ⱦͷ��
	 * @param windowId
	 * @return
	 */
	public static String renderHead(String windowId){
		return renderLinkTreeView(null, windowId, TYPE_HEAD);
	}
	/**
	 * ��Ⱦ�ײ�
	 * @return
	 */
	public static String renderFoot(){
		return renderLinkTreeView(null, null, TYPE_FOOT);
	}
}
