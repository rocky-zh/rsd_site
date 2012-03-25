package nc.uap.portal.deploy.itf;

import nc.uap.portal.util.PtUtil;

/**
 * Portal�������ʵ��
 * @author licza
 *
 */
public abstract class AbstractPtDeploy implements IPtDeploy{
	
//	/**
//	 * ��Դ����
//	 * @param resourcefuls
//	 */
//	public void deployResource(Resourceful[] resourcefuls,String module,Integer type){
//		PortalServiceUtil.getServiceProvider().getResourceService().doDeploy(resourcefuls, module, type);
//	}
	
	/**
	 * �Ƚϱ��ذ汾�����ݿ��еİ汾
	 * 
	 * @param localVersion
	 * 
	 * @param dbversion
	 * 
	 * @return ���������ݿ��а汾���¾�
	 */
	protected static boolean versionCompare(String localVersion, String dbversion) {
		if (PtUtil.isNumbic(localVersion) && PtUtil.isNumbic(dbversion))
			return Integer.parseInt(localVersion) > Integer.parseInt(dbversion);
		else
			return false;
	}
}
