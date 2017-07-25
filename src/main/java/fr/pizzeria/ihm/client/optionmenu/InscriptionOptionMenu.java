package fr.pizzeria.ihm.client.optionmenu;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.bin.PizzeriaClientConsoleApp;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.exception.client.InvalidConnectionException;
import fr.pizzeria.exception.client.RegisterClientException;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.client.menu.MenuClient;
import fr.pizzeria.model.Client;

public class InscriptionOptionMenu extends OptionMenu {
	
	private static final Logger LOG = LoggerFactory.getLogger(InscriptionOptionMenu.class);
	
	public InscriptionOptionMenu(IClientDao dao, MenuClient m) {
		super(dao, m);
		this.libelle = "S'inscrire";
	}
	
	@Override
	public boolean execute() {
		String nom = "", prenom = "", email = "", pwd = "";
		String emailRegex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$";
		PizzeriaClientConsoleApp.sc.nextLine();
		
		LOG.info("Entrez votre nom:\n99 pour annuler");
		nom = PizzeriaClientConsoleApp.sc.nextLine();
		if (nom.equals("99")) {
			return false;
		}
		
		LOG.info("Entrez votre prénom:\n99 pour annuler");
		prenom = PizzeriaClientConsoleApp.sc.nextLine();
		if (prenom.equals("99")) {
			return false;
		}
		
		do {
			LOG.info("Entrez votre email:\n99 pour annuler");
			email = PizzeriaClientConsoleApp.sc.nextLine();
			if (!email.matches(emailRegex) && !email.equals("99")) {
				LOG.info("Email invalide");
			}
		} while (!email.matches(emailRegex) && !email.equals("99"));
		if (email.equals("99")) {
			return false;
		}
		
		do {
			LOG.info("Entrez votre mot de passe:");
			pwd = PizzeriaClientConsoleApp.sc.nextLine();
		} while (pwd.isEmpty());
		
		Client session = new Client(nom, prenom, email, pwd);
		try {
			
			dao.registerUser(session);
			LOG.info("Merci pour votre inscription...");
			session = dao.getClientByEmailAndPwd(email, DigestUtils.sha512Hex(pwd));
			menu.setSession(session);
			
		} catch (RegisterClientException e) {
			LOG.info("Vous n'avez pas pu être enregistré...", e);
			return false;
		} catch (InvalidConnectionException e) {
			LOG.info("Connexion à votre compte impossible...", e);
			return false;
		}
		
		return true;
	}
	
}
