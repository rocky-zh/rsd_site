package nc.uap.portal.util.freemarker.functions;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
 import java.util.Map;

 import nc.uap.portal.util.freemarker.FreeMarkerTools;

import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * ����cssģ��
 * @author licza
 *
 */
public class Js implements TemplateDirectiveModel {
	/**JSǰ׺����**/
	public static final String PR = "<script>\r\n";
	/**JS��׺����**/
	public static final String SX = "\r\n</script>";
	/**JS��������**/
	public static final String VARIABLE_NAME = "_JS_VARIABLE_LIST_";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Writer out = new StringWriter();
		body.render(out);
		//·��
		String path = out.toString();
		FreeMarkerTools.warpTmps(env, params, body, PR, VARIABLE_NAME, SX, path);
	}
}
