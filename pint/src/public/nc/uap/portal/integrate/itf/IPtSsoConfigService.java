package nc.uap.portal.integrate.itf;

import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.vo.PtSsopropVO;



/**
 * sso�������ù��������
 * @author gd 2008-12-11
 * @version NC5.5
 * @since NC5.5
 *
 */
public interface IPtSsoConfigService {
	 
	
	/**
	 * �����µ�SSOProviderVO
	 * @param provider
	 */
	public void add(SSOProviderVO provider);
	/**
	 * ����SSOProviderVO
	 * @param provider
	 */
	public void update(SSOProviderVO provider);
	/**
	 * ɾ��provider
	 * @param systemCode
	 */
	public void delete(String systemCode);
 
	/**
	 * �����µ�PtSsopropVO
	 * @param ssoProp
	 */
	public void add(PtSsopropVO ssoProp);
	/**
	 * ����PtSsopropVO
	 * @param ssoProp
	 */
	public void update(PtSsopropVO ssoProp);
	/**
	 * ɾ��
	 * @param pk_ssoprop
	 */
	public void deleteByPK(String pk_ssoprop);
	
}
