package fr.pizzeria.ihm.client.optionmenu;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.bin.PizzeriaClientConsoleApp;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.exception.client.InvalidConnectionException;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.client.menu.MenuClient;
import fr.pizzeria.model.Client;

public class ConnectionOptionMenu extends OptionMenu {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConnectionOptionMenu.class);
	
	public ConnectionOptionMenu(IClientDao dao, MenuClient m) {
		super(dao, m);
		this.libelle = "Se connecter";
	}
	
	@Override
	public boolean execute() {
		String email = "", pwd = "";
		PizzeriaClientConsoleApp.sc.nextLine();
		
		LOG.info("Entrez votre email:\n99 pour annuler");
		email = PizzeriaClientConsoleApp.sc.nextLine();
		if (email.equals("99")) {
			return false;
		}
		
		LOG.info("Entrez votre mot de passe:");
		pwd = PizzeriaClientConsoleApp.sc.nextLine();
		
		Client session;
		try {
			
			session = dao.getClientByEmailAndPwd(email, DigestUtils.sha512Hex(pwd));
			menu.setSession(session);
			
		} catch (InvalidConnectionException e) {
			LOG.info("Connexion à votre compte impossible...", e);
			return false;
		}
		
		return true;
	}
	
}
