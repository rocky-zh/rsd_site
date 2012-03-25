package nc.uap.portal.integrate.sso.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.credential.CredentialWrapper;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.vo.PtSlotVO;

 
/**
 * SSOƾ֤�Ĳ�ѯ�����ӿ�
 * 
 * @author lkp
 *
 */
public interface ISSOQueryService {
	
	/**
	 * ���ƾ֤����
	 * 
	 * ���ҹ���
	 * ���sharelevel==0����ͨ��portletId + userId + sharelevel ���в���
	 * ���sharelevel==1,��ͨ��classname + userId + sharelevel ���в���
	 * ���sharelevel==2,��ͨ��classname + sharelevel ���в���
	 * 
	 * @param userId
	 * @param portletId
	 * @param className
	 * @param sharelevel
	 * @return
	 */
	public PtCredentialVO getCredentials(String userId, String portletId,
			String className, Integer sharelevel) throws PortalServiceException ;
	
	/**
	 * ����portletid��className��ȡ���е�CredentialWrapper����
	 * ����ǰϵͳӵ�е�����slotvo/credentialVO����Ϣ��
	 * 
	 * @param portletId
	 * @param className
	 * @param sharelevel
	 * @return
	 * @throws PortalServiceException
	 */
	public CredentialWrapper[] getPortletCredentials(String portletId,
			String className,Integer sharelevel) throws PortalServiceException;
	
	/**
	 * ���ƾ֤�۶���
	 * 
	 * ���ҹ���
	 * ���sharelevel==0����ͨ��portletId + userId + sharelevel ���в���
	 * ���sharelevel==1,��ͨ��classname + userId + sharelevel ���в���
	 * ���sharelevel==2,��ͨ��classname + sharelevel ���в��� 
	 * 
	 * @param userId
	 * @param portletId
	 * @param className
	 * @param sharelevel
	 * @return
	 */
	public PtSlotVO[] getSlots(String userId, String portletId,
			               String className,Integer sharelevel) throws PortalServiceException;
	/**
	 * �����������ƾ֤�۶���
	 * @param pk_slot
	 * @return
	 * @throws PortalServiceException
	 */
	public PtSlotVO getSlotByPK(String pk_slot)throws PortalServiceException;
}
