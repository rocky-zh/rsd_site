package nc.uap.portal.integrate.sso.impl;
 
 
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.inte.PintServiceFactory;
import nc.uap.portal.integrate.credential.CredentialReferenceSerializer;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.sso.itf.ISSOService;
import nc.uap.portal.persist.dao.PtBaseDAO;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IEncodeService;
import nc.uap.portal.vo.PtSlotVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;

/**
 * SSO ƾ֤����ʵ����
 */
public class SSOServiceImpl implements ISSOService {
	private IEncodeService iEncodeService;
	public IEncodeService getIEncodeService() {
		return this.iEncodeService;
	}
 
	public void setIEncodeService(IEncodeService iEncodeService) {
		this.iEncodeService = iEncodeService;
	}

	public void createCredentials(PtCredentialVO credentialVO, PtSlotVO slotVO) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			slotVO.setActive(Integer.valueOf(1));
            /**
             * �����ж��Ƿ����������ƾ֤�ۣ���������ڣ����²��룻
             * ������ڣ����ȡ������Ϣ��
             */
			PtSlotVO[] oldSlots = PintServiceFactory.getSsoQryService().getSlots(slotVO.getUserid(), slotVO.getPortletid(), 
					                 slotVO.getClassname(), slotVO.getSharelevel());
			String slotId = null;
			if(oldSlots == null || oldSlots.length == 0)
				slotId = dao.insertVO(slotVO);
			else
			    slotId = oldSlots[0].getPk_slot();
			
			//����ƾ֤����Ϣ
			credentialVO.setFk_slot(slotId);
			//���û��������
			credentialVO.setPassword(iEncodeService.encode(credentialVO.getPassword()));
			// ����ƾ֤������reference����Ϣ
			String xmlStr = CredentialReferenceSerializer
					                    .toXML(credentialVO.getCredentialReference());
			credentialVO.setReference(xmlStr);
			// ȷ��һ��ƾ֤�۽���Ӧһ��ƾ֤��¼
			String delSql = "delete from pt_credential where fk_slot = '" + slotId + "'";
			dao.executeUpdate(delSql);
			dao.insertVO(credentialVO);
		} catch (BusinessException e) {
			Logger.error("����credentialʧ��", e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "SSOServiceImpl-000000", null, new String[]{e.getMessage()})/*����credentialʧ��{0}*/);
		}
	}

	public void removeApplicationSharedCredentials(String userId, String portletId, String className, Integer shareLevel) throws BusinessException {
		PtSlotVO[] slots = PintServiceFactory.getSsoQryService().getSlots(userId, portletId, className, shareLevel);
		if(slots == null || slots.length == 0)
			return ;
		String slotId = slots[0].getPk_slot();
		PtBaseDAO dao = new PtBaseDAO();
		try {
			// ȷ��һ��ƾ֤�۽���Ӧһ��ƾ֤��¼
			String delSql = "delete from pt_credential where fk_slot = '" + slotId + "'";
			dao.executeUpdate(delSql);
			dao.deleteByPK(PtSlotVO.class, slotId);
		} catch(BusinessException e) {
			Logger.error("ɾ��credentialʧ��", e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "SSOServiceImpl-000001", null, new String[]{e.getMessage()})/*ɾ��credentialʧ��{0}*/);
		}
	}
	
	public void addSlot(PtSlotVO vo) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new PortalServiceException("Slot����ʧ��");
		}
	}

	@Override
	public void removeSlot(String pk_slot) throws PortalServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			// ȷ��һ��ƾ֤�۽���Ӧһ��ƾ֤��¼
			String delSql = "delete from pt_credential where fk_slot = '" + pk_slot + "'";
			dao.executeUpdate(delSql);
			dao.deleteByPK(PtSlotVO.class, pk_slot);
		} catch(BusinessException e) {
			Logger.error("ɾ��ƾ֤��ʧ��", e);
			throw new PortalServiceException("ɾ��ƾ֤��ʧ��");
		}
	}
}
