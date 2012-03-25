package nc.uap.portal.login.thirdparty;

import nc.bs.framework.common.NCLocator;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.service.itf.IPtThirdPartyLoginService;
import nc.uap.portal.vo.PtTrdauthVO;


/**
 * ��������¼������
 * 
 * @author licza
 * 
 */
public class ThirdPartyLoginHelper   {
	
	/**
	 * ע���¼���Ʋ����ص�¼���ƺ�
	 * @param auth
	 * @return
	 */
	public static String registerPtTrdauthVO(PtTrdauthVO auth){
		IPtThirdPartyLoginService tpl = NCLocator.getInstance().lookup(IPtThirdPartyLoginService.class);
		try {
			tpl.addAuth(auth);
			return auth.getAkey();
		} catch (PortalServiceException e) {
			return null;
		}
	}
	
	/**
	 * ��õ�¼����
	 * @param id
	 * @return
	 */
	public static PtTrdauthVO  getAuth(String akey){
		IPtThirdPartyLoginService tpl = NCLocator.getInstance().lookup(IPtThirdPartyLoginService.class);
		try {
			return tpl.getAuth(akey);
		} catch (PortalServiceException e) {
			return null;
		}
	}
	
}
