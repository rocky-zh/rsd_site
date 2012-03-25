package nc.uap.portal.container.portlet;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.portlet.Event;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.cache.PortalCacheManager;
import nc.uap.portal.container.om.EventDefinition;
import nc.uap.portal.container.om.ModuleApplication;
import nc.uap.portal.container.service.itf.EventProvider;

/**
 * �¼�����ʵ��
 * 
 * @author licza
 */
public class EventProviderImpl implements EventProvider {
	private PortletWindow portletWindow;

	public EventProviderImpl(PortletWindow portletWindow) {
		this.portletWindow = portletWindow;
	}

	/**
	 *�����¼�
	 * 
	 * @see nc.uap.portal.container.service.itf.EventProvider#createEvent(QName, Serializable)
	 */
	@SuppressWarnings("unchecked")
	public Event createEvent(QName qname, Serializable value) throws IllegalArgumentException {
		if (value != null && !isValueInstanceOfDefinedClass(qname, value)) {
			String msg = " ��������:" + value.getClass().getCanonicalName() + "���¼�����Ĳ�ƥ��.";
			LfwLogger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		try {
			if (value == null) {
				return new EventImpl(qname, value);
			} else {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				Writer out = new StringWriter();
				Class<Serializable> clazz = (Class<Serializable>)value.getClass();
				try {
					Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
					//�����л�ΪXML
//					Marshaller marshaller =JaxbMarshalFactory.newIns().lookupMarshaller(clazz);
//					JAXBElement<Serializable> element = new JAXBElement<Serializable>(qname, clazz, value);
//					marshaller.marshal(element, out);
					//��������
					if(value instanceof String){
//						<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
//						<switchIframeContentEvent xmlns="http://ufida.com.cn">/portal/core/uimeta.um?pageId=myprtptprocess&amp;model=nc.portal.pwfm.pagemeta.MyPrtptProcessPageModel&amp;pk_prodef=0001Z01000000008XPSO</switchIframeContentEvent>

						((StringWriter)out).write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
						((StringWriter)out).write("<" + qname.getLocalPart() + " xmlns=\"" + qname.getNamespaceURI() + "\">" + ((String)value).replaceAll("&", "&amp;")+ "</" + qname.getLocalPart() + ">");
					}
					else{
						//�����л�ΪXML
						Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller(); 
						JAXBElement<Serializable> element = new JAXBElement<Serializable>(qname, clazz, value);
						marshaller.marshal(element, out);
					}
					
					
				} finally {
					Thread.currentThread().setContextClassLoader(cl);
				}
				return new EventImpl(qname, out.toString());
			}
		} catch (JAXBException e) {
			// JAXB�쳣 ������󶨴���
			LfwLogger.error("Event handling failed", e);
		} catch (FactoryConfigurationError e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * ����ֵ�Ƿ��Ƕ�������
	 * 
	 * @param qname �¼���
	 * @param value ����ֵ
	 * @return ����������ȷ���
	 */
	private boolean isValueInstanceOfDefinedClass(QName qname, Serializable value) {
		String module = portletWindow.getPortletDefinition().getModule();
		ModuleApplication[] apps = PortalCacheManager.getModuleAppByModuleNs(qname.getNamespaceURI());

		List events = new ArrayList();
		if(apps != null && apps.length > 0){
			for(ModuleApplication app : apps){
				events.addAll(app.getEventDefinitions());
			}
		}
		if (events != null && events.size() > 0) {
			for (Object o : events) {
				EventDefinition def = (EventDefinition)o;
				if (def.getQName() != null) {
					if (def.getQName().equals(qname)) {
						return value.getClass().getName().equals(def.getValueType());
					}
				} else {
					QName tmp = new QName(qname.getNamespaceURI(), def.getName());
					if (tmp.equals(qname)) {
						return value.getClass().getName().equals(def.getValueType());
					}
				}
			}
		}
		// δ�����¼�
		return false;
	}
}
