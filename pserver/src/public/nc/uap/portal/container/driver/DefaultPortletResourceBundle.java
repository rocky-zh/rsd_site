package nc.uap.portal.container.driver;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import nc.bs.ml.NCLangResOnserver;
import nc.uap.portal.util.ArgumentUtility;
import nc.vo.ml.Language;

/**
 * PortletĬ����Դ <br/>
 * ����ʹ��NC������ʵ��
 * <pre>
 * ������Դ����: 
 * �����ļ���:&quot;/lang/����id/ģ����/ģ����res.properties&quot;
 * ��:portlet����+&quot;.&quot;+key
 * 
 * TCK PORTLET��SPEC:24:����ʹ�� ResourceBundle ��Դ�ļ������壬 ��Portlet container
 * �����Ȳ�����ResourceBundle�ﶨ�����Ϣ�� ���� ResourceBundleû����Щ��Ϣ������û��ʹ��
 * ResourceBundle������ �� portlet container �������д�ڶ����ļ������Ϣ�� ����
 * ResourceBundle �Ͷ����ļ��ﶼû�ж�����Щ��Ϣ�� �� portlet container �����Կ��ַ������ش���
 * �����ڶ���portletʱ��û�ж���resource bundle�����ǰ���Ϣ�����ڲ������ļ��У� ��ʱ portlet
 * container ��������һ�� ResourceBundle �����Ұ���Щ��Ϣ�Ž�����
 * </pre>
 * 
 * @author licza
 * 
 */
public class DefaultPortletResourceBundle extends ResourceBundle {
	private static final String DOT = ".";
	/**
	 * ����
	 */
	private String lan;
	/**
	 * Portlet ID
	 */
	private String portlet;
	/**
	 * ģ��
	 */
	private String module;
	/**
	 * �洢��Դ
	 */
	private Map<String, Object> contents = new LinkedHashMap<String, Object>();
	
	/**
	 * PortalĬ����Դ��
	 * @param dft Ĭ����Դ
	 * @param locale ����
	 * @param module ģ��
	 * @param portlet portlet��
	 */
	public DefaultPortletResourceBundle(InlinePortletResourceBundle dft,
			Locale locale, String module, String portlet) {
		this.module = module;
		this.portlet = portlet;
		/**
		 * ����
		 */
		dump(dft);
		/**
		 * �������Ա���
		 */
		getLangCode(locale);
	}

	/**
	 * ��ʼ����ǰ���Ա���
	 * 
	 * @param locale
	 */
	private void getLangCode(Locale locale) {
		/**
		 * �������֧�ֵ�����
		 */
		Language[] langs = getLangRes().getAllLanguages();
		if (langs != null && langs.length > 0) {
			for (Language lang : langs) {
				/**
				 * �����ͬ
				 */
				if (lang.getLocal().equals(locale)) {
					this.lan = lang.getCode();
				}
			}
		}
		/**
		 * Ĭ�ϼ�������
		 */
		if (this.lan == null)
			this.lan = Language.SIMPLE_CHINESE_CODE;
	}

	@Override
	public Enumeration<String> getKeys() {
		/**
		 * !����ط���õļ����ܲ���ȷ
		 */
		return Collections.enumeration(contents.keySet());
	}

	@Override
	protected Object handleGetObject(String key) {
		ArgumentUtility.validateNotNull("key", key);
		/**
		 * ����������NC����key
		 */
		String resId = portlet + DOT + key;
		
		/**
		 * ��Ĭ��bundle�л�õ�ֵ
		 */
		String dftval = (String) contents.get(key);
		String value = getLangRes().getString(lan, module, dftval, resId);
		return value;
	}

	/**
	 * ��ȡNC������Դ��ʵ��
	 * @return
	 */
	private NCLangResOnserver getLangRes() {
		return NCLangResOnserver.getInstance();
	}

	/**
	 * ������Դ
	 * 
	 * @param bundle
	 */
	private void dump(ResourceBundle bundle) {
		Enumeration<String> e = bundle.getKeys();
		while (e.hasMoreElements()) {
			String value = e.nextElement().toString();
			contents.put(value, bundle.getObject(value));
		}
	}
}
