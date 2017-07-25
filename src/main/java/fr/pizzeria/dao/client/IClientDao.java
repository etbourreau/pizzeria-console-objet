package fr.pizzeria.dao.client;

import java.util.List;

import fr.pizzeria.dao.DaoGeneral;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Pizza;

public interface IClientDao extends DaoGeneral {
	
	List<Pizza> findAllPizzas();

	Pizza getPizzaById(int idPizza);
	
	List<Commande> findCommandesByClient(Client client);
	
	void commanderPizza(Commande commande);
	
	void registerUser(Client client);
	
}
