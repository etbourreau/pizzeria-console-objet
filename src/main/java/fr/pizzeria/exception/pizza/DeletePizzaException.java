package fr.pizzeria.exception.pizza;

public class DeletePizzaException extends PizzaException {

	private static final long serialVersionUID = 1L;

	public DeletePizzaException() {
		super();
	}

	public DeletePizzaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeletePizzaException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeletePizzaException(Throwable cause) {
		super(cause);
	}

	public DeletePizzaException(String message) {
		super(message);
	}

}
