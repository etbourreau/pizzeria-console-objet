package fr.pizzeria.ihm;

import fr.pizzeria.dao.IPizzaDao;

public abstract class OptionMenu {
	protected String libelle;
	protected IPizzaDao dao;
	protected Menu menu;
	
	/** Default constructor
	 * @param reference to the DAO
	 * @param reference to the main menu
	 */
	public OptionMenu(IPizzaDao dao, Menu m) {
		super();
		this.dao = dao;
		this.menu = m;
	}

	/**
	 * @return the OptionMenu label
	 */
	public String getLibelle() {
		return libelle;
	}

	/** Sets the OptionMenu label
	 * @param String label
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**Abstract method for OptionMenu use
	 * @return true if execution is done, false otherwise
	 */
	public abstract boolean execute();
}
