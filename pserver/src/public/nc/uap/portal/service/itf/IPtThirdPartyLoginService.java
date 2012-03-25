package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtTrdauthVO;

public interface IPtThirdPartyLoginService {
	/**
	 * ����һ������
	 * @param auth
	 */
	void addAuth(PtTrdauthVO auth) throws PortalServiceException;
	/**
	 * ��õ�¼����
	 * @param id
	 * @return
	 */
	PtTrdauthVO getAuth(String akey) throws PortalServiceException;
}
