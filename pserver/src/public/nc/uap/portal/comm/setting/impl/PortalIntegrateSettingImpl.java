package nc.uap.portal.comm.setting.impl;

import nc.uap.portal.comm.setting.PtSettingVO;
import nc.uap.portal.comm.setting.itf.IPortalSetting;

/**
 * Portal��������
 * 
 * @author licza
 * 
 */
public class PortalIntegrateSettingImpl implements IPortalSetting {


	@Override
	public PtSettingVO[] getSettings() {
		return new PtSettingVO[] { new PtSettingVO("integrateSetting", "����ƾ�ݹ���",
				"integrateSetting", "/portal/pt/integr/setting/home") };
	}

}
