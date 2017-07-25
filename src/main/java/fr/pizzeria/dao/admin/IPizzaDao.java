package fr.pizzeria.dao.admin;

import java.util.List;

import fr.pizzeria.dao.DaoGeneral;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao extends DaoGeneral {
	
	List<Pizza> findAllPizzas();

	List<Pizza> findPizzasByCategory(CategoriePizza cp);

	void saveNewPizza(Pizza pizza);

	void updatePizza(Pizza pizza);

	void deletePizza(Pizza pizza);

	Pizza getPizzaById(int idPizza);

	Pizza getPizzaByCode(String code);
	
}
