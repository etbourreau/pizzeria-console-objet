package fr.pizzeria.bin;

import java.util.Scanner;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.client.ClientDaoJpa;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.ihm.client.menu.MenuClient;

public class PizzeriaClientConsoleApp {
	
	public static final Logger LOG = LoggerFactory.getLogger(PizzeriaClientConsoleApp.class);
	
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		LOG.debug("Pizzeria started !");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pizzeria-jpa-unit");
		InitApp.createData(emf);
		LOG.debug("Data created !");
		
		IClientDao dao = new ClientDaoJpa(emf);
		MenuClient m = new MenuClient(dao, null);
		m.show();
	}
	
}
