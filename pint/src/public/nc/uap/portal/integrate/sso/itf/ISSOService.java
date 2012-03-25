/**
 * 
 */
package nc.uap.portal.integrate.sso.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.vo.PtSlotVO;
import nc.vo.pub.BusinessException;


/**
 * SSO ƾ֤����ӿڣ� ���ƾ֤�ۡ�ƾ֤������Daoʵ��; ���ĳ���û���¼portal���ĳ��portletӦ�õķ���ƾ֤�������ȼ������У�; �洢ƾ֤
 * @author yzy Created on 2006-02-22
 * @author lkp 
 */
public interface ISSOService {


	/**
	 * ����������ƾ֤������������ƾ֤�ۣ�����������ƾ֤�⣩
	 * @param credentialVO ƾ֤VO
	 * @param slotVO ƾ֤��VO
	 * @throws PortalServiceException
	 */
	public void createCredentials(PtCredentialVO credentialVO, PtSlotVO slotVO) throws BusinessException;
	
	/**
	 * �÷�����һ���Ƚ�ר�еķ�����ֻ�����û�����ȡ��ƾ֤���á�
	 * �÷���ɾ������Ӧ�ù������ƾ֤��
	 * 
	 * @param userId
	 * @param className
	 * @throws BusinessException
	 */
	public void removeApplicationSharedCredentials(String userId, String portletId, String className, Integer shareLevel) throws BusinessException;
	/**
	 * ����һ��ƾ֤��
	 * @param vo
	 * @throws PortalServiceException
	 */
	public void addSlot(PtSlotVO vo) throws PortalServiceException;
	/**
	 * ɾ��һ��ƾ֤����Ϣ
	 * @param pk_slot
	 * @throws PortalServiceException
	 */
	public void removeSlot(String pk_slot) throws PortalServiceException;
}
