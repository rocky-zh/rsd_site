package nc.uap.portal.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Param;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.Page;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.util.PmlUtil;
import nc.uap.portal.util.PortalPageDataWrap;
import nc.uap.portal.util.PtUtil;
import nc.uap.portal.vo.PtPageVO;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

/**
 * ҳ�������Action
 * @author licza
 *
 */
@Servlet(path="/portalpage")
public class PortalPageManagerAction extends BaseAction{
	
	/**
	 * ����ĳ���ŵ�PML
	 * @param pk_group ���ű���
	 * @param pml ҳ������
	 */
	@Action(method="POST")
	public void doNew(@Param(name="groupid")String pk_group,@Param(name="pml")String pml){
		LfwSessionBean ses = LfwRuntimeEnvironment.getLfwSessionBean();
		if(pml == null || pk_group == null || ses == null)
			return;
		/**
		 * ����Ա�Ƿ���Ȩ��
		 */
//		if(!SecurityHelper.hasPower(ses.getPk_user(), pk_group))
//			return;
		
		try {
			/**
			 * PMLת��Ϊҳ������ģ��
			 */
			Page page = PmlUtil.parser(URLDecoder.decode(pml,"UTF-8"));
			String pagename = page.getPagename();
			if(page == null || PtUtil.isNull(pagename))
				return;
			/**
			 * ����Ƿ���ڴ�ҳ��
			 */
			boolean isPageExist = false;//PortalServiceUtil.getPageQryService().isPortalPageExist(PortalEnv.getPortalCoreName(), pagename);
			
			if(isPageExist)
				return;
			/**
			 * ����
			 */
			PortalServiceUtil.getPageService().add(pml2vo(page, pk_group));
			
			/**
			 * ������Դ
			 */
			/**
			 * ���¼��Ż���
			 */
			//PortalServiceUtil.getRegistryService().updateGroupCache(pk_group , Boolean.TRUE);
		} catch (UnsupportedEncodingException e) {
			LfwLogger.error(e.getMessage(),e);
		}   catch (SAXException e) {
			LfwLogger.error(e.getMessage()	, e);
			print("PML��ʽ����");
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
 	}
	/**
	 * ת������ģ��Ϊ���ݿ�洢ģ��
	 * @param page ҳ������ģ��
	 * @param pk_group ���ű���
	 * @return
	 */
	private PtPageVO pml2vo(Page page,String pk_group){
		PtPageVO vo = PortalPageDataWrap.copyPage2PageVO(page, null);
		vo.setPk_group(pk_group);
		vo.setFk_pageuser("*");
		vo.setModule(PortalEnv.getPortalCoreName());
		return vo;
	}
	
	 
	
	/**
	 * �༭PML
	 * @param pk ҳ������
	 * @param pml ҳ������
	 */
	@Action(method="POST")
	public void doEdit(@Param(name="pk")String pk,@Param(name="pml")String pml){
		LfwSessionBean ses = LfwRuntimeEnvironment.getLfwSessionBean();
		if(pml == null || pk == null || ses == null)
			return;
		try {
			/**
			 * ��þɰ汾
			 */
			PtPageVO oldVersion = PortalServiceUtil.getPageQryService().getPageByPk(pk);
			String pk_group = oldVersion.getPk_group();
			/**
			 * ����Ա�Ƿ���Ȩ��
			 */
//			if(!SecurityHelper.hasPower(ses.getPk_user(), pk_group))
//				return;
			Page page = PmlUtil.parser(URLDecoder.decode(pml,"UTF-8"));
			if(page == null)
				return;
			boolean pageNameHasModify = !StringUtils.equals(page.getPagename(), oldVersion.getPagename());
			/**
			 * ҳ��ID�Ƿ��޸�
			 */
			if(pageNameHasModify)
				return;
			
			/**
			 * Ӧ���޸�
			 */
			PtPageVO vo = PortalPageDataWrap.copyPage2PageVO(page,oldVersion);
			/**
			 * ����
			 */
			PortalServiceUtil.getPageService().update(vo);
			/**
			 * ���»���
			 */
			//PortalServiceUtil.getRegistryService().updateGroupCache(vo.getPk_group() , Boolean.TRUE);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LfwLogger.error(e.getMessage(),e);
		} catch (SAXException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("PML��ʽ����");
		}
	}
	/**
	 * ����PK��ӡ��PML
	 * @param pk 
	 */
	@Action(method="GET")
	public void getPml(@Param(name="pk")String pk){
		if(pk == null)
			return;
		try {
			PtPageVO vo = PortalServiceUtil.getPageQryService().getPageByPk(pk);
			String pml = vo.doGetSettingsStr();
			print(pml);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(),e);
		}
	}
}
