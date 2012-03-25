package nc.uap.portal.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.uap.lfw.core.log.LfwLogger;

/**
 * ���������� �����г���ת��ΪKey-Value�Լ���
 * ��ǰ̨����
 * @author licza
 * 
 */
public class ConstantUtil {
	/**
	 * �����г���ת��ΪKey-Value�Լ���
	 * @param <T> 
	 * @param t
	 * @return Key-Value�Լ���
	 */
	public static <T> Set<Map<String,String>> shatter(Class<T> t) {
		Set<Map<String,String>> fieldValSet=new HashSet<Map<String,String>>();
		try {
		Field[]	fieldArray=	t.getFields();
		 for(Field field:fieldArray){
			 String fieldName= field.getName();
			 String fieldVal=	 (String)field.get(fieldName);
			 Map<String,String> fieldMap=new HashMap<String,String>();
			 fieldMap.put("fieldName", fieldName);
			 fieldMap.put("fieldVal",fieldVal );
			 fieldValSet.add(fieldMap);
		 }
		} catch ( Exception e) {
			LfwLogger.info(e.getMessage());
		} 
		return fieldValSet;
	}
}
