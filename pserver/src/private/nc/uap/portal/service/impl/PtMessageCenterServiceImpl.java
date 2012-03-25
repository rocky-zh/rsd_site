package nc.uap.portal.service.impl;


import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.MCConstant;
import nc.uap.portal.integrate.message.vo.PtMessageVO;
import nc.uap.portal.persist.dao.PtBaseDAO;
import nc.uap.portal.service.itf.IPtMessageCenterService;

import org.apache.commons.lang.StringUtils;

public class PtMessageCenterServiceImpl implements IPtMessageCenterService {

	@Override
	public String add(PtMessageVO vo) throws PortalServiceException {
		return add(new PtMessageVO[] { vo })[0];
	}

	public String[] add(PtMessageVO[] vo) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVOs(vo);
		} catch (DAOException e) {
			LfwLogger.error("��Ϣ����ʧ��", e);
			throw new PortalServiceException(e);
		}
	}

	@Override
	public void delete(String[] pk, boolean onwer) throws PortalServiceException {
		LfwLogger.info("ɾ��PKΪ:"+pk+"����Ϣ");
		if(onwer)
			modMessageState(pk, MCConstant.STATE_DELETE);
		else
			modSentMessageState(pk, MCConstant.STATE_DELETE);
		LfwLogger.warn("�ɹ���PKΪ:"+pk+"����Ϣɾ��");
	}

	@Override
	public void read(String pk) throws PortalServiceException {
		LfwLogger.info("�޸�PKΪ:"+pk+"����Ϣ״̬Ϊ�Ѷ�");
		modMessageState(new String[]{pk}, MCConstant.STATE_READ);
		LfwLogger.warn("�ɹ���PKΪ:"+pk+"����Ϣ״̬Ϊ�Ѷ�");
	}

	@Override
	public void trash(String[] pk ,boolean onwer) throws PortalServiceException {
		LfwLogger.info("�޸�PKΪ:"+pk+"����Ϣ״̬Ϊ��ɾ��");
		if(onwer)
			modMessageState(pk, MCConstant.STATE_TRASH);
		else
			modSentMessageState(pk, MCConstant.STATE_TRASH);
		LfwLogger.warn("�ɹ���PKΪ:"+pk+"����Ϣ״̬Ϊ��ɾ��");
	}

	/**
	 * �޸���Ϣ״̬
	 * 
	 * @param pk
	 * @param state
	 * @throws PortalServiceException
	 */
	public void modMessageState(String[] pk, String state)
			throws PortalServiceException {
		StringBuffer sb = new StringBuffer("UPDATE pt_message SET state = ? ");
		if(state != null && state.equals("1"))
			sb.append(" , readtime = getdate() ");
		sb.append("where pk_message in ('"+StringUtils.join(pk, "','")+"') ");
		SQLParameter param = new SQLParameter();
		param.addParam(state);
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate(sb.toString(), param);
		} catch (DAOException e) { 
			LfwLogger.error("��Ϣ�޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}
	/**
	 * �޸��û���������Ϣ״̬
	 * @param pk
	 * @param state
	 */
	public void modSentMessageState(String[] pk, String state) throws PortalServiceException {
		StringBuffer sb = new StringBuffer("UPDATE pt_message SET ownerstate = ? ");
		sb.append("where pk_message in ('"+StringUtils.join(pk, "','")+"') ");
		SQLParameter param = new SQLParameter();
		param.addParam(state);
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate(sb.toString(), param);
		} catch (DAOException e) { 
			LfwLogger.error("��Ϣ�޸�ʧ��", e);
			throw new PortalServiceException(e);
		}
	}
}
