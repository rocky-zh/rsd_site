package nc.uap.portal.comm.setting.itf;

import nc.uap.portal.comm.setting.PtSettingVO;

/**
 * Portal���ýӿ�
 * 
 * @author licza
 * 
 */
public interface IPortalSetting {
	public static final String PID = "PortalSetting";

	/**
	 * ���������
	 * 
	 * @return
	 */
	PtSettingVO[] getSettings();

	 
}
