package main.java.fr.pizzeria.exception;

public class InvalidPizzaException extends StockageException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor
	 */
	public InvalidPizzaException(){
		super("La pizza spécifiée est invalide");
	}
	
	/**Override constructor
	 * @param String message
	 */
	public InvalidPizzaException(String msg){
		super(msg);
	}
	
}
