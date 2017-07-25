package fr.pizzeria.dao.admin;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.exception.pizza.SavePizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * @author Utilisateur
 *
 */
public class PizzaDaoMemoire implements IPizzaDao {

	private static final Logger LOG = LoggerFactory.getLogger(PizzaDaoMemoire.class);

	private List<Pizza> allPizzas = new ArrayList<>();

	public PizzaDaoMemoire(List<Pizza> pizzas) {
		allPizzas = pizzas;
	}

	/**
	 * @return the ArrayList of all pizzas
	 */
	public List<Pizza> findAllPizzas() {
		LOG.info("Finding all pizzas");
		return new ArrayList<>(allPizzas);
	}

	/**
	 * @param id
	 *            of the desired pizza
	 * @return the pizza designated by ID or throw InvalidPizzaException
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 */
	public Pizza getPizzaById(int id) {
		LOG.info("Getting pizza by id {}", id);
		return allPizzas.stream().filter(p -> p.getId() == id).findAny().orElseThrow(InvalidPizzaException::new);
	}

	/**
	 * @param code
	 *            of the desired pizza
	 * @return the pizza designated by code or an exception
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 */
	public Pizza getPizzaByCode(String code) {
		LOG.info("Getting pizza by code {}", code);
		return allPizzas.stream().filter(p -> p.getCode().equalsIgnoreCase(code)).findAny()
				.orElseThrow(InvalidPizzaException::new);

	}

	/**
	 * Save a new pizza in local memory
	 * 
	 * @param pizza
	 *            is the pizza to create
	 * @throws SavePizzaException
	 *             if creation fails
	 */
	public void saveNewPizza(Pizza pizza) {
		LOG.info("Saving new pizza " + pizza.getCode() + " " + pizza.getNom() + " " + pizza.getPrix() + " "
				+ pizza.getCategorie().getDescription() + "...");
		try {
			getPizzaByCode(pizza.getCode());
			throw new SavePizzaException("Code already present");
		} catch (InvalidPizzaException e) {
			allPizzas.add(pizza);
		}
		LOG.info("...pizza saved");
	}

	/**
	 * Update a pizza in local memory
	 * 
	 * @param pizza
	 *            concerned
	 * @throws UpdatePizzaException
	 *             if update fails
	 */
	public void updatePizza(Pizza pizza) {
		LOG.info("Updating pizza " + pizza.getCode() + " " + pizza.getNom() + " " + pizza.getPrix() + " "
				+ pizza.getCategorie().getDescription() + "...");
		try {
			Pizza current = getPizzaById(pizza.getId());
			int index = allPizzas.indexOf(current);
			allPizzas.set(index, pizza);
		} catch (InvalidPizzaException e) {
			throw new UpdatePizzaException("Can't update pizza : pizza not found");
		}
		LOG.info("...pizza updated");
	}

	/**
	 * Remove a pizza from local memory
	 * 
	 * @param pizza
	 *            concerned
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 * @throws SelectableChannel
	 *             if deletion fails
	 */
	public void deletePizza(Pizza pizza) {
		LOG.info("Deleting pizza " + pizza.getCode() + " " + pizza.getNom() + " " + pizza.getPrix() + " "
				+ pizza.getCategorie().getDescription() + "...");
		try {
			Pizza current = getPizzaById(pizza.getId());
			int index = allPizzas.indexOf(current);
			allPizzas.remove(index);
		} catch (InvalidPizzaException e) {
			throw new DeletePizzaException("Can't delete pizza : pizza not found");
		}
		LOG.info("...pizza deleted");
	}

	/**
	 * 
	 */
	public List<Pizza> findPizzasByCategory(CategoriePizza cp) {
		LOG.info("Finding all pizzas for category " + cp.getDescription());
		return allPizzas.stream().filter(p -> p.getCategorie().equals(cp)).collect(Collectors.toList());
	}

}
