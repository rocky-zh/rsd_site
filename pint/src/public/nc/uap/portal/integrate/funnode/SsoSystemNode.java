package nc.uap.portal.integrate.funnode;

/**
 * ϵͳ��ͨ�ýڵ�VO
 * @author gd 2009-5-20
 * @since NC5.6
 * @version NC5.6
 */
public class SsoSystemNode {
	// �ڵ�����
	private String nodeId;
	// �ڵ�����
	private String nodeName;
	// �ڵ����
	private String nodeCode;
	// ���ڵ����� 
	private String pNodeId;
	// �ڵ�·��
	private String nodeUrl;
	// �ڵ����� 0:�������� 1:ִ�нű�����
	private Integer nodeType;
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getPNodeId() {
		return pNodeId;
	}
	public void setPNodeId(String nodeId) {
		pNodeId = nodeId;
	}
	public String getNodeUrl() {
		return nodeUrl;
	}
	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
}
