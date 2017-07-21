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

	public PizzaDaoMemoire(List<Pizza> pizzas) {
		allPizzas = pizzas;
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
	public Optional<Pizza> getPizzaById(int id) {
		return allPizzas.stream().filter(p -> p.getId() == id).findAny();
	}

	/**
	 * @param code
	 *            of the desired pizza
	 * @return the pizza designated by code or an exception
	 * @throws InvalidPizzaException
	 *             if pizza not found
	 */
	public Optional<Pizza> getPizzaByCode(String code) {
		return allPizzas.stream().filter(p -> p.getCode().equalsIgnoreCase(code)).findAny();

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
	public void updatePizza(Pizza pizza) {
		Optional<Pizza> current = getPizzaById(pizza.getId());
		if (current.isPresent()) {
			int index = allPizzas.indexOf(current.get());
			allPizzas.set(index, pizza);
		} else {
			throw new UpdatePizzaException("Can't update pizza : pizza not found");
		}
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
	public void deletePizza(Pizza pizza) {
		Optional<Pizza> current = getPizzaById(pizza.getId());
		if (current.isPresent()) {
			int index = allPizzas.indexOf(current);
			allPizzas.remove(index);
		} else {
			throw new DeletePizzaException("Can't delete pizza : pizza not found");
		}
	}

	public List<Pizza> findPizzasByCategory(CategoriePizza cp) {
		return allPizzas.stream().filter(p -> p.getCategorie().equals(cp)).collect(Collectors.toList());
	}

}
