package nc.uap.portal.util.freemarker.functions;

import java.util.List;

import nc.uap.lfw.util.LanguageUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * ���﷭��(FreeMarkerģ�巽��)
 * <pre> ʹ�÷�����:��${multiLang("forum","forum.var49")}</pre>
 * @author licza
 *
 */
public class MultiLang implements TemplateMethodModel{

	@Override
	public Object exec(List arg) throws TemplateModelException {
		int len = arg.size();
		if(len == 1){
			String key = (String)arg.get(0);
			return LanguageUtil.get(key);
		}
		
		if(len ==2){
			String productCode = (String)arg.get(0);;
			String key = (String)arg.get(1);
			return LanguageUtil.getByProductCode(productCode, key);
		}
		
		return null;
	}

}
