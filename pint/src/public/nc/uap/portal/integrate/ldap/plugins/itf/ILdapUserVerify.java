package nc.uap.portal.integrate.ldap.plugins.itf;

import java.util.Map;

/**
 * Ldap��֤����ӿ�
 * @author licza
 *
 */
public interface ILdapUserVerify {
	static final String PID = "ldapuserverify";

	public boolean verifyPasswd(String userdn, String password,
			Map<String, Object> param);

}
