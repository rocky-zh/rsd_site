package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtThemeVO;

/**
 * �����ѯ
 * 
 * @author licza
 * 
 */
public interface IPtPortalThemeQryService {
	/**
	 * ����һ������
	 * 
	 * @param themeId
	 * @return
	 * @throws PortalServerException
	 */
	public PtThemeVO find(String themeId) throws PortalServiceException;
	
	/**
	 * ��ü��ŵ�����
	 * @return
	 * @throws PortalServiceException
	 */
	public PtThemeVO[] getThemeByGroup() throws PortalServiceException;
	
}
