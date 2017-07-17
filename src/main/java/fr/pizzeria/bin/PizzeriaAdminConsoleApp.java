package fr.pizzeria.bin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.ihm.Menu;

/**
 * @author etbourreau
 * Main Launcher class
 */
public class PizzeriaAdminConsoleApp {
	
	public static final Logger LOG = LoggerFactory.getLogger(PizzeriaAdminConsoleApp.class);
	
	public static void main(String[] args) {
		LOG.info("Pizzeria initialized !");
		new Menu();
	}
}
