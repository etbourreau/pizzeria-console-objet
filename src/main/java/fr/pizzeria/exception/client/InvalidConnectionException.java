package fr.pizzeria.exception.client;

public class InvalidConnectionException extends ClientException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidConnectionException() {
		super();
	}
	
	public InvalidConnectionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	public InvalidConnectionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public InvalidConnectionException(String arg0) {
		super(arg0);
	}
	
	public InvalidConnectionException(Throwable arg0) {
		super(arg0);
	}
	
}
