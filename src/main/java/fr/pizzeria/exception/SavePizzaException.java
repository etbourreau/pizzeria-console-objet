package main.java.fr.pizzeria.exception;

public class SavePizzaException extends StockageException {

	private static final long serialVersionUID = 1L;
	
	public SavePizzaException(String message){
		super(message);
	}

}
