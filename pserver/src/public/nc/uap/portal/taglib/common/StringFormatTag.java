package nc.uap.portal.taglib.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * �ַ�����ʽ���ߣ����ַ�����ȡ�ض�����(���ְ�2��������)
 * 
 * @author dengjt 2006-5-25
 */
public class StringFormatTag extends TagSupport {

	private static final long serialVersionUID = 4962372964859397870L;

	private String source;

	private int length;
	
	//�ַ�����׺,ֻ�����ַ������ȳ���length��ָ���ĳ���ʱ�Ż�׷��suffix�ַ���
	private String suffix;

	public int doStartTag() throws JspException {
		if (source == null || source.equals(""))
			return EVAL_PAGE;
		if (length < 0)
			length = 20;
		byte[] bytes = source.getBytes();
		String result;
		int i, j;
		for (j = i = 0; i <= length && i < bytes.length;i ++) {
			if (bytes[i] < 0)
				j ++;
		}
		if(j % 2 == 1)
			result = new String(bytes, 0, i - 1);
		else
			result = new String(bytes, 0, i);
		
		if(source.length() > result.length())
		{
			if(suffix != null && !suffix.equals(""))
			{
				result = result + suffix;
			}
		}
		try {
			pageContext.getOut().print(result);
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	class SplitString {
		private String str;

		private int byteNum;

		public SplitString() {
		}

		public SplitString(String str, int byteNum) {
			this.str = str;
			this.byteNum = byteNum;

		}

		public void splitIt() {

			byte bt[] = str.getBytes();
			if (byteNum > 1) {
				if (bt[byteNum] < 0) {
					String substrx = new String(bt, 0, --byteNum);
				} else {
					String substrex = new String(bt, 0, byteNum);
				}

			} else {
				if (byteNum == 1) {
					if (bt[byteNum] < 0) {
						String substr1 = new String(bt, 0, ++byteNum);
					} else {
						String subStr2 = new String(bt, 0, byteNum);
					}
				} else {
				}
			}
		}
	}
}
