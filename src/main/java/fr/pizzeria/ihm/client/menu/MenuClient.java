package fr.pizzeria.ihm.client.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.bin.PizzeriaClientConsoleApp;
import fr.pizzeria.dao.client.ClientDaoJpa;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.client.optionmenu.CommanderPizzaOptionMenu;
import fr.pizzeria.ihm.client.optionmenu.ConnectionOptionMenu;
import fr.pizzeria.ihm.client.optionmenu.DeconnectionOptionMenu;
import fr.pizzeria.ihm.client.optionmenu.InscriptionOptionMenu;
import fr.pizzeria.ihm.client.optionmenu.ListerCommandesOptionMenu;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.model.Client;

public class MenuClient implements Menu {
	
	private static final Logger LOG = LoggerFactory.getLogger(MenuClient.class);
	
	private Client session;
	private List<OptionMenu> options = new ArrayList<>();
	IClientDao dao;
	
	public MenuClient(IClientDao dao, Client c) {
		session = c;
		this.dao = dao;
		this.loadOptionMenus();
	}
	
	public Client getClient() {
		return this.session;
	}
	
	public void loadOptionMenus() {
		options = new ArrayList<>();
		if (Optional.ofNullable(session).isPresent()) {
			options.add(new CommanderPizzaOptionMenu(dao, this));
			options.add(new ListerCommandesOptionMenu(dao, this));
			options.add(new DeconnectionOptionMenu(dao, this));
		} else {
			options.add(new ConnectionOptionMenu(dao, this));
			options.add(new InscriptionOptionMenu(dao, this));
		}
	}
	
	public void setSession(Client c) {
		session = c;
		this.loadOptionMenus();
	}
	
	public void stopSession() {
		session = null;
		this.loadOptionMenus();
	}
	
	public Client getSession() {
		return session;
	}
	
	public void show() {
		Integer answer = 0;
		do {
			
			try {
				if (Optional.ofNullable(session).isPresent()) {
					LOG.info("/*/*/*/*/*/*/*/ Compte de " + session.getPrenom() + " " + session.getNom()
							+ " \\*\\*\\*\\*\\*\\*\\*\\");
				} else {
					LOG.info("/*/*/*/*/*/*/*/ Pizzeria \\*\\*\\*\\*\\*\\*\\*\\");
				}
				for (OptionMenu om : options) {
					LOG.info("{}. {}", (options.indexOf(om) + 1), om.getLibelle());
				}
				LOG.info("99. Quitter");
				answer = PizzeriaClientConsoleApp.sc.nextInt();
				
				if (answer != 99) {
					options.get(answer - 1).execute();
				}
				
			} catch (InputMismatchException | IndexOutOfBoundsException exception) {
				LOG.info("Option invalide");
			}
			
		} while (answer != 99);
		if (dao instanceof ClientDaoJpa) {
			((ClientDaoJpa) dao).closeDao();
		}
		LOG.debug("Pizzeria closed");
		System.exit(0);
	}
	
	@Override
	public void setStatus(String message, int severity) {
		return;
	}
	
	@Override
	public void hideContenu() {
		return;
		
	}
	
	@Override
	public void setContenu(JPanel panel) {
		return;
	}
	
}
