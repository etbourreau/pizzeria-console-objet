package main.java.fr.pizzeria.dao;

import java.util.List;

import main.java.fr.pizzeria.exception.DeletePizzaException;
import main.java.fr.pizzeria.exception.SavePizzaException;
import main.java.fr.pizzeria.exception.UpdatePizzaException;
import main.java.fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	
	List<Pizza> findAllPizzas();

	void saveNewPizza(Pizza pizza) throws SavePizzaException;
	void updatePizza(Pizza pizza) throws UpdatePizzaException;
	void deletePizza(Pizza pizza) throws DeletePizzaException;
	
}
