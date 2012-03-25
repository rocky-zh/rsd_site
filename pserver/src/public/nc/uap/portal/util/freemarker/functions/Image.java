package nc.uap.portal.util.freemarker.functions;

import java.util.List;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.portal.util.PortalRenderEnv;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * ���������ͼƬ·��
 * <pre>
 * ʹ�÷���:${image("/path/a.jpg")}</br>
 * ָ����Դ:${portal}/apps/${module}/web/path/a.jpg
 * �ļ���Դ:${module}/portalspec/web/path/a.jpg
 * </pre>
 * @author licza
 *
 */
public class Image implements TemplateMethodModel{

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String tempModule = PortalRenderEnv.getCurrentPortletTempletModule();
		String url = arguments.get(0).toString();
		return LfwRuntimeEnvironment.getRootPath() +"/apps/"+tempModule+ url;
	}

}
