package nc.uap.portal.service.itf;

import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtVacationVO;
import nc.uap.portal.vo.PtWeekendVO;

/**
 * �����ղ�ѯ����
 * @author licza
 *
 */
public interface IPtWorkDayQryService { 
	
	/**
	 * ��ü���
	 * @return
	 */
	public PtVacationVO[] getHolidays();
	
	/**
	 * ������⹤����
	 * @return
	 */
	public PtVacationVO[] getSpecialWorkDay();
	
	/**
	 * �����ĩ
	 * @return
	 */
	public Integer[] getWeekend();
	
	/**
	 * �����ĩ��������Ϣ
	 * @return
	 * @throws PortalServiceException
	 */
	public PtWeekendVO getWeekendProp() throws PortalServiceException;
	
	/**
	 * ��ʼ������
	 */
	public void initCache();
}
