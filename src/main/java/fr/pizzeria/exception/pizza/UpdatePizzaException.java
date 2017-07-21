package fr.pizzeria.exception.pizza;

public class UpdatePizzaException extends PizzaException {

	private static final long serialVersionUID = 1L;

	public UpdatePizzaException() {
		super();
	}

	public UpdatePizzaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UpdatePizzaException(String message, Throwable cause) {
		super(message, cause);
	}

	public UpdatePizzaException(Throwable cause) {
		super(cause);
	}

	public UpdatePizzaException(String message) {
		super(message);
	}

}
