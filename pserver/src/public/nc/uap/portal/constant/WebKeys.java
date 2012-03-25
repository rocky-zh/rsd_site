package nc.uap.portal.constant;

import nc.uap.lfw.core.common.WebConstant;
/**
 * ҳ�泣����
 * @author dengjt
 *
 */
public class WebKeys extends WebConstant{

	public static final String SYSTEM_CACHE_PORTLET_STATE = "SYSTEM_CACHE_PORTLET_STATE";
	public static final String USER_CACHE_PORTLET_STATE = "USER_CACHE_PORTLET_STATE";

//	public static final String CACHE_NAME = IClusterCacheService.class.getName();
	
	public static final String CA_RADOM = "LOGIN_CA_RADOM";
	public static final String SIGNED_CA_RADOM = "SIGNED_CA_RADOM";

	public static final String CTX = "CTX";
	public static final String PORTALCTX = "PORTALCTX";
	public static final String REQUESTID = "REQUESTID";
	
	// ��ǰ�û�������layout
	public static final String LAYOUT = "LAYOUT";
    /*Portal��·��*/
    public static final String MAIN_PATH = "MAIN_PATH";
	//Ӧ�ö�Ӧpath��
	public static final String APP_PATH = "APP_PATH";
	//���������ñ༭��Ŀ¼·��
	public static final String EDITOR_PATH = "EDITOR_PATH";
	//js�ؼ������Ŀ¼
	public static final String FRAME_PATH = "FRAME_PATH";
	//ȫ��ͼƬĿ¼
	public static final String IMAGE_PATH = "IMAGE_PATH";
	// portlet�߶�
	public static final String PORTLET_HEIGHT = "portletHeight";
	
	public static final String JAVAX_PORTLET_CONFIG = "javax.portlet.config";
	public static final String JAVAX_PORTLET_KEYWORDS = "javax.portlet.keywords";
	public static final String JAVAX_PORTLET_PORTLET = "javax.portlet.portlet";
	public static final String JAVAX_PORTLET_REQUEST = "javax.portlet.request";
	public static final String JAVAX_PORTLET_RESPONSE = "javax.portlet.response";
	public static final String JAVAX_PORTLET_SHORT_TITLE = "javax.portlet.short-title";
	public static final String JAVAX_PORTLET_TITLE = "javax.portlet.title";
	
	
	public static final String PORTLET_CATEGORIES = "PORTLET_CATEGORIES";
	public static final String PORTLET_CLASS_LOADER = "PORTLET_CLASS_LOADER";
	public static final String PORTLET_DECORATE = "PORTLET_DECORATE";
	public static final String PORTLET_URL = "PORTLET_URL";
	public static final String PORTLET_RENDER_PARAMETERS = "PORTLET_RENDER_PARAMETERS";
	public static final String PORTLE_SPRING_ACTION = "PORTLET_SPRING_ACTION";
	
	public static final String RENDER_PORTLET = "RENDER_PORTLET";
	public static final String RENDER_PORTLET_CUR_COLUMN_COUNT = "RENDER_PORTLET_CUR_COLUMN_COUNT";
	public static final String RENDER_PORTLET_CUR_COLUMN_ORDER = "RENDER_PORTLET_CUR_COLUMN_ORDER";
	public static final String RENDER_PORTLET_CUR_COLUMN_POS = "RENDER_PORTLET_CUR_COLUMN_POS";
	public static final String ROLE = "ROLE";
	public static final String SEL_LAYOUT = "SEL_LAYOUT";
	public static final String SEARCH_SEARCH_RESULTS = "SEARCH_SEARCH_RESULTS";
	public static final String SERVLET_CONTEXT_NAME = "SERVLET_CONTEXT_NAME";
	public static final String SESSION_LISTENER = "SESSION_LISTENER";

	public static final String TASK_LIST = "TASK_LIST";

	public static final String THEME_DISPLAY = "themeDisplay";
	public static final String TRANSLATOR_TRANSLATION = "TRANSLATOR_TRANSLATION";
	public static final String TREE_JS_CLICKS = "TREE_JS_CLICKS";
	public static final String UNIT_CONVERTER_CONVERSION = "UNIT_CONVERTER_CONVERSION";
	//��ǰ��¼�û����û���Ϣ�Ϳɲ�����Դ
	public static final String LOGIN_USER = "LOGIN_USER";
	// ��ǰ�û���Ȩ�Ĳ���
	//public static final String USER_AUTH_LAYOUTS = "USER_AUTH_LAYOUTS";
	public static final String USER_PASSWORD = "USER_PASSWORD";
	
	public static final String LOCALE_KEY = "LOCALE_KEY";
	
	public static final String PORTLET_PROCESS_EXCEPTION = "PORTLET_PROCESS_EXCEPTION";
	
	public static final String PORTLET_PROCESS_FORWARD = "PORTLET_PROCESS_FORWARD";
	// PORTLETƾ֤�۹���������
	public static final int PORTLET_SHARE_GIVEUP = -1; // ����
	public static final int PORTLET_SHARE_PRIVATE = 0; // ר��
	public static final int PORTLET_SHARE_APPLICATION = 1; // Ӧ�ù���
	public static final int PORTLET_SHARE_GLOBAL = 2; // ȫ�ֹ���
	// ��̳Ȩ������
	public static final int BBSDIR_SEE_PERMISSION = 0; // �������Ȩ��
	public static final int BBSDIR_PUBLISH_PERMISSION = 1; // ���淢��Ȩ��
	// ������Ȩ������
	public static final int NEWSGROUP_SEE_PERMISSION = 0; // ���������Ȩ��
	public static final int NEWSGROUP_PUBLISH_PERMISSION = 1; // ������׫д����Ȩ��
	public static final int NEWSGROUP_MANAGE_PERMISSION = 2; // ���������Ȩ��
	//�ĵ�Ŀ¼Ȩ��
	public static final int DOC_SEE_PERMISSION = 0; // �ĵ�Ŀ¼���Ȩ��
	public static final int DOC_PUBLISH_PERMISSION = 1; //�ĵ�Ŀ¼׫дȨ��
	// ������Ȩ������
	public static final int COMPDOCLABEL_SEE_PERMISSION = 0; // ���������Ȩ��
	public static final int COMPDOCLABEL_PUBLISH_PERMISSION = 1; // ������׫д����Ȩ��
	// ��̬�����Ȩ��
	public static final int FORM_USE_PERMISSION = 0;
	
	//Web application portlet��URL��־
	public static final String APPLICATION_PORTLET_URL_KEY = "APPLICATION_GATE_URL";
	public static final String SSO_PROVIDER = "SSO_PROVIDER";
	/*//���в����黺�泣��
	public static final String LAYOUT_GROUP_CACHE = "ILayoutGroup_ALL";
	//�û���ʾ�����黺�泣��
	public static final String SHOW_LAYOUT_GROUP_CACHE = "ILayoutGroup_SHOW";*/
	//���в��ֻ��泣��
	public static final String LAYOUT_CACHE = "LAYOUT_ALL";
	//�û���ʾ�����黺�泣��
	public static final String SHOW_LAYOUT_CACHE = "LAYOUT_SHOW";
	
	public static final String EXPIRE_KEY = "$_EXPIRE_KEY";
	
	public static final String EMPTY_GROUP = "00000000000000000000";
	public static final String EMPTY_ORG = "00000000000000000000";
	
	// �ĵ����������滻��ʾ,�ô��ᱻ�����滻Ϊ���õ��ĵ���������ַ
	public static final String DOCSERVERIP_FLAG = "http://docserverip";
}
