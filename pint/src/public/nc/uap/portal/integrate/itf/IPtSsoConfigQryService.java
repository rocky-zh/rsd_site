package nc.uap.portal.integrate.itf;

import java.util.List;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.system.SSOProviderVO;

/**
 * Portal�����½���ò�ѯ����ӿ�
 * @author licza
 *
 */
public interface IPtSsoConfigQryService {
	/**
	 * �����ݿ��в�ѯ���е�������Ŀ
	 * @return
	 * @throws PortalServiceException
	 */
	public List<SSOProviderVO> getAllConfig() throws PortalServiceException;
}
