package nc.uap.portal.service.impl;

import java.util.List;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.persist.dao.PtBaseDAO;
import nc.uap.portal.service.itf.IPtPageQryService;
import nc.uap.portal.vo.PtPageVO;

/**
 * ҳ���ѯ����ʵ��
 * @author licza
 *
 */
public class PtPageQryServiceImpl implements IPtPageQryService {

	@SuppressWarnings("unchecked")
	@Override
	public PtPageVO[] getPageByGroup(String pk_group)
			throws PortalServiceException {
		String clause = "  pk_group = '"+ pk_group +"' ";
		try {
			List<PtPageVO> list = (List<PtPageVO>)new PtBaseDAO().retrieveByClause(PtPageVO.class, clause);
			if(list != null && list.size() > 0)
				return list.toArray(new PtPageVO[0]);
		} catch (Exception e) {
			LfwLogger.error("��ѯҳ�� SQL : ("+ clause +")�����쳣:" + e, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PtPageVO[] getPageByLevel(Integer level)
			throws PortalServiceException {
		String clause = " fk_pageuser='*' and levela = "+ level +" ";
		try {
			List<PtPageVO> list = (List<PtPageVO>)new PtBaseDAO().retrieveByClause(PtPageVO.class, clause);
			if(list != null && list.size() > 0)
				return list.toArray(new PtPageVO[0]);
		} catch (Exception e) {
			LfwLogger.error("��ѯҳ�� SQL : ("+ clause +")�����쳣:" + e, e);
		}
		return null;
	}

	@Override
	public PtPageVO getPageByPk(String pk) throws PortalServiceException {
		try {
			return (PtPageVO) new PtBaseDAO().retrieveByPK(PtPageVO.class, pk);
		} catch (Exception e) {
			LfwLogger.error("��ѯҳ������쳣:" + e, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PtPageVO[] getPageByUser(String pk_user)
			throws PortalServiceException {
		String clause = " fk_pageuser = '"+ pk_user +"' ";
		try {
			List<PtPageVO> list = (List<PtPageVO>)new PtBaseDAO().retrieveByClause(PtPageVO.class, clause);
			if(list != null && list.size() > 0)
				return list.toArray(new PtPageVO[0]);
		} catch (Exception e) {
			LfwLogger.error("��ѯҳ�� SQL : ("+ clause +")�����쳣:" + e, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PtPageVO[] getSysPagesByModule(String module)
			throws PortalServiceException {
		String clause = " isnull(parentid, '~') = '~' and module = '"+ module +"' ";
		try {
			List<PtPageVO> list = (List<PtPageVO>)new PtBaseDAO().retrieveByClause(PtPageVO.class, clause);
			if(list != null && list.size() > 0)
				return list.toArray(new PtPageVO[0]);
		} catch (Exception e) {
			LfwLogger.error("��ѯҳ�� SQL : ("+ clause +")�����쳣:" + e, e);
		}
		return null;
	}

}
