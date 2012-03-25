package nc.uap.portal.service.itf;

import nc.uap.portal.vo.PtModuleVO;

/**
 * Portalģ����ɾ�ķ���
 * 
 * @author licza
 * 
 */
public interface IPtPortalModuleService {
	/**
	 * ����һ��PtModuleVO����
	 * 
	 * @param vo
	 * @return
	 */
	public String add(PtModuleVO vo);

	/**
	 * ����һ��PtModuleVO����
	 * 
	 * @param vo
	 */
	public void add(PtModuleVO[] vos);

	/**
	 * ����һ��PtModuleVO
	 * 
	 * @param vos
	 */
	public void update(PtModuleVO[] vos);

	/**
	 * ����һ��Module���������ţ�
	 * 
	 * @param vos
	 */
	public void updateAll(PtModuleVO vo);
}
