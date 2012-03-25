package nc.uap.portal.testcase;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import junit.framework.TestCase;

/**
 * �����½servlet��������
 * 
 * @author licza
 * 
 */
public class SsoClientTestCase extends TestCase {
	/**
	 * �����������URL
	 */
	static final String serverURL = "http://localhost/portal/registerServlet";
	static final String contentType = "content-type";
	static final String contentTypeValue = "application/x-java-serialized-object,charset=utf-8";

	/**
	 * ���Ի�ȡ��֤�ֶ�
	 */
	public void testGetAuthField() throws Exception {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {

			URL url = new URL(serverURL);
			URLConnection conn = url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setChunkedStreamingMode(2048);
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty(contentType, contentTypeValue);
			oos = new ObjectOutputStream(conn.getOutputStream());
			oos.writeInt(0);
			oos.flush();
			oos.close();
			ois = new ObjectInputStream(conn.getInputStream());
			Object obj = ois.readObject();
			if (obj instanceof Exception) {
				throw (Exception) obj;
			}
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(ois);
		}
	}
}
