package fr.pizzeria.bin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Pizza;

public class InitApp {
	
	private static final Logger LOG = LoggerFactory.getLogger(InitApp.class);
	
	private InitApp() {
	}
	
	public static void createData(EntityManagerFactory emf) {
		List<Pizza> jeuEssais = new ArrayList<>();
		jeuEssais.add(new Pizza(null, "PEP", "Pépéroni", 12.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(null, "MAR", "Margherita", 14.00, CategoriePizza.VEGAN));
		jeuEssais.add(new Pizza(null, "REI", "La Reine", 11.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(null, "FRO", "La 4 fromages", 12.00, CategoriePizza.VEGAN));
		jeuEssais.add(new Pizza(null, "CAN", "La cannibale", 12.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(null, "SAV", "La savoyarde", 13.00, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(null, "ORI", "L'orientale", 13.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(null, "IND", "L'indienne", 14.00, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(null, "TRU", "La truite fumée", 13.50, CategoriePizza.POISSON));
		Client user = new Client("Admin", "Super", "root", "root");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		jeuEssais.stream().forEach(em::persist);
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
}
