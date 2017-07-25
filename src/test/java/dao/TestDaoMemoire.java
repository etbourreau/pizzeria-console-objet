package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.pizzeria.dao.admin.IPizzaDao;
import fr.pizzeria.dao.admin.PizzaDaoMemoire;
import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.exception.pizza.SavePizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class TestDaoMemoire {

	private IPizzaDao dao;

	@Before
	public void setUp() {
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

		this.dao = new PizzaDaoMemoire(jeuEssais);
	}

	@Test
	public void testInit() throws SQLException {
		assertEquals(dao.findAllPizzas().size(), 9);
	}

	@Test
	public void testFindAllPizzas() throws SQLException {
		assertEquals(dao.findAllPizzas().size(), 9);
	}

	@Test
	public void testGetPizzaByIdValid() throws SQLException {
		Pizza p = this.dao.getPizzaById(2);
		assertEquals(p.getNom(), "La Reine");
	}

	@Test(expected = InvalidPizzaException.class)
	public void testGetPizzaByIdInvalidPizzaException() throws InvalidPizzaException {
		this.dao.getPizzaById(9);
	}

	@Test
	public void testGetPizzaByCodeValid() throws InvalidPizzaException {
		Pizza p = this.dao.getPizzaByCode("REI");
		assertEquals(p.getNom(), "La Reine");
	}

	@Test(expected = InvalidPizzaException.class)
	public void testGetPizzaByCodeInvalidPizzaException() throws InvalidPizzaException {
		this.dao.getPizzaByCode("AAA");
	}

	@Test
	public void testFindPizzasByCategoryInvalidPizzaException() {
		CategoriePizza cp = CategoriePizza.VIANDE;
		List<Pizza> list = this.dao.findPizzasByCategory(cp);
		assertEquals(list.size(), 6);
	}

	@Test
	public void testSaveNewPizza() throws SavePizzaException {
		this.dao.saveNewPizza(new Pizza(9, "MON", "La montagnarde", 13.5, CategoriePizza.VIANDE));
	}

	@Test(expected = SavePizzaException.class)
	public void testSaveNewPizzaExistingCode() throws SavePizzaException {
		this.dao.saveNewPizza(new Pizza(9, "REI", "La montagnarde", 13.5, CategoriePizza.VIANDE));
	}

	@Test
	public void testUpdatePizza() throws InvalidPizzaException, UpdatePizzaException {
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
	public void testDeletePizza() throws InvalidPizzaException, DeletePizzaException {
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
