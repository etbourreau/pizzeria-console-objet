package fr.pizzeria.exception.pizza;

public class InvalidPizzaException extends PizzaException {

	private static final long serialVersionUID = 1L;
	
	public InvalidPizzaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPizzaException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPizzaException(Throwable cause) {
		super(cause);
	}

	public InvalidPizzaException(){
		super();
	}
	
	public InvalidPizzaException(String msg){
		super(msg);
	}
	
}
