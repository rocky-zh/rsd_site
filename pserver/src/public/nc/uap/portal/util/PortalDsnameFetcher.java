package nc.uap.portal.util;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.sfapp.IAppendProductConfService;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;

/**
 * Portalʹ������Դ��ȡ
 *
 */
public final class PortalDsnameFetcher {
	private static String dsName;
	public static String getPortalDsName() {
		if(dsName == null){
			IAppendProductConfService proService = NCLocator.getInstance().lookup(IAppendProductConfService.class);
			try {
				dsName = proService.getNCPortalDsName();
				LfwLogger.debug("��ȡ��Portal����Դ:" + dsName);
			} 
			catch (BusinessException e) {
				LfwLogger.error(e);
			}
		}
		return dsName;
	}
}
