package fr.pizzeria.dao;

import java.util.List;

import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.InvalidPizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	
	List<Pizza> findAllPizzas();

	void saveNewPizza(Pizza pizza) throws SavePizzaException;
	void updatePizza(Pizza pizza) throws UpdatePizzaException;
	void deletePizza(Pizza pizza) throws DeletePizzaException;

	int getNextAvailableId();

	Pizza getPizzaById(int parseInt) throws InvalidPizzaException;
	
}
