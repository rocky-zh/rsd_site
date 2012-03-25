package nc.uap.portal.comm.setting.impl;

import nc.uap.portal.comm.setting.PtSettingVO;
import nc.uap.portal.comm.setting.itf.IPortalSetting;
/**
 * Portal������
 * @author licza
 *
 */
public class PtPortalSystemSetting implements IPortalSetting {

	@Override
	public PtSettingVO[] getSettings() {
		return new  PtSettingVO[]{
				new PtSettingVO("changePassWd","�޸�����","changePassWd","/portal/pages/changePasswd.jsp"),
				new PtSettingVO("langSetting","��������","langSetting","/portal/pages/langSetting.jsp"),
				new PtSettingVO("themeSetting","��������","themeSetting","/portal/pt/setting/themeList"),
				new PtSettingVO("templateSetting","Ƥ������","templateSetting","/portal/pt/setting/templateList"),
				new PtSettingVO("addPortlet","����Portlet","addPortlet","/portal/pages/addPortlet.jsp")

				
		};
	}

}
