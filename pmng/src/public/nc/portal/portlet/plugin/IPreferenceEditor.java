package nc.portal.portlet.plugin;

import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.data.Dataset;

/**
 * Portlet���ñ༭
 * 
 * @author licza
 * 
 */
public interface IPreferenceEditor {
	
	public static final String PID = "IPreferenceEditor";
	
	/**
	 * �ɴ����PortletID
	 */
	public String getPortletId();
	
	/**
	 * ����FormElement
	 * 
	 * @param ele
	 */
	public void processFormElement(FormElement ele);

	/**
	 * ����ǰ���ݲ���
	 * 
	 * @param parentDs
	 * @param currentDs
	 */
	public void beforeDataSave(Dataset parentDs, Dataset currentDs);

	/**
	 * ��������ݲ���
	 * 
	 * @param parentDs
	 * @param currentDs
	 */
	public void afterDataSave(Dataset parentDs, Dataset currentDs);
}
