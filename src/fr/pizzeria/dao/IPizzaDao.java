package fr.pizzeria.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.pizzeria.exception.InvalidPizzaException;
import fr.pizzeria.model.Pizza;

/**
 * @author Utilisateur
 *
 */
public class IPizzaDao{
	
	static ArrayList<Pizza> allPizzas = new ArrayList<Pizza>();
	
	/**
	 * Initializes pizzas
	 */
	public static void init(){
		allPizzas.add(new Pizza(0, "PEP", "Pépéroni", 12.50));
		allPizzas.add(new Pizza(1, "MAR", "Margherita", 14.00));
		allPizzas.add(new Pizza(2, "REI", "La Reine", 11.50));
		allPizzas.add(new Pizza(3, "FRO", "La 4 fromages", 12.00));
		allPizzas.add(new Pizza(4, "CAN", "La cannibale", 12.50));
		allPizzas.add(new Pizza(5, "SAV", "La savoyarde", 13.00));
		allPizzas.add(new Pizza(6, "ORI", "L'orientale", 13.50));
		allPizzas.add(new Pizza(7, "IND", "L'indienne", 14.00));
	}
	
	/**
	 * @return the ArrayList of all pizzas
	 */
	public ArrayList<Pizza> getAllPizzas(){
		return allPizzas;
	}
	
	/**
	 * @param id of the desired pizza
	 * @return the pizza designated by ID or throw InvalidPizzaException
	 * @throws InvalidPizzaException 
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
	 * @return the pizza designated by code or throw InvalidPizzaException
	 * @throws InvalidPizzaException 
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
	 * @throws InvalidPizzaException 
	 */
	public Integer getNextAvailableId() throws InvalidPizzaException{
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
	 * @param Pizza to create
	 * @return true if creation is okay, an SQLException otherwise
	 * @throws SQLException
	 */
	public boolean saveNewPizza(Pizza p) throws SQLException{
		allPizzas.add(p);
		sortPizzasById();
		return true;
	}
	
	/**Update a pizza in local memory
	 * and soon in database
	 * @param Pizza's ID, throws exception if invalid
	 * @param The new Pizza object
	 * @return true if modification is okay, an exception otherwise
	 * @throws InvalidPizzaException
	 * @throws SQLException
	 */
	public boolean updatePizza(int id, Pizza p) throws InvalidPizzaException, SQLException{
		Pizza current = getPizzaById(id);
		int index = allPizzas.indexOf(current);
		allPizzas.set(index, p);
		return true;
	}
	
	/**Remove a pizza from local memory
	 * and soon from database
	 * @param Pizza's ID, throws exception if invalid
	 * @return true if deletion is okay, an exception otherwise
	 * @throws InvalidPizzaException
	 * @throws SQLException
	 */
	public boolean deletePizza(int id) throws InvalidPizzaException, SQLException{
		Pizza current = getPizzaById(id);
		int index = allPizzas.indexOf(current);
		allPizzas.remove(index);
		return true;
	}
	
	
	
}
