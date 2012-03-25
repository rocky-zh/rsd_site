package nc.uap.portal.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.container.om.Preferences;
import nc.uap.portal.om.Display;
import nc.uap.portal.om.LookAndFeel;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.PortletAppDescriptorService;

import org.apache.commons.io.IOUtils;
 
/**
 * XML��ȡ����
 * 
 * @author licza
 * 
 */
public class XmlUtil {

	/**
	 * ��ȡ����
	 * 
	 * @param reader
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static LookAndFeel readLookAndFeel(Reader reader)
			throws JAXBException {
		return (LookAndFeel) JaxbMarshalFactory.newIns().lookupUnMarshaller(LookAndFeel.class).unmarshal(reader);
	}

	/**
	 * ��ȡDisplayʵ��
	 * 
	 * @param reader
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static Display readDisplay(Reader reader) throws JAXBException {
		Display dis = (Display)JaxbMarshalFactory.newIns().lookupUnMarshaller(Display.class).unmarshal(reader) ;
		return dis;
	}

	 

 

	/**
	 * XMLת��ΪportletType
	 * 
	 * @param name
	 * @param input
	 * @return
	 * @throws JAXBException
	 * @throws Exception
	 */
	public static PortletDefinition readPortletType(Reader reader)
			throws JAXBException {
		JAXBContext jc = JAXBContext
				.newInstance("nc.uap.portal.container.om");
		Unmarshaller u = jc.createUnmarshaller();
		return (PortletDefinition) u.unmarshal(reader);
	}

	/**
	 * portletTypeת��ΪXML
	 * 
	 * @param name
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String portletTypeWriter(PortletDefinition pt)
			throws JAXBException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXBContext jc = JAXBContext
				.newInstance("nc.uap.portal.container.om");
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(pt, out);
		try {
			return out.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
 
	/**
	 * preferencesת��ΪXML
	 * 
	 * @param ppt
	 * @return
	 * @throws Exception
	 */
	public static String preferencesWriter(Preferences ppt)
			throws JAXBException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Marshaller marshaller = JaxbMarshalFactory.newIns().lookupMarshaller(Preferences.class);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(ppt, out);
		try {
			return out.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * XMLת��Ϊpreferences
	 * 
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public static  Preferences  preferencesReader(Reader reader) throws JAXBException {
		return ( Preferences ) JaxbMarshalFactory.newIns().lookupUnMarshaller(Preferences.class).unmarshal(reader);
	}

	/**
	 * XMLת��ΪPortletApplicationDefinition
	 * 
	 * @param prjPath
	 * @return
	 */
	public static PortletApplicationDefinition readPortletAppDef(String prjPath) {
		InputStream in = null;
		try {
			File file = new  File(prjPath + "/portalspec/pbase/portalspec/portlet.xml");
			if(!file.exists())
				return null;
 			in = new FileInputStream(file);
			String moduleFileName=prjPath ;
			PortletAppDescriptorService sv = PortalServiceUtil.getPortletAppDescriptorService();
			return sv.read(moduleFileName, in);
		} catch (IOException e) {
			throw new RuntimeException(prjPath + "�µ�portlet.xml�ļ������ڻ��߸�ʽ����");
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
				}
			}
		}
	}
}
