package nc.uap.portal.service.impl;

import nc.bs.dao.DAOException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.persist.dao.PtBaseDAO;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;
import nc.uap.portal.service.itf.IPtPluginService;

/**
 * �������ʵ��
 * 
 * @author licza
 * 
 */
public class PtPluginServiceImpl implements IPtPluginService {

	@Override
	public void add(PtExtension ex) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ:" + ex.getTitle() + "����ʧ��", e);
			throw new PortalServiceException(e);
		}

	}

	@Override
	public void add(PtExtension[] exs) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ�鱣��ʧ��", e);
			throw new PortalServiceException(e);
		}

	}

	@Override
	public void delete(PtExtension ex) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void delete(PtExtension[] exs) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ��ɾ��ʧ��", e);
			throw new PortalServiceException(e);
		}

	}

	@Override
	public void update(PtExtension ex) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ�޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void update(PtExtension[] exs) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void add(PtExtensionPoint ex) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ:" + ex.getTitle() + "����ʧ��", e);
			throw new PortalServiceException(e);
		}

	}

	@Override
	public void add(PtExtensionPoint[] exs) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ�鱣��ʧ��", e);
			throw new PortalServiceException(e);
		}

	}

	@Override
	public void delete(PtExtensionPoint ex) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void delete(PtExtensionPoint[] exs) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ��ɾ��ʧ��", e);
			throw new PortalServiceException(e);
		}

	}

	@Override
	public void update(PtExtensionPoint ex) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ�޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void update(PtExtensionPoint[] exs) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void clearModule(String moduleName) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("delete from " + (new PtExtension()).getTableName() +" where module =' "+ moduleName +"'");
		} catch (DAOException e) {
			LfwLogger.error("��չ����ʧ��", e);
			throw new PortalServiceException(e);
		}		
	}
}
