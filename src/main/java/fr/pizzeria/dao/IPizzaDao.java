package fr.pizzeria.dao;

import java.util.Comparator;
import java.util.List;

import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.exception.pizza.SavePizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	
	List<Pizza> findAllPizzas();
	List<Pizza> findPizzasByCategory(CategoriePizza cp);

	void init();

	public void sort(Comparator<Pizza> sorter);

	void saveNewPizza(Pizza pizza) throws SavePizzaException;
	void updatePizza(Pizza pizza) throws UpdatePizzaException;
	void deletePizza(Pizza pizza) throws DeletePizzaException;

	int getNextAvailableId();

	Pizza getPizzaById(int parseInt) throws InvalidPizzaException;

	Pizza getPizzaByCode(String code) throws InvalidPizzaException;

	
}
