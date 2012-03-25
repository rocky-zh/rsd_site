package nc.uap.portal.deploy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.deploy.itf.IPtDeploy;
import nc.uap.portal.deploy.vo.PortalDeployDefinition;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.plugins.model.ExPoint;
import nc.uap.portal.plugins.model.Extension;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;
import nc.uap.portal.plugins.model.PtPlugin;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IPtPluginQryService;
import nc.uap.portal.service.itf.IPtPluginService;
import nc.uap.portal.util.PtUtil;

/**
 * �������ʵ��
 * 
 * @author licza
 * 
 */
public class PtPluginDeploy implements IPtDeploy {

	@Override
	public void deploy(PortalDeployDefinition define) {
		String moduleName = define.getModule();
		PtPlugin plugin = define.getPlugin();
		if (plugin == null){
			clear(moduleName);
			return;
		}
		
		//��ʼ��
		init(plugin);
		IPtPluginQryService pluginQry = PortalServiceUtil.getPluginQryService();
		try {
			// ���ݿ�����չ
			PtExtension[] exArr = pluginQry.getAllExtension(define.getModule());
			PtExtensionPoint[] exPointArr=pluginQry.getAllExtensionPoint();
			deploy(exList, exArr, moduleName);
			deploy(expList, exPointArr);
		} catch (PortalServiceException e) {
			LfwLogger.error("Module :" + moduleName + " Plugin deploy fail", e);
		}
		
	}
	/**
	 * ����ģ��
	 * @param moduleName
	 */
	private void clear(String moduleName){
		try {
			PortalServiceUtil.getPluginService().clearModule(moduleName);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
		}
	}
	/**
	 * ����ģ���µ���չ
	 * 
	 * @param exList ���ص���չ�б�
	 * @param exArr ���ݿ��е���չ�б�
	 * @throws PortalServiceException ���ݿ�����쳣
	 */
	public void deploy(List<PtExtension> exList, PtExtension[] exArr, String module) throws PortalServiceException {
		IPtPluginService pluginService = PortalServiceUtil.getPluginService();

		// ����
		Set<String> localMirror = new HashSet<String>();
		// ����portletsʵ��
		Map<String, PtExtension> localCopy = new HashMap<String, PtExtension>();
		if (exList != null && exList.size() > 0) {
			for (PtExtension ex : exList) {
				localMirror.add(ex.getId());
				ex.setModule(module);
				localCopy.put(ex.getId(), ex);
			}
		}
		Set<PtExtension> beAdd = new HashSet<PtExtension>();
		Set<PtExtension> beUpdate = new HashSet<PtExtension>();
		Set<PtExtension> beDelete = new HashSet<PtExtension>();
		if (exArr != null && exArr.length > 0) {
			for (PtExtension exVO : exArr) {
				String localPageName = exVO.getId();
				PtExtension local = localCopy.get(localPageName);
				if (local == null) {
					// ��Ҫɾ����
					beDelete.add(exVO);
				} else {
					// ��Ҫ���µ�
					if (!exVO.same(local)) {
						exVO.copy(local);
						beUpdate.add(exVO);
					}
					// �Ӿ���ɾ��
					localMirror.remove(localPageName);
				}
			}
		}
		if (!localMirror.isEmpty()) {
			// ��Ҫ���ӵ�
			for (String localmirrorEntry : localMirror) {
				beAdd.add(localCopy.get(localmirrorEntry));
			}
		}
		if (!beAdd.isEmpty()) {
			pluginService.add(beAdd.toArray(new PtExtension[0]));
		}
		if (!beUpdate.isEmpty()) {
			pluginService.update(beUpdate.toArray(new PtExtension[0]));
		}
//		if (!beDelete.isEmpty()) {
//			pluginService.delete(beDelete.toArray(new PtExtension[0]));
//		}
	}
	/**
	 * ����ģ���µ���չ��
	 * 
	 * @param exList ���ص���չ���б�
	 * @param exArr ���ݿ��е���չ���б�
	 * @throws PortalServiceException ���ݿ�����쳣
	 */
	public void deploy(List<PtExtensionPoint> exList, PtExtensionPoint[] exArr) throws PortalServiceException {
		IPtPluginService pluginService = PortalServiceUtil.getPluginService();

		// ����
		Set<String> localMirror = new HashSet<String>();
		// ����portletsʵ��
		Map<String, PtExtensionPoint> localCopy = new HashMap<String, PtExtensionPoint>();
		if (exList != null && exList.size() > 0) {
			for (PtExtensionPoint ex : exList) {
				localMirror.add(ex.getPoint());
				localCopy.put(ex.getPoint(), ex);
			}
		}
		Set<PtExtensionPoint> beAdd = new HashSet<PtExtensionPoint>();
		Set<PtExtensionPoint> beUpdate = new HashSet<PtExtensionPoint>();
		Set<PtExtensionPoint> beDelete = new HashSet<PtExtensionPoint>();
		if (exArr != null && exArr.length > 0) {
			for (PtExtensionPoint exVO : exArr) {
				String localPageName = exVO.getPoint();
				PtExtensionPoint local = localCopy.get(localPageName);
				if (local == null) {
					// ��Ҫɾ����
					beDelete.add(exVO);
				} else {
					// ��Ҫ���µ�
					if (!exVO.equals(local)) {
						exVO.copy(local);
						beUpdate.add(exVO);
					}
					// �Ӿ���ɾ��
					localMirror.remove(localPageName);
				}
			}
		}
		if (!localMirror.isEmpty()) {
			// ��Ҫ���ӵ�
			for (String localmirrorEntry : localMirror) {
				beAdd.add(localCopy.get(localmirrorEntry));
			}
		}
		if (!beAdd.isEmpty()) {
			pluginService.add(beAdd.toArray(new PtExtensionPoint[0]));
		}
		if (!beUpdate.isEmpty()) {
			pluginService.update(beUpdate.toArray(new PtExtensionPoint[0]));
		}
//		if (!beDelete.isEmpty()) {
//			pluginService.delete(beDelete.toArray(new PtExtensionPoint[0]));
//		}
	}
	
	private List<PtExtension> exList = new ArrayList<PtExtension>();
	
	private List<PtExtensionPoint> expList = new ArrayList<PtExtensionPoint>();
	/**
	 * ��ʼ��
	 * @param plugin
	 */
	private void init(PtPlugin plugin){
		exList.clear();
		expList.clear();
		if(plugin != null){
			List<ExPoint> exps = plugin.getExtensionPointList();
			if(!PtUtil.isNull(exps)){
				for(ExPoint exp : exps){
					if(exp == null)
						continue;
					PtExtensionPoint expVO = new PtExtensionPoint();
					expVO.formXML(exp);
					expList.add(expVO);
					if(!PtUtil.isNull(exp.getExtensionList())){
						for(Extension ex : exp.getExtensionList()){
							PtExtension exVO = new PtExtension();
							exVO.fromXML(ex, exp.getPoint());
							exList.add(exVO);
						}
					}
				}
			}
		}
	}

}
