package main.java.fr.pizzeria.dao;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.fr.pizzeria.exception.DeletePizzaException;
import main.java.fr.pizzeria.exception.InvalidPizzaException;
import main.java.fr.pizzeria.exception.SavePizzaException;
import main.java.fr.pizzeria.exception.UpdatePizzaException;
import main.java.fr.pizzeria.model.CategoriePizza;
import main.java.fr.pizzeria.model.Pizza;

/**
 * @author Utilisateur
 *
 */
public class PizzaDaoMemoire implements IPizzaDao{
	
	static ArrayList<Pizza> allPizzas = new ArrayList<Pizza>();
	
	/**
	 * Initializes pizzas
	 */
	public static void init(){
		allPizzas.add(new Pizza(0, "PEP", "Pépéroni", 12.50, CategoriePizza.VIANDE));
		allPizzas.add(new Pizza(1, "MAR", "Margherita", 14.00, CategoriePizza.VEGAN));
		allPizzas.add(new Pizza(2, "REI", "La Reine", 11.50, CategoriePizza.VIANDE));
		allPizzas.add(new Pizza(3, "FRO", "La 4 fromages", 12.00, CategoriePizza.VEGAN));
		allPizzas.add(new Pizza(4, "CAN", "La cannibale", 12.50, CategoriePizza.VIANDE));
		allPizzas.add(new Pizza(5, "SAV", "La savoyarde", 13.00, CategoriePizza.VIANDE));
		allPizzas.add(new Pizza(6, "ORI", "L'orientale", 13.50, CategoriePizza.VIANDE));
		allPizzas.add(new Pizza(7, "IND", "L'indienne", 14.00, CategoriePizza.VIANDE));
		allPizzas.add(new Pizza(8, "TRU", "La truite fumée", 13.50, CategoriePizza.POISSON));
		
	}
	
	/**
	 * @return the ArrayList of all pizzas
	 */
	@Override
	public List<Pizza> findAllPizzas(){
		return allPizzas;
	}
	
	/**
	 * @param id of the desired pizza
	 * @return the pizza designated by ID or throw InvalidPizzaException
	 * @throws InvalidPizzaException if pizza not found
	 */
	public Pizza getPizzaById(int id) throws InvalidPizzaException{
		for(Pizza p : allPizzas){
			if(p.getId() == id){
				return p;
			}
		}
		throw new InvalidPizzaException("Pizza ID "+id+" unknown");
	}
	
	/**
	 * @param code of the desired pizza
	 * @return the pizza designated by code or an exception
	 * @throws InvalidPizzaException  if pizza not found
	 */
	public Pizza getPizzaByCode(String code) throws InvalidPizzaException{
		for (Pizza p : allPizzas) {
			if (p.getCode().equalsIgnoreCase(code)){
				return p;
			}
		}
		throw new InvalidPizzaException("Pizza code "+code+" unknown");
	}
	
	/**
	 * @return the next free pizza ID
	 * @throws InvalidPizzaException if pizza not found
	 */
	public Integer getNextAvailableId(){
		for(int i = 0; i < allPizzas.size()+1; i++){
			try{
				getPizzaById(i);
			}catch(InvalidPizzaException exc){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Sorts the pizzas array by ID
	 */
	@SuppressWarnings("unchecked")
	public void sortPizzasById(){
		Collections.sort((List)allPizzas);
	}
	
	/**Save a new pizza in local memory
	 * and soon in database
	 * @param pizza is the pizza to create
	 * @throws SavePizzaException if creation fails
	 */
	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException{
		allPizzas.add(pizza);
		sortPizzasById();
	}
	
	/**Update a pizza in local memory
	 * and soon in database
	 * @param pizza concerned
	 * @throws UpdatePizzaException if update fails
	 */
	@Override
	public void updatePizza(Pizza pizza) throws UpdatePizzaException{
		Pizza current;
		try {
			current = getPizzaById(pizza.getId());
		} catch (InvalidPizzaException e) {
			throw new UpdatePizzaException(e.getMessage());
		}
		int index = allPizzas.indexOf(current);
		allPizzas.set(index, pizza);
	}
	
	/**Remove a pizza from local memory
	 * and soon from database
	 * @param pizza concerned
	 * @throws InvalidPizzaException if pizza not found
	 * @throws SelectableChannel if deletion fails
	 */
	@Override
	public void deletePizza(Pizza pizza) throws DeletePizzaException{
		Pizza current;
		try {
			current = getPizzaById(pizza.getId()
					
					);
		} catch (InvalidPizzaException e) {
			throw new DeletePizzaException(e.getMessage());
		}
		int index = allPizzas.indexOf(current);
		allPizzas.remove(index);
	}
	
	
	
}
