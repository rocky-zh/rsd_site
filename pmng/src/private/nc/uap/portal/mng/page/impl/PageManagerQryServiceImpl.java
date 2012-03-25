package nc.uap.portal.mng.page.impl;

import nc.bs.dao.DAOException;
import nc.portal.portlet.vo.PtPageDeptVO;
import nc.portal.portlet.vo.PtPageRoleVO;
import nc.portal.portlet.vo.PtPageUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.mng.page.itf.IPageManagerQryService;
import nc.uap.portal.persist.dao.PtBaseDAO;

public class PageManagerQryServiceImpl implements IPageManagerQryService {

	@Override
	public PtPageDeptVO[] getPageDeptVOByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		PtPageDeptVO[] vos = null;
		try {
			vos = (PtPageDeptVO[]) dao.queryByCondition(PtPageDeptVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("��ѯ����-���ű�ʱ����", e);
			throw new LfwRuntimeException("��ѯ����-���ű�ʱ����", e);
		}
		return vos;
	}

	@Override
	public PtPageRoleVO[] getPageRoleVOByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		PtPageRoleVO[] vos = null;
		try {
			vos = (PtPageRoleVO[]) dao.queryByCondition(PtPageRoleVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("��ѯ����-��ɫ��ʱ����", e);
			throw new LfwRuntimeException("��ѯ����-��ɫ��ʱ����", e);
		}
		return vos;
	}

	@Override
	public PtPageUserVO[] getPageUserVOByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		PtPageUserVO[] vos = null;
		try {
			vos = (PtPageUserVO[]) dao.queryByCondition(PtPageUserVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("��ѯ����-�û���ʱ����", e);
			throw new LfwRuntimeException("��ѯ����-�û���ʱ����", e);
		}
		return vos;
	}

}
