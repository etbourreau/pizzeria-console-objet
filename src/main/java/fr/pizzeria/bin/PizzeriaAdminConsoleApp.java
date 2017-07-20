package fr.pizzeria.bin;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoMemoire;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * @author etbourreau
 * Main Launcher class
 */
public class PizzeriaAdminConsoleApp {
	
	public static final Logger LOG = LoggerFactory.getLogger(PizzeriaAdminConsoleApp.class);
	
	public static void main(String[] args) {
		LOG.info("Pizzeria started !");

		List<Pizza> jeuEssais = new ArrayList<>();
		jeuEssais.add(new Pizza(0, "PEP", "Pépéroni", 12.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(1, "MAR", "Margherita", 14.00, CategoriePizza.VEGAN));
		jeuEssais.add(new Pizza(2, "REI", "La Reine", 11.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(3, "FRO", "La 4 fromages", 12.00, CategoriePizza.VEGAN));
		jeuEssais.add(new Pizza(4, "CAN", "La cannibale", 12.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(5, "SAV", "La savoyarde", 13.00, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(6, "ORI", "L'orientale", 13.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(7, "IND", "L'indienne", 14.00, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(8, "TRU", "La truite fumée", 13.50, CategoriePizza.POISSON));

		IPizzaDao dao = new PizzaDaoMemoire(jeuEssais);
		new Menu(dao);
	}
}
