package fr.pizzeria.ihm.client.optionmenu;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.bin.PizzeriaClientConsoleApp;
import fr.pizzeria.dao.client.ClientDaoJpa;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.client.menu.MenuClient;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Pizza;

public class CommanderPizzaOptionMenu extends OptionMenu {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommanderPizzaOptionMenu.class);
	
	public CommanderPizzaOptionMenu(IClientDao dao, MenuClient m) {
		super(dao, m);
		this.libelle = "Passer une commande";
	}
	
	@Override
	public boolean execute() {
		
		List<Pizza> allPizzas = dao.findAllPizzas();
		Commande commande = new Commande(((ClientDaoJpa) dao).getNextNumeroCommande(),
				"Non traité",
				LocalDateTime.now(), null, ((MenuClient) menu).getSession());
		Integer answer = 0;
		
		do {
			try {
				LOG.info("Liste des pizzas:");
				for (Pizza p : allPizzas) {
					LOG.info("{}. {} ({}€)", allPizzas.indexOf(p) + 1, p.getNom(), p.getPrix());
				}
				LOG.info("99. C'est tout!");
				answer = PizzeriaClientConsoleApp.sc.nextInt();
				
				if (answer != 99) {
					commande.addPizza(allPizzas.get(answer - 1));
				}
			} catch (InputMismatchException | IndexOutOfBoundsException e) {
				LOG.info("Réponse invalide !");
			}
			
		} while (answer != 99);
		if (!commande.getPizzas().isEmpty()) {
			dao.commanderPizza(commande);
			LOG.info("Votre commande est enregistrée !");
		}
		
		return true;
	}
	
}
