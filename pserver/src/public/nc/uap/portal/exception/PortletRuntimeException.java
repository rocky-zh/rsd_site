package nc.uap.portal.exception;

/**
 * ������װPortlet����ʱ�׳����쳣
 * 
 * @author licza
 * 
 */
public class PortletRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3689927126047332894L;

	/**
	 * �кõ���ʾ��Ϣ
	 */
	private String hint;
	/**
	 * ��ʾ��ִ��JS���
	 */
	private String exec;

	public PortletRuntimeException(String message, String hint, String exec) {
		super(message);
		this.hint = hint;
		this.exec = exec;
	}

	public PortletRuntimeException(String message, String hint, String exec,
			Throwable cause) {
		super(message, cause);
		this.hint = hint;
		this.exec = exec;
	}

	public PortletRuntimeException() {
		super();
	}

	public PortletRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PortletRuntimeException(String message) {
		super(message);
	}

	public PortletRuntimeException(Throwable cause, String hint, String exec) {
		super(cause);
		this.hint = hint;
		this.exec = exec;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getExec() {
		return exec;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}

	public String getHint() {
		if (hint == null)
			hint = this.getMessage();
		if (hint == null) {
			if (this.getCause() != null)
				hint = this.getCause().getMessage();
		}
		return hint;
	}
}
