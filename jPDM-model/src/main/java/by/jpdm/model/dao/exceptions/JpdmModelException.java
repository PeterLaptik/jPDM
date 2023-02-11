package by.jpdm.model.dao.exceptions;

public class JpdmModelException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public JpdmModelException(String msg) {
		super(msg);
	}
	
	public JpdmModelException(String msg, Throwable e) {
		super(msg, e);
	}
}
