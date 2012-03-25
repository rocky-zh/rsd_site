package nc.uap.portal.login;

import nc.uap.portal.user.entity.IUserVO;

/**
 * ������½������Ϣ
 * 
 * @author licza
 * 
 */
public class AnnoymousLoginConfig {

	/**
	 * Portal�Ƿ�֧�������û�
	 * 
	 * @return
	 */
	public static boolean isSupportAnnoymous() {
		IUserVO user = getAnnoymous();
		return (user != null) && (user.getPk_user() != null);
	}

	/**
	 * ��������û�
	 * 
	 * @return
	 */
	public static IUserVO getAnnoymous() {
//		INotifyAbleCache na = (INotifyAbleCache)LfwClassUtil.newInstance("nc.portal.anonymousmgr.AnnoyMousCache");
//		return PortalCacheProxy.newIns().get(na,IUserVO.class);
		return null;
	}
}
