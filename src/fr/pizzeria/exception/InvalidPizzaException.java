package fr.pizzeria.exception;

public class InvalidPizzaException extends Exception {
	
	/**
	 * Empty constructor
	 */
	public InvalidPizzaException(){
		super("La pizza sp�cifi�e est invalide");
	}
	
	/**Override constructor
	 * @param String message
	 */
	public InvalidPizzaException(String msg){
		super(msg);
	}
	
}
