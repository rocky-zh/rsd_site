package nc.uap.portal.exception;

import nc.uap.lfw.core.exception.LfwRuntimeException;
/**
 * Portalģ��ҵ���߼�ʹ�õ�����ʱ�쳣
 * @author dengjt
 *
 */
public class PtRuntimeException extends LfwRuntimeException {
	private static final long serialVersionUID = 8774863645419572267L;

	public PtRuntimeException(String message, String hint,
			Throwable cause) {
		super(message, hint, cause);
	}

	public PtRuntimeException(String message, String hint) {
		super(message, hint);
	}

	public PtRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PtRuntimeException(String message) {
		super(message);
	}

	public PtRuntimeException(Throwable cause) {
		super(cause);
	}

}
