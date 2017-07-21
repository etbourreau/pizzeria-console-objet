package fr.pizzeria.exception.pizza;

public class PizzaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PizzaException() {
		super();
	}

	public PizzaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PizzaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PizzaException(Throwable cause) {
		super(cause);
	}

	public PizzaException(String message) {
		super(message);
	}

}
