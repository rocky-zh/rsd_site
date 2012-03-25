package nc.uap.portal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.servlet.StringServletResponse;


/**
 * ����
 * @author licza
 *
 */
public final class ToolKit {
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean notNull(String str) {
		return str != null && str.trim().length() > 0;
	}
	
	/**
	 * �ж������Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static final boolean notNull(Object[] os){
		return os != null && os.length > 0;
	}
	/**
	 * �жϼ����Ƿ�Ϊ��
	 * @param cs
	 * @return
	 */
	public static final boolean notNull(Collection<?> cs){
		return cs != null && !cs.isEmpty();
	}
	
	/**
	 * ����������Դ��ִ�з���
	 * @param o
	 * @param m
	 * @param params
	 * @param targetDs
	 * @param currentDs
	 * @return
	 * @throws Throwable
	 */
	public static final Object execMethodWithOtherDataSource(Object o, Method m, Object[] params, String targetDs, String currentDs) throws Throwable{
		InvocationInfoProxy.getInstance().setUserDataSource(targetDs);
		try {
			return m.invoke(o, params);
		} catch (IllegalArgumentException e) {
			LfwLogger.error(e.getMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			LfwLogger.error(e.getMessage(), e);
			return null;
		} catch (InvocationTargetException e) {
			LfwLogger.error(e.getMessage(), e);
			return null;
		}catch (Throwable e) {
			throw e;
		}finally{
			InvocationInfoProxy.getInstance().setUserDataSource(currentDs);
		}
	}
	/**
	 * ��ȡ����������Ӧ����
	 * @param url
	 * @return
	 */
	/**
	 * ��ȡ����������Ӧ����
	 * @param url
	 * @return
	 * @deprecated �벻Ҫʹ���������,����ɾ��
	 */
	public static final String contextResponseFetcher(String url){
		StringServletResponse stringResponse = new StringServletResponse(LfwRuntimeEnvironment.getWebContext().getResponse());
		HttpServletRequest request =  (LfwRuntimeEnvironment.getWebContext().getRequest());
		String html = null;
		try {
			request.getRequestDispatcher(url).forward(request, stringResponse);
			html =  new String(stringResponse.getString().getBytes("gbk"));
		} catch (Throwable e) {
			LfwLogger.error("���Ի�ȡ��Դʱ�����쳣" + e.getMessage(),e);
		}  finally{
			
		}
		return html;
	}
}

