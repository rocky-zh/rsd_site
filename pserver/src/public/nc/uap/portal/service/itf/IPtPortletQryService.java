package nc.uap.portal.service.itf;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.vo.PtPortletVO;
import nc.uap.portal.vo.PtPreferenceVO;

/**
 * Portlet��Ϣ��ѯ�ӿ�
 * 
 * @author licza
 * 
 */
public interface IPtPortletQryService {

	/**
	 * ��ѯ�û���portlet����
	 * @param pk_user
	 * @param pk_group
	 * @param portletname
	 * @param pagename
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPreferenceVO getUserPortletPreference(String pk_user,String pk_group,String portletname , String pagename) throws PortalServiceException;
	/**
	 * ��ѯ����Portlet����
	 * @param pk_group
	 * @param portletname.
	 * @param pagename
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPreferenceVO getGroupPortletPreference(String pk_group,String portletname , String pagename) throws PortalServiceException;
	/**
	 * �ӻ����л��һ�����Զ������õ�Portlet����
	 * 
	 * @param pk_user �û�����
	 * @param pk_group ���ű���
	 * @param portletName Portlet����
	 * @param portletModule Portletģ��
	 * @param pageModule ҳ��ģ��
	 * @param pageName ҳ������
	 * @return
	 */
	public PortletDefinition findPortlet(String pk_user, String pk_group, String portletName, String portletModule, String pageModule, String pageName);

	/**
	 * �����������Portletʵ��
	 * 
	 * @param pk_portlet Portlet����
	 * @return PortletVO
	 * @throws PortalServiceException
	 */
	public PtPortletVO findPortletByPK(String pk_portlet) throws PortalServiceException;

	 
	/**
	 * ���ϵͳ��PortletVO
	 * 
	 * @param module ģ��
	 * @return ϵͳ��PtPortletVO
	 */
	public PtPortletVO[] getSystemPortlet(String module) throws PortalServiceException;

	/**
	 * ��ü��ż�PortletVO
	 * 
	 * @param module ģ����
	 * @param parentid ԭ��Portlet����
	 * @return ���ż�PortletVO
	 * @throws PortalServiceException
	 */
	public PtPortletVO[] getGroupPortlets(String module, String parentid) throws PortalServiceException;

	/**
	 * ���ϵͳ��Portlet
	 * 
	 * @return ϵͳ��PtPortletVO
	 * @throws PortalServiceException
	 */
	public PtPortletVO[] getSystemPortlet() throws PortalServiceException;

	/**
	 * ��ü��ż�PortletVOs
	 * 
	 * @return ���ż�PortletVOs
	 * @throws PortalServiceException
	 */
	public PtPortletVO[] getGroupPortlets() throws PortalServiceException;

	/**
	 * ����û�˽�е�Portlet
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPortletVO[] getUserDiyPortlets() throws PortalServiceException;

	/**
	 * ��ü��ŵ�Portlet
	 * 
	 * @param pk_group ���ű���
	 * @param portletNames Portlet����(ģ����,Portlet��)
	 * @return PortletVOs
	 * @throws PortalServiceException
	 */
	public PtPortletVO[] getGroupPortlets(String pk_group, String[][] portletNames) throws PortalServiceException;
	/**
	 * 
	 * �ӻ�����ѡ���ż�Portlet
	 * 
	 * @param pk_group ���ű���
	 * @param portletName Portlet����
	 * @param module ģ��
	 * @return ���ż�Portlet����
	 */
	public PortletDefinition findPortletFromGroupCache(String pk_group, String portletName, String module);
	
	/**
	 * �ӻ�����ѡ��ϵͳ��Portlet
	 * 
	 * @param portletId Portlet����
	 * @param module ģ��
	 * @return ϵͳ��Portlet����
	 */
	public PortletDefinition findPortletFromSystemCache(String portletId, String module) ;
	
	/**
	 * ��ü��ŵ�Portlet
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public PtPortletVO[] getGroupPortlets(String pk_group) throws PortalServiceException;
	
	/**
	 * �������Ͳ�ѯPortlet
	 * @param clause
	 * @return
	 */
	PtPortletVO[] qryPortletByClause(String clause);
	
}
