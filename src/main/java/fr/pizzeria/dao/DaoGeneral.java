package fr.pizzeria.dao;

import java.util.ArrayList;
import java.util.List;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Pizza;

public interface DaoGeneral {
	
	List<Pizza> findAllPizzas();
	
	default List<Pizza> findPizzasByCategory(CategoriePizza categoriePizza) {
		return new ArrayList<>();
	}
	
	Pizza getPizzaById(int parseInt);
	
	default void saveNewPizza(Pizza pizza) {
	}
	
	default void updatePizza(Pizza pizza) {
	}
	
	default void deletePizza(Pizza p) {
	}
	
	default void registerUser(Client client) {
	}
	
	default Client getClientByEmailAndPwd(String email, String pwd) {
		return null;
	}
	
	default List<Commande> findCommandesByClient(Client client) {
		return new ArrayList<>();
	}
	
	default void commanderPizza(Commande commande) {
	}
	
}
