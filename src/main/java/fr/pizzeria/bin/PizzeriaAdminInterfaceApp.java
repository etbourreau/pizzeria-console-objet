package fr.pizzeria.bin;


import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoDb;
import fr.pizzeria.ihm.menu.Menu;

/**
 * @author etbourreau
 * Main Launcher class
 */
public class PizzeriaAdminInterfaceApp {
	
	public static final Logger LOG = LoggerFactory.getLogger(PizzeriaAdminInterfaceApp.class);
	
	public static void main(String[] args) throws SQLException {
		LOG.info("Pizzeria started !");

		final String DB_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_CONNECTION = "jdbc:mysql://localhost:3306/pizzeria";
		final String DB_USER = "pizzayolo";
		final String DB_PASSWORD = "pizza";

		IPizzaDao dao = new PizzaDaoDb(DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);
		new Menu(dao);
	}
}
