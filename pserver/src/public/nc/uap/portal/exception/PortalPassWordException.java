package nc.uap.portal.exception;

/**
 * Portal�����쳣
 * @author licza
 *
 */
public class PortalPassWordException extends RuntimeException {
    private static final long serialVersionUID = 6878364986674394167L;
    public  PortalPassWordException(String msg){
    	super(msg);
    }
}