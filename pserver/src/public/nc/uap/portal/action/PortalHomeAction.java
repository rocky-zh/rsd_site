package nc.uap.portal.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import nc.uap.cpb.log.LoginLogHelper;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Param;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.lfw.util.LfwClassUtil;
import nc.uap.portal.cache.PortalCacheManager;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.uap.portal.exception.PortalPassWordException;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.exception.UserAccessException;
import nc.uap.portal.om.Page;
import nc.uap.portal.om.PageMenu;
import nc.uap.portal.om.Portlet;
import nc.uap.portal.om.PortletDisplayCategory;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.util.PageBuilder;
import nc.uap.portal.util.PortalPageDataWrap;
import nc.uap.portal.util.PortletDataWrap;
import nc.uap.portal.util.freemarker.FreeMarkerTools;
import nc.uap.portal.vo.PtPageVO;
import nc.vo.pub.lang.UFDateTime;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;


/**
 * ��ʾPortalҳ��
 * 
 * @author licza
 * 
 */
@Servlet(path = "/home")
public class PortalHomeAction extends BaseAction {
	
	/**
	 * ��ʾ�û���ҳ
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Action
	public void index() throws IOException  {
		try {
			Page[] myPages = PortalPageDataWrap.getUserPages();
			/**
			 * �����ҳ
			 */
			Page page = PortalPageDataWrap.getUserDefaultPage(myPages);
			/**
			 * �˵�
			 */
			PageMenu menu = PortalPageDataWrap.getUserMenu(myPages);
			if(menu.isKeepState()){
				//TODO 
			}
			/**
			 * ��ӡ���ɵ�ҳ��
			 */
			print(PageBuilder.render(page, myPages,menu));
			HttpSession ses = request.getSession();
			
			/**
			 * �״ε�½�ж�
			 */
			String firstForce = (String)ses.getAttribute("firstForce");
			if(firstForce != null && firstForce.equals("1")) 
				throw new PortalPassWordException("�״ε�½,���޸�����!");
 			
			/**
			 * �������
			 */
			String sercutityForce = (String)ses.getAttribute("sercutityForce");
			if(sercutityForce != null && sercutityForce.equals("1")) 
				throw new PortalPassWordException("�������޸�����");
			
			/**
			 * �������
			 */
			String outdateForce = (String)ses.getAttribute("outdateForce");
			if(outdateForce != null && outdateForce.equals("1")) 
				throw new PortalPassWordException("�����ѹ���,�������޸�����!");
	 
		} 
		catch (PortalServiceException e) {
			throw new LfwRuntimeException(e.getMessage(), "ģ����Ⱦ�쳣", e);
		} 
		catch (UserAccessException e) {
			logout();
		}
		catch (LfwRuntimeException e) {
			print("<h1>"+e.getMessage()+"</h1><a href='/portal/core/login.jsp?pageId=login'>BACK</a>");
		}
		catch(PortalPassWordException e){
			String chagePassWdURL = request.getContextPath()+"/pages/changePasswd.jsp";
			addExecScript("showFrameDailog('"+e.getMessage()+"',420,250,'"+chagePassWdURL+"',false);");
		}
	}
	
	/**
	 * ����һ��Portlet
	 * @param pageName
	 * @param pageModule
	 */
	@Action
	public void insertNewPortlet(@Param(name = "pageName") String pageName,
			   @Param(name = "pageModule") String pageModule){
		Map<String, PortletDisplayCategory>  cates = PortalServiceUtil.getPortletRegistryService().getPortletDisplayCache();
		if(cates == null || cates.isEmpty())
			return;
		String ftlName = "nc/portal/ftl/insertPortlet.ftl";
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("pageName", pageName);
		root.put("pageModule", pageModule);
		root.put("PortletDisplayCategory", cates.values());
		try {
			print(FreeMarkerTools.contextTemplatRender(ftlName, root));
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage());
		}
	}
	
	/**
	 * ��ʾ�û���һ��Portalҳ��
	 * 
	 * @param model ����ģ��
	 * @param pageName ҳ������
	 * @throws IOException
	 */
	@Action
	public void view(@Param(name = "pageModule") String model,
					 @Param(name = "pageName") String pageName) throws  IOException,ServletException {
		try {
			Page[] myPages = PortalPageDataWrap.getUserPages();
			/**
			 * ��õ�ǰҳ��
			 */
			Page page = PortalPageDataWrap.getUserPage(myPages, model, pageName);
			/**
			 * �˵�
			 */ 
			PageMenu menu = PortalPageDataWrap.getUserMenu(myPages);
			/**
			 * ��ӡ���ɵ�ҳ��
			 */
			print(PageBuilder.render(page, myPages,menu));
		} catch (PortalServiceException e) {
			throw new LfwRuntimeException(e.getMessage(), "ģ����Ⱦ�쳣", e);
		} catch (UserAccessException e) {
			logout();
		}catch (NullPointerException e) {
			print("<h1>"+e.getMessage()+"</h1><a href='/portal/core/login.jsp?pageId=login'>BACK</a>");
		}
	}

	/**
	 * ����Porltet����
	 * @param pageName ҳ������
	 * @param pageModule ҳ��ģ��
	 * @param portletId PortletID
	 * @param destinationId Ŀ��ID
	 * @param isAfter �Ƿ���ǰ��(��������ڲ�Ϊ�� ��ΪNULL)
	 * @throws PortalServiceException 
	 * @throws IOException 
	 */
	@Action
	public void layout(@Param(name = "pageName") String pageName,
					   @Param(name = "pageModule") String pageModule,
					   @Param(name = "portletId") String portletId, 
					   @Param(name = "destinationId") String destinationId,
					   @Param(name = "isAfter") Boolean isAfter) throws  IOException {
			try {
				
				/**
				 * ����û�����ҳ��
				 */
				Page[] myPages = PortalPageDataWrap.getUserPages();
				
				/**
				 * ����û�����
				 */
				PtSessionBean sbean = (PtSessionBean) LfwRuntimeEnvironment.getLfwSessionBean();
				String userId = sbean.getPk_user();
				 
				/**
				 * ��õ�ǰҳ��
				 */
				Page page = PortalPageDataWrap.getUserPage(myPages, pageModule, pageName);
				
				/**
				 * �ж�ҳ���Ƿ�ֻ��
				 */
				if(page.getReadonly() == null || page.getReadonly()){
					return;
				}
				
				/**
				 * ��������ϵ
				 */
				page.plantCoordTrees();
				if (isAfter == null) {
					
					/**
					 * ��Layout 
					 * ֱ�ӽ�PortLet����Layout
					 */
					page.addPortletToBlankLayout(portletId, destinationId);
				} else {
					
					/**
					 * �ǿ�Layout
					 * �ƶ�PortLet
					 */
					page.movePortlet(portletId, destinationId, isAfter);
				}
				
				/**
				 * ���»���
				 */
				PortalServiceUtil.getRegistryService().registryUserPageCache(page);
				
				/**
				 * �������ݿ�
				 */
				PtPageVO portalPageVO=new PtPageVO();
				portalPageVO.setFk_pageuser(userId);
				portalPageVO=PortalPageDataWrap.copyPage2PageVO(page, portalPageVO);
				PortalServiceUtil.getPageService().updateLayout(portalPageVO);
			} catch (PortalServiceException e) {
				throw new LfwRuntimeException(e.getMessage(), "ģ����Ⱦ�쳣", e);
			} catch (UserAccessException e) {
				logout();
			}
	}
	
	/**
	 * ������Portlet
	 * @param pageName ҳ��ID
	 * @param pageModule
	 * @param portletModule
	 * @param portletId
	 * @param skin
	 * @throws IOException
	 */
	@Action
	public void doDelPortlet(@Param(name = "PageName") String pageName,
			   @Param(name = "PageModule") String pageModule,
			   @Param(name = "PortletModule") String portletModule,
			   @Param(name = "PortletName") String portletId,
			   @Param(name = "pid") String pid) throws IOException{
		JSONArray returnData = new JSONArray();
		try {
			org.json.JSONObject node=new org.json.JSONObject();
			node.put("err", 1);
 
			/**
			 * ����û�����ҳ��
			 */
			Page[] myPages = PortalPageDataWrap.getUserPages();
			
			/**
			 * ����û�����
			 */
			PtSessionBean sbean = (PtSessionBean) LfwRuntimeEnvironment.getLfwSessionBean();
			String userId = sbean.getPk_user();
			 
			/**
			 * ��õ�ǰҳ��
			 */
			Page page = PortalPageDataWrap.getUserPage(myPages, pageModule, pageName);
			
			if(page.getReadonly() == null || page.getReadonly()){
				node.put("msg", "��ҳ���ֹɾ��Portlet!");
				returnData.put(node);
				return;
			}
 			/**
			 * �鿴��ǰҳ�����Ƿ��Ѿ���Ҫ��ӵ�Portlet
			 */
			boolean b = PortletDataWrap.hasPortlet(page, portletModule, portletId);
			
			if(!b){
				node.put("msg", "ҳ���в����ڴ�Portlet!");
				returnData.put(node);
				return;
			}
			/**
			 * ��������ϵ
			 */
			page.plantCoordTrees();
			
			/**
			 * ɾ��Portlet
			 * !!����Ĳ�����pid  ��PortletId
			 */
			page.removePortletElement(pid);
			
 			/**
			 * ���»���
			 */
			PortalServiceUtil.getRegistryService().registryUserPageCache(page);
			
			/**
			 * �������ݿ�
			 */
			PtPageVO portalPageVO=new PtPageVO();
			portalPageVO.setFk_pageuser(userId);
			portalPageVO=PortalPageDataWrap.copyPage2PageVO(page, portalPageVO);
			PortalServiceUtil.getPageService().updateLayout(portalPageVO);
			node.put("msg", "Portletɾ���ɹ�!");
			node.put("err", 0);
			returnData.put(node);
		} catch (UserAccessException e) {
			logout();
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(),e);
		}finally{
			print(returnData);
		}
	}
	
	/**
	 * ������Portlet
	 * @param pageName ҳ��ID
	 * @param pageModule
	 * @param portletModule
	 * @param portletId
	 * @param skin
	 * @throws IOException
	 */
	@Action
	public void doInsertNewPortlet(@Param(name = "pageName") String pageName,
			   @Param(name = "pageModule") String pageModule,
			   @Param(name = "portletModule") String portletModule,
			   @Param(name = "portletId") String portletId,
			   @Param(name = "skin") String skin) throws IOException{
		try {
			/**
			 * ����û�����ҳ��
			 */
			Page[] myPages = PortalPageDataWrap.getUserPages();
			
			/**
			 * ����û�����
			 */
			PtSessionBean sbean = (PtSessionBean) LfwRuntimeEnvironment.getLfwSessionBean();
			String userId = sbean.getPk_user();
			 
			/**
			 * ��õ�ǰҳ��
			 */
			Page page = PortalPageDataWrap.getUserPage(myPages, pageModule, pageName);
			
			if(page.getReadonly() == null || page.getReadonly()){
				alert("��ҳ���ֹ���Portlet!");
				return;
			}
			
			/**
			 * �鿴��ǰҳ�����Ƿ��Ѿ���Ҫ��ӵ�Portlet
			 */
			boolean b = PortletDataWrap.hasPortlet(page, portletModule, portletId);
			
			if(b){
				alert("ҳ�����Ѵ��ڴ�Portlet!");
				return;
			}
			/**
			 * ��������ϵ
			 */
			page.plantCoordTrees();
			
			Portlet portlet = PortalPageDataWrap.creatPortlet(portletId,portletModule,skin);
			page.insertNewPortlet(portlet);
			/**
			 * ���»���
			 */
			String key = PortalPageDataWrap.modModuleName(pageModule, pageName);
			PortalCacheManager.getUserPageCache().put(key, page);
			
			/**
			 * �������ݿ�
			 */
			PtPageVO portalPageVO=new PtPageVO();
			portalPageVO.setFk_pageuser(userId);
			portalPageVO=PortalPageDataWrap.copyPage2PageVO(page, portalPageVO);
			PortalServiceUtil.getPageService().updateLayout(portalPageVO);
			alert("Portlet�����ɹ�!");
			addExecScript("parent.insertOK();");
		} catch (UserAccessException e) {
			logout();
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * ע����½
	 * 
	 * @throws IOException
	 */
	@Action
	public void logout() throws IOException {
		Object osl = LfwClassUtil.newInstance("nc.uap.portal.integrate.system.OtherSystemLogout");
		String logoutScript = StringUtils.EMPTY;
		
		try {
			logoutScript = (String)MethodUtils.invokeMethod(osl, "getOtherSysLogoutScript", null);
		} catch (Exception e) {
			LfwLogger.error("��õ�����ע���ű������쳣��" + e.getMessage(),e);
		} 
		
		StringBuffer output = new StringBuffer(logoutScript);
		String loginUrl = request.getContextPath() +"/core/login.jsp?pageId=login";
		output.append("<script>window.location.href='"+loginUrl+"';</script>");
		print(output);
		HttpSession session = LfwRuntimeEnvironment.getWebContext().getRequest().getSession();
		try {
			LoginLogHelper.logout(session.getId(), new UFDateTime());
		} catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		
		/**
		 * ����session
		 */
		request.getSession().invalidate();
	}

} 