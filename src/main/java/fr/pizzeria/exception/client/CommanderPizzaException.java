package fr.pizzeria.exception.client;

public class CommanderPizzaException extends ClientException {
	
	private static final long serialVersionUID = 1L;
	
	public CommanderPizzaException() {
		super();
	}
	
	public CommanderPizzaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public CommanderPizzaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommanderPizzaException(String message) {
		super(message);
	}
	
	public CommanderPizzaException(Throwable cause) {
		super(cause);
	}
	
}
