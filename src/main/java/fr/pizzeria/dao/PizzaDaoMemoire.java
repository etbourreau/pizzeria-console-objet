package fr.pizzeria.dao;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

	public PizzaDaoMemoire(List<Pizza> jeuEssais) {
		allPizzas = jeuEssais;
	}

	/**
	 * @return the ArrayList of all pizzas
	 */
	public List<Pizza> findAllPizzas() {
		return new ArrayList<>(allPizzas);
	}

	/**
	 * @param id
	 *            of the desired pizza
	 * @return the pizza designated by ID or throw InvalidPizzaException
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 */
	public Pizza getPizzaById(int id) throws InvalidPizzaException {
		Optional<Pizza> found = allPizzas.stream().filter(p -> p.getId() == id).findAny();
		if (found.isPresent()) {
			return found.get();
		} else {
			LOG.debug("Can't find pizza by id {}", id);
			throw new InvalidPizzaException("Pizza ID " + id + " unknown");
		}
	}

	/**
	 * @param code
	 *            of the desired pizza
	 * @return the pizza designated by code or an exception
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 */
	public Pizza getPizzaByCode(String code) throws InvalidPizzaException {
		Optional<Pizza> found = allPizzas.stream().filter(p -> p.getCode().equalsIgnoreCase(code)).findAny();
		if (!found.isPresent()) {
			LOG.debug("Can't find pizza by code {}", code);
			throw new InvalidPizzaException("Pizza code " + code + " unknown");
		} else {
			return found.get();
		}

	}

	/**
	 * @return the next free pizza ID
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 */
	public int getNextAvailableId() {
		return allPizzas.stream().mapToInt(Pizza::getId).max().getAsInt() + 1;
	}

	/**
	 * Sorts the pizzas array by ID asc
	 */
	public void sort(Comparator<Pizza> sorter) {
		Collections.sort(allPizzas, sorter);
	}

	/**
	 * Save a new pizza in local memory and soon in database
	 * 
	 * @param pizza
	 *            is the pizza to create
	 * @throws SavePizzaException
	 *             if creation fails
	 */
	public void saveNewPizza(Pizza pizza) throws SavePizzaException {
		try{
			getPizzaByCode(pizza.getCode());
			throw new SavePizzaException("Code already present");
		} catch (InvalidPizzaException e) {
			allPizzas.add(pizza);
			sort(Comparator.comparing(Pizza::getId));
		}
	}

	/**
	 * Update a pizza in local memory and soon in database
	 * 
	 * @param pizza
	 *            concerned
	 * @throws UpdatePizzaException
	 *             if update fails
	 */
	public void updatePizza(Pizza pizza) throws UpdatePizzaException {
		Pizza current;
		try {
			current = getPizzaById(pizza.getId());
		} catch (InvalidPizzaException e) {
			LOG.debug("UpdatePizza cannot find desired pizza ({})", e.getMessage());
			throw new UpdatePizzaException(e.getMessage());
		}
		int index = allPizzas.indexOf(current);
		allPizzas.set(index, pizza);
	}

	/**
	 * Remove a pizza from local memory and soon from database
	 * 
	 * @param pizza
	 *            concerned
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 * @throws SelectableChannel
	 *             if deletion fails
	 */
	public void deletePizza(Pizza pizza) throws DeletePizzaException {
		Pizza current;
		try {
			current = getPizzaById(pizza.getId()

			);
		} catch (InvalidPizzaException e) {
			LOG.debug("DeletePizza cannot find desired pizza ({})", e.getMessage());
			throw new DeletePizzaException(e.getMessage());
		}
		int index = allPizzas.indexOf(current);
		allPizzas.remove(index);
	}

	public List<Pizza> findPizzasByCategory(CategoriePizza cp) {
		return allPizzas.stream().filter(p -> p.getCategorie().equals(cp)).collect(Collectors.toList());
	}

	public void init() {
		LOG.debug("DaoMemoire initialized");
	}

}
