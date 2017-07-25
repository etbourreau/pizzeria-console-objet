package fr.pizzeria.exception.client;

public class RegisterClientException extends ClientException {
	
	private static final long serialVersionUID = 1L;
	
	public RegisterClientException() {
		super();
	}
	
	public RegisterClientException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public RegisterClientException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RegisterClientException(String message) {
		super(message);
	}
	
	public RegisterClientException(Throwable cause) {
		super(cause);
	}
	
}
