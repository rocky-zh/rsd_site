package nc.uap.portal.user.itf;

import java.util.List;

import nc.uap.portal.om.Page;

/**
 * PortalȨ�޷���
 * 
 * @author licza
 * 
 */
public interface IPortalPowerService {
	List<Page> filterPagesByUserResource(Page[] pages);

	boolean hasPower(String originalid);
}
