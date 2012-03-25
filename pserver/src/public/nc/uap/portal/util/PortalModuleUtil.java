package nc.uap.portal.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.deploy.vo.PortalDeployDefinition;
import nc.uap.portal.deploy.vo.PortalModule;
import nc.uap.portal.exception.PortalServiceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.digester.Digester;

/**
 * <b>Portal ģ�鹤����.</b>
 * <p>
 * ������ģ��������������.
 * </p>
 * 
 * @author licza.
 */
public final class PortalModuleUtil {
	/** Portalģ�鶨������� **/
	private static Digester digester;
	static {
		getPortalModuleDigester();
	}

	/**
	 * ���Portalģ��
	 * 
	 * @param f
	 * @return
	 * @throws PortalServiceException
	 */
	public static PortalModule parsePortalModule(File f) throws PortalServiceException {
		try {
			return (PortalModule) digester.parse(f);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PortalServiceException("����PortalModule�����쳣," + f.getName());
		}
	}

	/**
	 * ����ģ�������
	 */
	private static void getPortalModuleDigester() {
		digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("portal", PortalModule.class.getName());
		digester.addSetProperties("portal");

		digester.addCallMethod("portal/module", "setModule", 0);
		digester.addCallMethod("portal/depends", "setDepends", 0);
		digester.addCallMethod("portal/depends", "setDescription", 0);
	}

	/**
	 * ��Portalģ�鶨�尴��������ϵ����
	 * 
	 * @param dfis Portalģ�鶨��
	 * @return ������Portalģ�鶨��
	 * @throws PortalServiceException ����ģ��ѭ������
	 */
	public static PortalDeployDefinition[] sortDefinition(List<PortalDeployDefinition> dfis) throws PortalServiceException {
		List<PortalDeployDefinition> definitions = new LinkedList<PortalDeployDefinition>(dfis);
		/**
		 * ��������ģ��
		 */
		List<PortalDeployDefinition> sortedDefinitions = new ArrayList<PortalDeployDefinition>();
		/**
		 * ����һ������ģ�� 
		 * ��ԭʱʹ�� 
		 */
		Map<String, String[]> localDependsModuleMirror = new HashMap<String, String[]>();
		if (CollectionUtils.isNotEmpty(definitions)) {
			for (PortalDeployDefinition pd : definitions) {
				localDependsModuleMirror.put(pd.getModule(), pd.getDependsModule());
			}
			int defSize = definitions.size();
			/**
			 * ����
			 */
			List<PortalDeployDefinition> peaks = hasPeak(definitions);
			while (!CollectionUtils.isEmpty(peaks)) {
				for (PortalDeployDefinition pd : peaks) {
					/**
					 * ���б���ɾ��
					 */
					definitions.remove(pd);
					/**
					 * ��ԭ��ʵ��������ϵ
					 */
					pd.setDependsModule(localDependsModuleMirror.get(pd.getModule()));
					/**
					 * �������� 
					 */
					sortedDefinitions.add(pd);
					/**
					 * ɾ��������ϵ
					 */
					removeDefinition(definitions, pd);
				}
				peaks = hasPeak(definitions);
			}
			/**
			 * ģ�����붥������һ��
			 */
			if (defSize != sortedDefinitions.size()) {
				String msg = "����ʱ����ģ�����ѭ������!ģ��Ϊ:" + definitions.toString() + "(�����ο�).����!";
				LfwLogger.error(msg);
				throw new PortalServiceException(msg);
			}
		}
		return sortedDefinitions.toArray(new PortalDeployDefinition[0]);
	}

	/**
	 * ɾ������ģ���жԶ���ģ���������ϵ
	 * 
	 * @param remainDefList ����ģ��
	 * @param pd ����ģ��
	 */
	private static void removeDefinition(List<PortalDeployDefinition> remainDefList, PortalDeployDefinition pd) {
		for (int i = 0; i < remainDefList.size(); i++) {
			String[] modules = remainDefList.get(i).getDependsModule();
			List<String> moduleList = new LinkedList<String>();
			if (modules != null && modules.length > 0) {
				String anObject = pd.getModule();
				for (String module : modules) {
					if (!module.equals(anObject))
						moduleList.add(module);
				}
			}
			remainDefList.get(i).setDependsModule(moduleList.toArray(new String[0]));
		}
	}

	/**
	 * ��õ�ǰģ���еĶ���ģ��
	 * 
	 * @param pds ��ǰģ��
	 * @return ����ģ��
	 */
	private static List<PortalDeployDefinition> hasPeak(List<PortalDeployDefinition> pds) {
		List<PortalDeployDefinition> orders = new LinkedList<PortalDeployDefinition>();
		for (PortalDeployDefinition pd : pds) {
			/**
			 * ֻ��������Ԫ��
			 * ��Ϊ����
			 */
			if (pd.getDependsModule() == null || pd.getDependsModule().length == 0) {
				orders.add(pd);
			}
		}
		return orders;
	}
}
