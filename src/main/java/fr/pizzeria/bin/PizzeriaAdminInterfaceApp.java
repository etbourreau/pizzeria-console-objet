package fr.pizzeria.bin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.admin.IPizzaDao;
import fr.pizzeria.dao.admin.PizzaDaoJpa;
import fr.pizzeria.ihm.admin.menu.MenuAdmin;

/**
 * @author etbourreau Main Launcher class
 */
public class PizzeriaAdminInterfaceApp {

	public static final Logger LOG = LoggerFactory.getLogger(PizzeriaAdminInterfaceApp.class);

	public static void main(String[] args) {
		LOG.info("Pizzeria started !");

		IPizzaDao dao = new PizzaDaoJpa();
		LOG.info("Dao launched");
		new MenuAdmin(dao);
		LOG.info("Menu launched");
	}
}
