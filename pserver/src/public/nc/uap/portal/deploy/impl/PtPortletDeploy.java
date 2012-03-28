package nc.uap.portal.deploy.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import nc.bs.logging.Logger;
import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.deploy.itf.AbstractPtDeploy;
import nc.uap.portal.deploy.itf.IPtDeploy;
import nc.uap.portal.deploy.vo.PortalDeployDefinition;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IPtPortletQryService;
import nc.uap.portal.service.itf.IPtPortletService;
import nc.uap.portal.util.PortletDataWrap;
import nc.uap.portal.vo.PtPortletVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * Portlet������
 * 
 * @author licza
 * 
 */
public class PtPortletDeploy extends AbstractPtDeploy implements IPtDeploy {
	/**
	 * ����Portlet
	 * 
	 * @param pdd Portalģ����Ϣ
	 */
	public void deploy(PortalDeployDefinition define) {
		IPtPortletQryService pageQry = PortalServiceUtil.getPortletQryService();
		String module = define.getModule();
		PortletApplicationDefinition portletDefineModule = define.getPortletDefineModule();
		if (portletDefineModule != null) {
			PortletDefinition[] portlets = portletDefineModule.getPortlets().toArray(new PortletDefinition[0]);
			try {
				/** �õ�ϵͳ��Portlet **/
				PtPortletVO[] portletvos = pageQry.getSystemPortlet(module);
				deployPortlet(portlets, portletvos, module);
//				deployResource(portlets, module, IResourceVO.RESOURCE_TYPE_PORTLET);
			} catch (Exception e) {
				Logger.error("Module:" + module + " Portlet  deploy fail", e);
			}
		}
	}

	/**
	 * �������ݿ��е�Portlet
	 * 
	 * @param portlets ����PortletDefinition����
	 * @param portletvos ���ݿ���PortletVO����
	 * @param module ģ����
	 * @throws JAXBException
	 */
	private void deployPortlet(PortletDefinition[] portlets, PtPortletVO[] portletvos, String module) throws PortalServiceException, JAXBException {

		// ׼���ļ�
		// ���ð汾
		Map<String, String> localMirror = new HashMap<String, String>();
		// ����portletsʵ��
		Map<String, PortletDefinition> localCopy = new HashMap<String, PortletDefinition>();
		if (portlets != null && portlets.length > 0) {
			for (PortletDefinition portlet : portlets) {
				localMirror.put(portlet.getPortletName(), PortletDataWrap.getVersion(portlet));
				localCopy.put(portlet.getPortletName(), portlet);
			}
		}
		Set<PtPortletVO> addPortlet = new HashSet<PtPortletVO>();
		Set<PtPortletVO> updatePortletCache = new HashSet<PtPortletVO>();
		Set<String> deletePortlet = new HashSet<String>();
		if (portletvos != null && portletvos.length > 0) {
			for (PtPortletVO portletVO : portletvos) {
				String localPageName = portletVO.getPortletid();
				PortletDefinition local = localCopy.get(localPageName);
				if (local == null) {
					// ��Ҫɾ����PML
					portletVO.setNewversion("");
					deletePortlet.add(portletVO.getPk_portlet());
				} else {
					// ��Ҫ���µ�portlet
					if (versionCompare(localMirror.get(localPageName), portletVO.getNewversion())) {
						portletVO.setNewversion(PortletDataWrap.getVersion(local));
						PtPortletVO portletCache = PortletDataWrap.warpVO(portletVO, local);
						updatePortletCache.add(portletCache);
					}
					// �Ӿ���ɾ��
					localMirror.remove(localPageName);
				}
			}
		}
		if (!localMirror.isEmpty()) {
			// ��Ҫ���ӵ�PML
			Set<Map.Entry<String, String>> localmirrorEntrySet = localMirror.entrySet();
			for (Map.Entry<String, String> localmirrorEntry : localmirrorEntrySet) {
				String localPageName = localmirrorEntry.getKey();
				PortletDefinition pd = localCopy.get(localPageName);
				PtPortletVO target = new PtPortletVO();
				target = PortletDataWrap.warpVO(target, pd);
				target.setActiveflag(UFBoolean.valueOf(true));
				target.setShareflag(UFBoolean.valueOf(false));
				target.setFk_portaluser("#");
				target.setModule(module);
				target.setNewversion(target.getVersion());
				addPortlet.add(target);
			}
		}
		// ��Ҫ����
		if (!addPortlet.isEmpty()) {
			addPortletWithGroups(addPortlet);
		}
		if (!updatePortletCache.isEmpty()) {
			updatePortletWithGroups(updatePortletCache);
		}
		if (!deletePortlet.isEmpty()) {
			deletePortletWithGroups(deletePortlet);
		}
	}

	/**
	 * ��������Portlet
	 * 
	 * @param addPortlet ��Ҫ���ӵ�Portlet
	 * @throws PortalServiceException �������ݿ��쳣
	 */
	private void addPortletWithGroups(Set<PtPortletVO> addPortlet) throws PortalServiceException {
		PtPortletVO[] portles = addPortlet.toArray(new PtPortletVO[0]);
		IPtPortletService prs = PortalServiceUtil.getPortletService();
		// д��ϵͳPortlet
//		String[] pks = 
		prs.addPortlets(portles);
//		PortalServiceUtil.getServiceProvider().getPortletDeployService().addPortlet(portles, prs, pks);
	}



	/**
	 * ��������Portlet
	 * 
	 * @param updatePortlet
	 * @throws PortalServiceException
	 */
	private void updatePortletWithGroups(Set<PtPortletVO> updatePortlet) throws PortalServiceException {
		IPtPortletService prs = PortalServiceUtil.getPortletService();
		PtPortletVO[] portlets = updatePortlet.toArray(new PtPortletVO[0]);
		prs.updatePortlets(portlets);
	}
	/**
	 * ����ɾ��
	 * @param delPortlet
	 * @throws PortalServiceException
	 */
	private void deletePortletWithGroups(Set<String> delPortlet)throws PortalServiceException {
		IPtPortletService prs = PortalServiceUtil.getPortletService();
		prs.delete(delPortlet.toArray(new String[]{}));
	}

}
