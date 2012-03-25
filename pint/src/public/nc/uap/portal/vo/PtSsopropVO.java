package nc.uap.portal.vo;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBException;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.integrate.system.SSOReference;
import nc.uap.portal.util.JaxbMarshalFactory;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * SSO����VO
 * 
 * @author licza
 * 
 */
public class PtSsopropVO extends SuperVO {

	private static final long serialVersionUID = -8315434627599144409L;
	
	private String pk_ssoprop;
	
	/**
	 * ϵͳ����
	 */
	private String systemcode;
	
	/**
	 * ϵͳ����
	 */
	private String systemname;
	
	/**
	 * �Ƿ������ϱ���mapping���� Ĭ�ϲ�����,���ú�Ż����IPӳ���滻
	 */
	private UFBoolean enablemapping;

	/**
	 * ��֤��
	 */
	private String authclass;

	/**
	 * ���ɹ��ܽڵ���
	 */
	private String nodesclass;

	/**
	 * ��֤����
	 */
	private String gateurl;

	/**
	 * ��չ��
	 */
	private String reference;
	
	private SSOReference ref;
 
	public String getSystemcode() {
		return systemcode;
	}

	public void setSystemcode(String systemcode) {
		this.systemcode = systemcode;
	}

	public String getSystemname() {
		return systemname;
	}

	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}

	public UFBoolean getEnablemapping() {
		return enablemapping;
	}

	public void setEnablemapping(UFBoolean enablemapping) {
		this.enablemapping = enablemapping;
	}

	public String getAuthclass() {
		return authclass;
	}

	public void setAuthclass(String authclass) {
		this.authclass = authclass;
	}

	public String getNodesclass() {
		return nodesclass;
	}

	public void setNodesclass(String nodesclass) {
		this.nodesclass = nodesclass;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getGateurl() {
		return gateurl;
	}

	public void setGateurl(String gateurl) {
		this.gateurl = gateurl;
	}

	@Override
	public String getPKFieldName() {
		return "pk_ssoprop";
	}

	@Override
	public String getTableName() {
		return "pt_ssoprop";
	}

	public SSOReference getRef() {
		if(ref == null){
			ref = (SSOReference) JaxbMarshalFactory.newIns().encodeXML(SSOReference.class, getReference());
		}
		return ref;
	}

	public void setRef(SSOReference ref) {
		this.ref = ref;
	}

	public String getPk_ssoprop() {
		return pk_ssoprop;
	}

	public void setPk_ssoprop(String pk_ssoprop) {
		this.pk_ssoprop = pk_ssoprop;
	}
	
}
