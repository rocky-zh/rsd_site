package nc.uap.portal.inte;

import nc.bs.framework.common.NCLocator;
import nc.uap.portal.integrate.itf.IPtSsoConfigQryService;
import nc.uap.portal.integrate.sso.itf.ISSOQueryService;
import nc.uap.portal.integrate.sso.itf.ISSOService;

/**
 * portal���ɷ��񹤳�
 * @author licza
 *
 */
public class PintServiceFactory {
	/***
	 * �õ�SSO��ѯ����
	 * @return
	 */
	public static ISSOQueryService getSsoQryService(){
		return NCLocator.getInstance().lookup(ISSOQueryService.class);
	}
	/**
	 * �õ�SSO����
	 * @return
	 */
	public static ISSOService getSsoService(){
		return NCLocator.getInstance().lookup(ISSOService.class);
	}
	/**
	 * ���sso������Ϣ��ѯ����
	 * @return
	 */
	public static IPtSsoConfigQryService getSsoConfigQryService(){
		return NCLocator.getInstance().lookup(IPtSsoConfigQryService.class);
	}
}
