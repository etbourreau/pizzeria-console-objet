package fr.pizzeria.ihm.client.optionmenu;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.bin.PizzeriaClientConsoleApp;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.client.menu.MenuClient;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Pizza;

public class ListerCommandesOptionMenu extends OptionMenu {
	
	private static final Logger LOG = LoggerFactory.getLogger(ListerCommandesOptionMenu.class);
	
	public ListerCommandesOptionMenu(IClientDao dao, MenuClient m) {
		super(dao, m);
		this.libelle = "Lister mes commandes";
	}
	
	@Override
	public boolean execute() {
		List<Commande> allCommandes = dao.findCommandesByClient(((MenuClient) menu).getSession());
		
		LOG.info(String.valueOf(allCommandes.size()));
		
		if (allCommandes.isEmpty()) {
			LOG.info("Vous n'avez encore aucune commande");
		} else {
			allCommandes.stream().forEach(c -> {
				String pizzas = c.getPizzas().stream().map(Pizza::getNom).collect(Collectors.joining(", "));
				LOG.info("{} - {}", c.getDateCommande(), pizzas);
			});
		}
		LOG.info("Appuyez sur Entrée pour continuer...");
		PizzeriaClientConsoleApp.sc.nextLine();
		PizzeriaClientConsoleApp.sc.nextLine();
		return true;
	}
	
}
