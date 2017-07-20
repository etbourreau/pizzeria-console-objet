package fr.pizzeria.exception.pizza;

public class InvalidPizzaException extends PizzaException {
	
	private static final long serialVersionUID = 1L;

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
