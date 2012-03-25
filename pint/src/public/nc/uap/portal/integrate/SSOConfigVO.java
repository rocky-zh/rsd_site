package nc.uap.portal.integrate;

import nc.uap.portal.integrate.system.SSOProviderVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

public class SSOConfigVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758817895767489228L;
	private String systemCode;
	private String systemName;
	// �Ƿ������ϱ���mapping����,Ĭ�ϲ�����,���ú�Ż����IPӳ���滻
	private UFBoolean enableMapping;
	// ���ڴ��mappingIP��ַ
	private String authClass;
	private String gateUrl;
	// �Ӹ����ȡ������ϵͳ�������ܱ����ɵĹ��ܽڵ�
	private String nodesClass;

	public SSOConfigVO(SSOProviderVO provider) {
		this.authClass = provider.getAuthClass();
		this.enableMapping = UFBoolean.valueOf(provider.isEnableMapping());
		this.systemCode = provider.getSystemCode();
		this.systemName = provider.getSystemName();
		this.gateUrl = provider.getGateUrl();
		this.nodesClass = provider.getNodesClass();
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public UFBoolean getEnableMapping() {
		return enableMapping;
	}

	public void setEnableMapping(UFBoolean enableMapping) {
		this.enableMapping = enableMapping;
	}

	public String getAuthClass() {
		return authClass;
	}

	public void setAuthClass(String authClass) {
		this.authClass = authClass;
	}

	public String getGateUrl() {
		return gateUrl;
	}

	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	public String getNodesClass() {
		return nodesClass;
	}

	public void setNodesClass(String nodesClass) {
		this.nodesClass = nodesClass;
	}

}
