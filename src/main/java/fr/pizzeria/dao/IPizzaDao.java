package fr.pizzeria.dao;

import java.util.List;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	
	List<Pizza> findAllPizzas();

	List<Pizza> findPizzasByCategory(CategoriePizza cp);

	void saveNewPizza(Pizza pizza);

	void updatePizza(Pizza pizza);

	void deletePizza(Pizza pizza);

	Pizza getPizzaById(int parseInt);

	Pizza getPizzaByCode(String code);
	
}
