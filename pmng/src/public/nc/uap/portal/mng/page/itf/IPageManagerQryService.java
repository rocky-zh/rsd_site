package nc.uap.portal.mng.page.itf;

import nc.portal.portlet.vo.PtPageDeptVO;
import nc.portal.portlet.vo.PtPageRoleVO;
import nc.portal.portlet.vo.PtPageUserVO;

public interface IPageManagerQryService {
	
	/**
	 * ���ݲ�ѯ������ѯҳ���û���ϵ��
	 * @param sqlWhere
	 * @return
	 */
	public PtPageUserVO[] getPageUserVOByCondition(String sqlWhere);
	
	/**
	 * ���ݲ�ѯ������ѯҳ���ɫ��ϵ��
	 * @param sqlWhere
	 * @return
	 */
	public PtPageRoleVO[] getPageRoleVOByCondition(String sqlWhere);
	
	/**
	 * ���ݲ�ѯ������ѯҳ�沿�Ź�ϵ��
	 * @param sqlWhere
	 * @return
	 */
	public PtPageDeptVO[] getPageDeptVOByCondition(String sqlWhere);
}
