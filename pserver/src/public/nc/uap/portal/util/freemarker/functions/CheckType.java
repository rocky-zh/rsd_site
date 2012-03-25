package nc.uap.portal.util.freemarker.functions;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * ȡ����������(FreeMarkerģ�巽��)
 * 
 * @author licza.
 * 
 */
public class CheckType implements TemplateMethodModel {
	@SuppressWarnings("unchecked")
	@Override
	public Object exec(final List arg) throws TemplateModelException {
		final String typeName = arg.get(0).toString().toLowerCase();
		return typeName;
	}
}
