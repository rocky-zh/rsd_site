package nc.uap.portal.integrate.credential;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.patch.XmlUtilPatch;
import nc.uap.lfw.util.Validator;
import nc.uap.portal.exception.PortalServiceException;
import nc.vo.jcom.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * ƾ֤Reference XML�洢���л�����
 * ���л����XML���Ǵ洢���ļ��У����Ǵ洢�����ݿ��У������л���ת���ַ����洢
 * XML���¸�ʽ��
 * 	<cpreferences>
 *		<cpreference>
 *			<name>accountcode</name>
 *			<value>2005-12-18</value>
 *		</cpreference>
 *		<cpreference>
 *			<name>corppk</name>
 *			<value>1001</value>
 *		</cpreference>
 *		����
 *	</cpreferences>
 * @author yzy
 *  Created on 2006-02-26
 */
public class CredentialReferenceSerializer {

	/**
	 * �����л����XML��ԭΪReference����
	 * @param xml ���л�Ϊ�ַ�����XML
	 * @return ƾ֤��Reference����
	 * @throws PortalServiceException
	 */
	public static ICredentialReference fromXML(String xml)
			throws PortalServiceException {
		ICredentialReference cri = new CredentialReferenceImpl();
		if (Validator.isNull(xml)) {
			return cri;
		}
		try {
			//ʵ����Document
			Document document = XmlUtilPatch.getDocumentBuilder().parse(new InputSource(new StringReader(xml)));
			NodeList nodeList = document.getElementsByTagName("cpreference");
			for (int i=0;i<nodeList.getLength();i++) {
				Element rfe = (Element)nodeList.item(i);
				String name = XMLUtil.getChildNodeValueOf(rfe, "name");
				String value = XMLUtil.getChildNodeValueOf(rfe, "value");
				cri.setValue(name, value);
			}
			return cri;
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
			throw new PortalServiceException(e);
		} catch (SAXException e) {
			Logger.error(e.getMessage(), e);
			throw new PortalServiceException(e);
		}
	}

	/**
	 * ��ƾ֤��Reference�������л�ΪXML�洢
	 * @param cri ƾ֤Reference����
	 * @return ���л�ΪXML���ַ���
	 * @throws PortalServiceException
	 */
	public static String toXML(ICredentialReference cri)
			throws PortalServiceException {
		StringBuffer xmlContext = new StringBuffer("");
		xmlContext.append("<cpreferences>");
		Iterator itr = cri.getMap().entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry) itr.next();
			xmlContext.append("<cpreference>");
			String nameText = (String) entry.getKey();
			String valueText = cri.getValue(nameText);
			xmlContext.append("<name>");
			xmlContext.append(nameText);
			xmlContext.append("</name>");
			xmlContext.append("<value>");
			if (valueText != null && valueText.trim().length() > 0) {
				xmlContext.append(valueText);
			}
			xmlContext.append("</value>");
			xmlContext.append("</cpreference>");
		}
		xmlContext.append("</cpreferences>");
		return xmlContext.toString();
	}
}
