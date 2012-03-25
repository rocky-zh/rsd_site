package nc.uap.portal.deploy.impl;

import java.util.ArrayList;
import java.util.List;

import nc.jdbc.framework.SQLParameter;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.deploy.itf.IPtDeploy;
import nc.uap.portal.deploy.vo.PortalDeployDefinition;
import nc.uap.portal.om.Display;
import nc.uap.portal.om.PortletDisplay;
import nc.uap.portal.om.PortletDisplayCategory;
import nc.uap.portal.util.PtUtil;
import nc.uap.portal.vo.PtDisplayVO;
import nc.uap.portal.vo.PtDisplaycateVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;

/**
 * ����Portlet����
 * 
 * @author licza
 * 
 */
public class PtDisplayDepoly implements IPtDeploy {
	/** Portlet��ʾ��Ϣ�б� **/
	private List<PtDisplayVO> portletDisplayList = null;
	
	/** Portlet�����б� **/
	private List<PtDisplaycateVO> portletDisplayCateList = null;
	
	@Override
	public void deploy(PortalDeployDefinition define) {
		Display display = define.getDisplay();
		if(display == null)
			return;
		String module = define.getModule();
		List<PortletDisplayCategory> cates = display.getCategory();
		if (cates == null || cates.isEmpty())  
			return;
		for (PortletDisplayCategory cate : cates) {
			addPortletDisplay(cate, module);
		}
		clearPortletDisplayByModule(module);
		addAllDisplay();
	}
	/**
	 * �������е�portlt������ʾ��Ϣ
	 */
	private void addAllDisplay(){
		addAllPortletDisplay();
		addAllPortletDisplayCategory();
	}
	
	/**
	 * ��Portlet��ʾ��ӵ����ݿ�
	 */
	private void addAllPortletDisplay(){
		if(getPortletDisplayList().isEmpty())
			return;
		try {
			CRUDHelper.getCRUDService().saveBusinessVOs(PtUtil.setSuperVO2AggVOParent(getPortletDisplayList(),VOStatus.NEW));
		} catch (Exception e) {
			LfwLogger.error("==="+this.getClass().getName()+"#===���Portlet��ʾ�����쳣" ,e.getCause());
		}
	}
	
	/**
	 * ��Portlet������ʾ��ӵ����ݿ�
	 */
	private void addAllPortletDisplayCategory(){
		if(getPortletDisplayCateList().isEmpty())
			return;
		try {
			CRUDHelper.getCRUDService().saveBusinessVOs(PtUtil.setSuperVO2AggVOParent(getPortletDisplayCateList(),VOStatus.NEW));
		} catch (Exception e) {
			LfwLogger.error("==="+this.getClass().getName()+"#===���Portlet��ʾ�����쳣" ,e.getCause());
		}
	}
	
	
	/**
	 * ���ģ���µ�Portlet��ʾ��Ϣ
	 */
	private void clearPortletDisplayByModule(String module){
		/**
		 * Portal����ģ��,������
		 */
		if(PortalEnv.getPortalCoreName().equals(module))
			return;
		String sql = "delete from pt_display where module = ? ";
		SQLParameter param = new SQLParameter();
		param.addParam(module);
		try {
			CRUDHelper.getCRUDService().executeUpdate(sql, param);
		} catch (Exception e) {
			LfwLogger.error("==="+this.getClass().getName()+"#===���ģ��:"+module+"�µ�Portlet���鷢���쳣" ,e.getCause());
		}
	}
	
	/**
	 * ����Portal���鵽�б���
	 * @param category
	 * @param module
	 */
	private void addPortletDisplay(PortletDisplayCategory category, String module){
		if(category == null || category.getPortletDisplayList().isEmpty())
			return;
		String cateid = category.getId();
		/**
		 * �·���
		 */
		if(!isCategoryNotExist(cateid)){
			PtDisplaycateVO dc = new PtDisplaycateVO();
			dc.setId(cateid);
			dc.setI18nname(category.getI18nName());
			dc.setTitle(category.getText());
			getPortletDisplayCateList().add(dc);
		}
		/**
		 * portlet��ʾ
		 */
		for(PortletDisplay display : category.getPortletDisplayList()){
			display.setModule(module);
			getPortletDisplayList().add(new PtDisplayVO(display,cateid));
		}
	}
	
	/**
	 * �жϷ����Ƿ����
	 * @param moduleid
	 * @return
	 */
	private boolean isCategoryNotExist(String cateid){
		PtDisplaycateVO displayVO = new PtDisplaycateVO();
		displayVO.setId(cateid);
		try {
			SuperVO[] result = CRUDHelper.getCRUDService().queryVOs(displayVO, null, null);
			return result != null && result.length > 0;
		} catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
			return false;
		}
	}
	
	/**
	 * getter
	 * @return
	 */
	protected List<PtDisplayVO> getPortletDisplayList() {
		if(portletDisplayList == null)
			portletDisplayList = new ArrayList<PtDisplayVO>();
		return portletDisplayList;
	}
	
	/**
	 * getter
	 * @return
	 */
	protected List<PtDisplaycateVO> getPortletDisplayCateList() {
		if(portletDisplayCateList == null)
			portletDisplayCateList = new ArrayList<PtDisplaycateVO>();
		return portletDisplayCateList;
	}
	
}
