package nc.uap.portal.container.driver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.container.om.PortletInfo;
import nc.uap.portal.util.StringManager;


/**
 * Portlet��Դ������.
 *
 *
 */
class ResourceBundleFactory {

    private static final StringManager EXCEPTIONS = StringManager.getManager(ResourceBundleFactory.class.getPackage().getName());

    /** Ĭ�ϵ���Դ�� **/
    private InlinePortletResourceBundle defaultBundle;
    private String productCode;
    private String portlet;
    /**
     * All of the previously loaded bundles.
     */
    private Map<Locale, ResourceBundle> bundles = new HashMap<Locale, ResourceBundle>();

    /**
     * The name of the bundle.
     */
    private String bundleName;

    public ResourceBundleFactory(PortletDefinition dd, PortletInfo windowInfo) {
        bundleName = dd.getResourceBundle();
        if(LfwLogger.isDebugEnabled()) {
            LfwLogger.debug("Resource Bundle Created: "+bundleName);
        }

        PortletInfo info = dd.getPortletInfo();
        this.productCode = dd.getModule();
        this.portlet = dd.getPortletName();
        if(info != null) {
            String title = windowInfo == null ? info.getTitle() : windowInfo.getTitle();
            String shortTitle = windowInfo == null ? info.getShortTitle() : windowInfo.getShortTitle();
            String keywords = windowInfo == null ? info.getKeywords() : windowInfo.getKeywords();
            defaultBundle = new InlinePortletResourceBundle(title, shortTitle, keywords
            );
        }
        else {
        	/**
        	 * ʹ�ñ���
        	 */
        	String title = dd.getDisplayNames().get(0).getDisplayName();
        	defaultBundle = new InlinePortletResourceBundle(title, "", "");
        }
    }

    public ResourceBundle getResourceBundle(Locale locale) {
        if(LfwLogger.isDebugEnabled()) {
            LfwLogger.debug("Resource Bundle: "+bundleName+" : "+locale+" requested. ");
        }
        /**
         * �Ѵ���
         */
        if (bundles.containsKey(locale)) {
            return bundles.get(locale);
        }

        try {
            ResourceBundle bundle = null;
            /**
             * ָ������Դ��
             */
            if(bundleName != null) {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                bundle = ResourceBundle.getBundle(bundleName, locale, loader);
                bundles.put(locale, new CombinedPortletResourceBundle(defaultBundle, bundle));
            }else {
            	/**
            	 * Ĭ����Դ��
            	 * ʹ��NC������з���
            	 */
            	bundle = new DefaultPortletResourceBundle(defaultBundle,locale,productCode,portlet);
                bundles.put(locale, bundle);
            }
        } catch (MissingResourceException mre) {
            if(bundleName != null && LfwLogger.isWarnEnabled()) {
                LfwLogger.warn(EXCEPTIONS.getString("warning.resourcebundle.notfound",bundleName, mre.getMessage()));
            }
            if(LfwLogger.isDebugEnabled()) {
                LfwLogger.debug("Using default bundle for locale ("+locale+").");
            }
            bundles.put(locale, defaultBundle);
        }
        return bundles.get(locale);
    }
}
