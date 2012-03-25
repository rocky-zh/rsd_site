package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.om.Skin;
import nc.uap.portal.vo.PtSkinVo;;

/**
 * ��ʽ��ѯ
 * 
 * @author dingrf
 * 
 */
public interface IPtPortalSkinQryService {
	/**
	 * ����һ����ʽ
	 * 
	 * @param skinId
	 * @return
	 * @throws PortalServerException
	 */
	public PtSkinVo find(String module ,String themeId , String type ,String skinId) throws PortalServiceException;

	/**
	 * ��������ĳһ����ʽ
	 * 
	 * @param type
	 * @return
	 * @throws PortalServiceException
	 */
	public PtSkinVo[] findByType(String type) throws PortalServiceException;

	/**
	 * ȡ��ʽ����
	 * 
	 * @param type
	 * @return
	 */
	public Skin[] getSkinCache(String type);
	
}
