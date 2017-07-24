package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.pizzeria.dao.PizzaDaoDb;
import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.exception.pizza.SavePizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class TestDaoDb {

	private PizzaDaoDb dao;
	private Connection c;
	static String DB_DRIVER = "org.h2.Driver";
	static String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	static String DB_USER = "sa";
	static String DB_PASSWORD = "";

	@BeforeClass
	public static void setTable() throws SQLException, ClassNotFoundException {
		String createPizza = "CREATE TABLE PIZZA(id int primary key auto_increment, code varchar(255) UNIQUE, nom varchar(255), prix double(8), idCategorie int(11))";
		Class.forName(DB_DRIVER);
		Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		try (PreparedStatement ps = c.prepareStatement(createPizza)) {
			ps.executeUpdate();
		}
	}

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		generateDbData(DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);
		this.dao = new PizzaDaoDb(DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);
	}

	private void generateDbData(String dbDriver, String dbUrl, String dbUser, String dbPwd)
			throws SQLException, ClassNotFoundException {
		List<Pizza> jeuEssais = new ArrayList<>();
		jeuEssais.add(new Pizza(0, "PEP", "Pépéroni", 12.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(1, "MAR", "Margherita", 14.00, CategoriePizza.VEGAN));
		jeuEssais.add(new Pizza(2, "REI", "La Reine", 11.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(3, "FRO", "La 4 fromages", 12.00, CategoriePizza.VEGAN));
		jeuEssais.add(new Pizza(4, "CAN", "La cannibale", 12.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(5, "SAV", "La savoyarde", 13.00, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(6, "ORI", "L'orientale", 13.50, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(7, "IND", "L'indienne", 14.00, CategoriePizza.VIANDE));
		jeuEssais.add(new Pizza(8, "TRU", "La truite fumée", 13.50, CategoriePizza.POISSON));

		c = DriverManager.getConnection(dbUrl, dbUser, dbPwd);

		String insertPizza = "INSERT INTO PIZZA(id, code, nom, prix, idCategorie) VALUES (?,?,?,?,?)";

		try (PreparedStatement ps = c.prepareStatement(insertPizza)) {
			for (Pizza p : jeuEssais) {
				ps.setInt(1, p.getId());
				ps.setString(2, p.getCode());
				ps.setString(3, p.getNom());
				ps.setDouble(4, p.getPrix());
				ps.setInt(5, p.getCategorie().ordinal());
				ps.executeUpdate();
			}
		}
	}

	@After
	public void tearDown() throws SQLException {
		try (PreparedStatement ps = c.prepareStatement("TRUNCATE TABLE pizza")) {
			ps.execute();
		}
	}

	@Test
	public void testFindAllPizzas() throws SQLException {
		assertEquals(dao.findAllPizzas().size(), 9);
	}

	@Test
	public void testGetPizzaByIdValid() throws InvalidPizzaException, SQLException {
		Pizza p = this.dao.getPizzaById(2);
		assertEquals(p.getNom(), "La Reine");
	}

	@Test
	public void testGetPizzaByCodeValid() throws InvalidPizzaException, SQLException {
		Pizza p = this.dao.getPizzaByCode("REI");
		assertEquals(p.getNom(), "La Reine");
	}

	@Test
	public void testFindPizzasByCategory() throws SQLException {
		CategoriePizza cp = CategoriePizza.VIANDE;
		List<Pizza> list = this.dao.findPizzasByCategory(cp);
		assertEquals(list.size(), 6);
	}

	@Test
	public void testSaveNewPizza() throws SavePizzaException, SQLException {
		this.dao.saveNewPizza(new Pizza(9, "MON", "La montagnarde", 13.5, CategoriePizza.VIANDE));
		assertEquals(this.dao.findAllPizzas().size(), 10);
	}

	@Test(expected = SavePizzaException.class)
	public void testSaveNewPizzaExistingCode() throws SavePizzaException {
		this.dao.saveNewPizza(new Pizza(9, "REI", "La montagnarde", 13.5, CategoriePizza.VIANDE));
	}

	@Test
	public void testUpdatePizza() throws UpdatePizzaException, SQLException {
		Pizza p = this.dao.getPizzaById(3);
		p.setNom("4 fromages");
		this.dao.updatePizza(p);
		assertThat(this.dao.getPizzaById(3).getNom().equals("4 fromages")).isTrue();
	}

	@Test(expected = UpdatePizzaException.class)
	public void testUpdatePizzaInvalidId() throws UpdatePizzaException {
		Pizza p = new Pizza(10, "MON", "La montagnarde", 13.5, CategoriePizza.VIANDE);
		p.setNom("4 fromages");
		this.dao.updatePizza(p);
	}

	@Test(expected = InvalidPizzaException.class)
	public void testDeletePizza() throws DeletePizzaException, SQLException {
		Pizza p = this.dao.getPizzaById(3);
		this.dao.deletePizza(p);
		this.dao.getPizzaById(3);
	}

	@Test(expected = DeletePizzaException.class)
	public void testDeletePizzaInvalidId() throws DeletePizzaException {
		Pizza p = new Pizza(10, "MON", "La montagnarde", 13.5, CategoriePizza.VIANDE);
		this.dao.deletePizza(p);
	}

}
