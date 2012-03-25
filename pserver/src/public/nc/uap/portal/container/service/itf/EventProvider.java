package nc.uap.portal.container.service.itf;

import java.io.Serializable;

import javax.portlet.Event;
import javax.xml.namespace.QName;

/**
 *�¼������ӿ�
 * 
 * @author licza
 * 
 */
public interface EventProvider {
	/**
	 * ����һ���¼�
	 * 
	 * @param name �¼�����
	 * @param value ����
	 * @return
	 * @throws IllegalArgumentException �����쳣
	 */
	Event createEvent(QName name, Serializable value) throws IllegalArgumentException;
}
