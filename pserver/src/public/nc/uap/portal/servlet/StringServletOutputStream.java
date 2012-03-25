package nc.uap.portal.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
/**
 * �����ܹ�����Ļ�ȡ�����������
 * @author dengjt
 * 2006-1-24
 */
public class StringServletOutputStream extends ServletOutputStream {

	private ByteArrayOutputStream output = null;
	public StringServletOutputStream(ByteArrayOutputStream out) {
		output = out;
	}

	public void write(int b) throws IOException {
		output.write(b);
	}

}