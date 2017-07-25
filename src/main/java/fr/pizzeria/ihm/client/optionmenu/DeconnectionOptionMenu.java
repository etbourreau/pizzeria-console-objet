package fr.pizzeria.ihm.client.optionmenu;

import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.client.menu.MenuClient;

public class DeconnectionOptionMenu extends OptionMenu {
	
	public DeconnectionOptionMenu(IClientDao dao, MenuClient m) {
		super(dao, m);
		this.libelle = "Se déconnecter";
	}
	
	@Override
	public boolean execute() {
		menu.stopSession();
		return true;
	}
	
}
