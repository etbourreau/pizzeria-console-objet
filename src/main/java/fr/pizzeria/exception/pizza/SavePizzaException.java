package fr.pizzeria.exception.pizza;

public class SavePizzaException extends PizzaException {

	private static final long serialVersionUID = 1L;

	public SavePizzaException() {
		super();
	}

	public SavePizzaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SavePizzaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SavePizzaException(Throwable cause) {
		super(cause);
	}
	
	public SavePizzaException(String message){
		super(message);
	}

}
