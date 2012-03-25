package nc.uap.portal.mng.page.itf;

import nc.portal.portlet.vo.PtPageDeptVO;
import nc.portal.portlet.vo.PtPageRoleVO;
import nc.portal.portlet.vo.PtPageUserVO;

public interface IPageManagerService {

	/**
	 * ����ҳ��-���Ź�ϵvo
	 * @param vo
	 */
	public void createPtPageDeptVO(PtPageDeptVO vo);
	/**
	 * ����ҳ��-�û���ϵvo
	 * @param vo
	 */
	public void createPtPageUserVO(PtPageUserVO vo);
	/**
	 * ����ҳ��-��ɫ��ϵvo
	 * @param vo
	 */
	public void createPtPageRoleVO(PtPageRoleVO vo);
}
