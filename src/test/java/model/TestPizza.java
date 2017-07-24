package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class TestPizza {
	private Pizza pizza;
	private CategoriePizza categorie = CategoriePizza.VIANDE;

	@Before
	public void setUp() throws Exception {
		this.pizza = new Pizza(1, "MON", "La montagnarde", 13.5, categorie);
	}

	/*
	 * @Test public void testID() { assertEquals("L'ID n'est pas correct",
	 * this.pizza.getId(), 1); }
	 */
	@Test
	public void testCode() {
		assertEquals("Le code n'est pas correct", this.pizza.getCode(), "MON");
	}

	@Test
	public void testNom() {
		assertEquals("Le nom n'est pas correct", this.pizza.getNom(), "La montagnarde");
	}

	@Test
	public void testPrix() {
		assertThat(this.pizza.getPrix() == 13.5).isTrue().withFailMessage("Le prix n'est pas correct");
	}

	@Test
	public void testCategorie() {
		assertEquals("La catégorie n'est pas correcte", this.pizza.getCategorie(), this.categorie);
	}

}
