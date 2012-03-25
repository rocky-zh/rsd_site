package nc.uap.portal.integrate.sso.impl;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.itf.IPtSsoConfigQryService;
import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.uap.portal.persist.dao.PtBaseDAO;
import nc.uap.portal.sso.util.SSOUtil;
import nc.uap.portal.vo.PtSsopropVO;
/**
 * Portal�����½���ò�ѯ����ʵ����
 * @author licza
 *
 */

public class PtSsoConfigQryServiceImpl implements IPtSsoConfigQryService {

	@Override
	public List<SSOProviderVO> getAllConfig() throws PortalServiceException {
		List<SSOProviderVO> providerlist = new  ArrayList<SSOProviderVO>();
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List <PtSsopropVO> vos = (List <PtSsopropVO> ) dao.retrieveAll(PtSsopropVO.class);
			if(vos != null && !vos.isEmpty()){
				for(PtSsopropVO vo : vos){
					SSOProviderVO  p = SSOUtil.prop2provider(vo);
					providerlist.add(p);
				}
			}
			return providerlist;
		} catch (DAOException e) {
			String err = "�����ݿ��л��sso�����ļ�ʧ��!";
			LfwLogger.error(err,e);
			throw new PortalServiceException(err);
		}
	}
}
