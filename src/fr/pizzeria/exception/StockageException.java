package fr.pizzeria.exception;

public abstract class StockageException extends Exception {
	private static final long serialVersionUID = 1L;

	public StockageException(String message){
		super(message);
	}
}
