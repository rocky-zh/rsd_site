package nc.uap.portal.constant;

/**
 * Portal������ <BR/>
 * ������һЩPortal����ʱ��Ҫ��õĳ���
 * 
 * @author licza
 * 
 */
public class PortalEnv {

	/**Portalģ��·��**/
	public static final String PORTAL_HOME_DIR = "/portalhome";
	
	public static final String FOLDER_TALLY = "/";
	/**
	 * PageƤ��·��
	 */
	public static final String PAGE_FOLDER = "/page/";
	/**
	 * layoutƤ��·��
	 */
	public static final String LAYOUT_FOLDER = "/layout/";
	
	/**
	 * portletƤ��·��
	 */
	public static final String PORTLET_PATH = "/portlet/";
	
	/**
	 * Page
	 */
	public static final String TYPE_PAGE = "page";
	
	/**
	 * layout 
	 */
	public static final String TYPE_LAYOUT  = "layout";
	
	/**
	 * portlet 
	 */
	public static final String TYPE_PORTLET  = "portlet";
	
	/**
	 * Ƥ��·��
	 */
	public static final String SKIN_PATH = "portalspec/ftl/portaldefine/skin/";

	/**
	 * ����Ƥ��·��
	 */
	public static final String COMM_FOLDER = "/comm/";

	/**
	 * FreeMarkerģ���׺.
	 */
	public static final String FREE_MARKER_SUFFIX = ".ftl";
	/**
	 * Ĭ��ģ������
	 * **/
	public static final String DEFAULT_TEMPLATE_NAME = "default";

	/**
	 * Ĭ��Ƥ������
	 * 
	 * @deprecated Ӧ�ӿ��л���û���Ĭ��Ƥ��
	 * **/
	public static final String DEFAULT_SKIN_NAME = "webclassic";
	
	/**Ĭ�������ռ�**/
	public static final String DEFAULT_NAME_SPACE="http://www.ufida.com.cn";
	
	/**Ĭ����ҳ��ַ**/
	public static final String DEFAULT_PAGE = "/pt/home/index";
	
	/**Ĭ��ϵͳ����**/
	public static final String DEFAULT_SYSTEM_TITLE = "portal.default.title";
	
	/**
	 * ���Portal����
	 * @return
	 */
	public static String getPortalName(){
		return "portal";
		//NcServiceFacility.getAppendProductService().getNCPortalProp(key)
	}
	/**
	 * ���Portal����ģ������
	 * @return
	 */
	public static String getPortalCoreName(){
		return "pserver";
	}
	/**
	 * ���Ĭ��ģ������
	 * @return
	 */
	public static String getDefaultTempleteName(){
		return DEFAULT_TEMPLATE_NAME;
	}
	/**
	 * ���Ĭ��Ƥ������
	 * @return
	 */
	public static String getDefaultSkinName(){
		return DEFAULT_SKIN_NAME;
	}
	/**Portalģ�����**/
	public static final String PORTALMODULECODE = "E001";
}
