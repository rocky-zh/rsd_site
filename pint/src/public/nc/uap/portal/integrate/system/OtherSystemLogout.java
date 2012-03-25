package nc.uap.portal.integrate.system;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * ������ϵͳ�ǳ���
 * <pre>
 * ���������ϵͳע���˵�½·������ע��ҳ������һ��Ƕ��ע��ҳ���IFrame��ʵ��Portal���伯�ɵĵ�����ϵͳ��ͬʱ�ǳ���
 * </pre>
 * @author licza
 *
 */
public class OtherSystemLogout {
	/** ע����ַ **/
	public static final String LOGOUT_URL = "LOGOUT_URL";
	/**
	 * �������ϵͳ��½ע���ű�Ƭ��
	 * @param pool
	 * @return
	 */
	 StringBuffer makeOtherSysLogoutPart(Map<String, SSOProviderVO> pool) {
		StringBuffer output = new StringBuffer();
		String head = "<iframe  width='0' height='0'  frameborder='0' vspace='0' hspace='0' scrolling='no' src='";
		String tile = "'></iframe>";
		for(SSOProviderVO provider : pool.values()){
			String logoutUrl = provider.getValue(LOGOUT_URL);
			/**
			 * ���������ע����ַ
			 */
			if(logoutUrl != null && logoutUrl.length() > 0) 
				output.append(head+logoutUrl+tile);
		}
		return output;
	}
	/**
	 * ��õ�����ϵͳ��ע���ű�
	 * @return
	 */
	public String getOtherSysLogoutScript(){
		Map<String, SSOProviderVO> pool = ProviderFetcher.getInstance().getProvidersPool();
		if(pool == null || pool.isEmpty()){
			return StringUtils.EMPTY;
		}else{
			return makeOtherSysLogoutPart(pool).toString();
		}
		
	}
	
}
