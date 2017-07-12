package main.java.fr.pizzeria.exception;

public class CategoryNotFoundException extends StockageException {
	
	private static final long serialVersionUID = 6341094682983708324L;

	public CategoryNotFoundException(String message) {
		super(message);
	}
	
	
	
}
