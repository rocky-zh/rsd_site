package nc.uap.portal.integrate.credential;

import java.util.Enumeration;
import java.util.Map;


/**
 * ƾ֤Reference�ӿڣ�SSO��ƾ֤�����û���������������������֮�⣬����
 * 	һЩ�����Ϣ����NC�����ס���˾�ȡ�ƾ֤Reference����Щ��Ϣ�Ľӿ�
 * @author yzy
 *	Created on 2006-02-26
 */
public interface ICredentialReference {
	/**
	 * ����Ԫ�ض�
	 * @param key 
	 * @param value
	 */
	public void setValue(String key,String value);
	
	/**
	 * ����Ԫ�ضԣ���KEY�ɶ�Value��
	 * @param key
	 * @param values
	 */
	public void setValues(String key,String[] values);
	
	/**
	 * ͨ��KEY���VALUE��������VALUE�����ص�һ����
	 * @param key
	 * @return
	 */
	public String getValue(String key);
	
	/**
	 * ͨ��KEY���VALUES
	 * @param key
	 * @return
	 */
	public String[] getValues(String key);
	
	/**
	 * ���KEY��Enumeration
	 * @return
	 */
	public Enumeration getNames();
	
	/**
	 * ���Ԫ�ضԵ�MAP
	 * @return
	 */
	public Map<String, String[]> getMap();
	
	/**
	 * �Ӵ洢�����»�ȡReference
	 * @param key
	 */
	public void reset(String key);
	
	/**
	 * ����Reference���洢��ȥ
	 *
	 */
	public void store();
}
