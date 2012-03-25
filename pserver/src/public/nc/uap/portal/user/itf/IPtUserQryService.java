package nc.uap.portal.user.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.user.entity.IUserVO;

/**
 * Portal�û���ѯ����ӿ�
 * @author licza
 *
 */
public interface IPtUserQryService {
	/**
	 * �����û���������û�VO
	 * @param pkUser
	 * @return
	 * @throws PortalServiceException
	 */
	IUserVO getUserById(String pkUser) throws PortalServiceException;
}
