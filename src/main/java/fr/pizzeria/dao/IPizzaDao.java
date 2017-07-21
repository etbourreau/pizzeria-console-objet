package fr.pizzeria.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	
	List<Pizza> findAllPizzas() throws SQLException;

	List<Pizza> findPizzasByCategory(CategoriePizza cp) throws SQLException;

	void saveNewPizza(Pizza pizza);

	void updatePizza(Pizza pizza);

	void deletePizza(Pizza pizza);

	Optional<Pizza> getPizzaById(int parseInt) throws SQLException;

	Optional<Pizza> getPizzaByCode(String code) throws SQLException;
	
}
