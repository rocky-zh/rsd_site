package nc.uap.portal.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.CookieConstant;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Param;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.portal.comm.setting.PtSettingVO;
import nc.uap.portal.comm.setting.itf.IPortalSetting;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.exception.UserAccessException;
import nc.uap.portal.om.Page;
import nc.uap.portal.om.Skin;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.util.PortalPageDataWrap;
import nc.uap.portal.util.ToolKit;
import nc.uap.portal.util.freemarker.FreeMarkerTools;
import nc.uap.portal.vo.PtPageVO;
import nc.uap.portal.vo.PtThemeVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.sm.UserVO;
import nc.vo.uap.rbac.util.RbacUserPwdUtil;

import org.apache.commons.lang.StringUtils;

/**
 * �Ż�����Action
 * 
 * @author licza
 * 
 */
@Servlet(path = "/setting")
public class PortalSettingAction extends BaseAction {
	@Action
	public void passwd(@Param(name="c_passwd") String c_passwd, @Param(name="n_passwd") String n_passwd, @Param(name="n_passwd1") String n_passwd1){
		String pk_user = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		
		if(StringUtils.equals(n_passwd1, n_passwd)){
			try {
				CpUserVO user = CpbServiceFacility.getCpUserQry().getUserByPk(pk_user);
				CpbServiceFacility.getCpUserBill().changeUserPwd(user, c_passwd, n_passwd);
				addExecScript("alert('�޸ĳɹ�,�����µ�¼!')");
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
				addExecScript("alert('" + e.getMessage().replace("'", "`") + "');");
			}
		}else{
			addExecScript("alert('������������벻ͬ!')");
		}
		
		 
	}
	
	/**
	 * ������Ŀ�б�
	 */
	@Action
	public void list() {
		PluginManager pm = PluginManager.newIns();
		List<PtExtension> exs = pm.getExtensions(IPortalSetting.PID);
		List<PtSettingVO> settings = new ArrayList<PtSettingVO>();
		if (ToolKit.notNull(exs))
			for (PtExtension ex : exs) {
				IPortalSetting setting = ex.newInstance(IPortalSetting.class);
				settings.addAll(Arrays.asList(setting.getSettings()));
			}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("singleSetting", settings);
		String html = null;
		try {
			html = FreeMarkerTools.contextTemplatRender(
					"nc/portal/ftl/setting.ftl", root);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		print(html);
	}

	/**
	 * ��������
	 */
	@SuppressWarnings("restriction")
	@Action
	public void lang(@Param(name="langId")String langId) {
		String pk_user = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		try {
			CpbServiceFacility.getCpUserBill().changeUserLanguage(pk_user, langId);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			addExecScript("alert('" + e.getMessage().replace("'", "`") + "');");
			return;
		}
		String sysId = "" + LfwRuntimeEnvironment.getSysId();
		Cookie lc = new Cookie(CookieConstant.LANG_CODE + sysId, langId);
		lc.setPath("/");
		lc.setMaxAge(CookieConstant.MAX_AGE);
		response.addCookie(lc);
		addExecScript("alert('���óɹ�!�����µ�¼!');");
	}
	
	/**
	 * ģ���б�
	 */
	@Action
	public void templateList(){
		Skin[] skins = PortalServiceUtil.getSkinQryService().getSkinCache(PortalEnv.TYPE_PAGE);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("skins", skins);
		String html = null;
		try {
			html = FreeMarkerTools.contextTemplatRender(
					"nc/uap/portal/comm/setting/tpl/templateList.ftl", root);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		print(html);
	}
	/**
	 * ģ������
	 * @throws IOException 
	 */
	@Action
	public void template(@Param(name="id")String id, @Param(name="pagemodule")String pageModule, @Param(name="pagename")String pageName) throws IOException{
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
			page.setTemplate(id);
			
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
			addExecScript("alert('���óɹ�!�����µ�¼!');");
		} catch (PortalServiceException e) {
			throw new LfwRuntimeException(e.getMessage(), "ģ����Ⱦ�쳣", e);
		} catch (UserAccessException e) {
			logout();
		}
	}
	
	/**
	 * Ƥ���б�
	 */
	@Action
	public void themeList(){
		 PtThemeVO[] vos = new PtThemeVO[]{};
			try {
				vos = PortalServiceUtil.getThemeQryService().getThemeByGroup();
			} catch (PortalServiceException e) {
				LfwLogger.error(e.getMessage(),e);
				throw new LfwRuntimeException(e.getMessage());
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("skins", vos);
			String html = null;
			try {
				html = FreeMarkerTools.contextTemplatRender(
						"nc/uap/portal/comm/setting/tpl/skinList.ftl", root);
			} catch (PortalServiceException e) {
				LfwLogger.error(e.getMessage(), e);
			}
			print(html);
	}
	/**
	 * Ƥ������
	 * @throws IOException 
	 */
	@Action
	public void theme(@Param(name="id")String id,  @Param(name="pagemodule")String pageModule, @Param(name="pagename")String pageName) throws IOException{
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
			page.setSkin(id);
			
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
			addExecScript("alert('���óɹ�!�����µ�¼!')");
		} catch (PortalServiceException e) {
			throw new LfwRuntimeException(e.getMessage(), "ģ����Ⱦ�쳣", e);
		} catch (UserAccessException e) {
			logout();
		}
	}
	
	/**
	 * ����Portlet��ʽ����
	 */
	@Action
	public void changePortletTheme(@Param(name="portlet")String portlet, @Param(name="pid")String pid){
		Skin[] skins = PortalServiceUtil.getSkinQryService().getSkinCache(PortalEnv.TYPE_PORTLET);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("skins", skins);
		root.put("portlet", portlet);
		root.put("pid", pid);
		String html = null;
		try {
			html = FreeMarkerTools.contextTemplatRender(
					"nc/uap/portal/comm/setting/tpl/portletTheme.ftl", root);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		print(html);
	}
	/**
	 * �޸�Portlet��ʽ
	 * @param portlet
	 * @param pid
	 * @throws IOException 
	 */
	@Action
	public void portletTheme(@Param(name="id")String id, @Param(name="portlet")String portlet, @Param(name="pid")String pid) throws IOException{
		String[] idArr = portlet.split("_");
		
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
			Page page = PortalPageDataWrap.getUserPage(myPages, idArr[0], idArr[1]);
			page.plantCoordTrees();
			nc.uap.portal.om.Portlet pl = page.getPortlet(idArr[2] + ":" + idArr[3]);
			if(pl == null)
				pl = page.getPortlet(idArr[3]);
			if(pl != null){
				pl.setTheme(id);
				
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
				addExecScript("alert('���óɹ�!');parent.parent.document.location.reload();");
			}
		} catch (PortalServiceException e) {
			throw new LfwRuntimeException(e.getMessage(), "ģ����Ⱦ�쳣", e);
		} catch (UserAccessException e) {
			logout();
		}
	}
}
