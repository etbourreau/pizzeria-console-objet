package fr.pizzeria.model;

import fr.pizzeria.exception.pizza.CategoryNotFoundException;

public enum CategoriePizza {
	
	DEFAULT(0, "Standard"), VIANDE(1, "Viande"), POISSON(2, "Poisson"), VEGAN(3, "Vegan");
	
	private int id;
	private String categorie;
	
	private CategoriePizza(int id, String categorie) {
		this.id = id;
		this.categorie = categorie;
	}
	
	/** Category's description
	 * @return the category description
	 */
	public String getDescription(){
		return this.categorie;
	}

	/**
	 * Category's description
	 * 
	 * @return the category description
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            is the id of the category's constant
	 * @return the desired CategoriePizza
	 * @throws CategoryNotFoundException
	 *             if id is not present
	 */
	public static CategoriePizza findCategoryById(int id) throws CategoryNotFoundException {
		for (CategoriePizza cp : CategoriePizza.values()) {
			// PizzeriaAdminConsoleApp.LOG.info("test " + cp.getDescription() +
			// " " + cp.getId());
			if (cp.getId() == id) {
				return cp;
			}
		}
		throw new CategoryNotFoundException("Category ID " + id + " invalid");
	}

	/**
	 * @param constantName
	 *            is the name of the category's constant
	 * @return the desired CategoriePizza
	 * @throws CategoryNotFoundException
	 *             if constantName is invalid
	 */
	public static CategoriePizza findCategoryByConstantName(String constantName) throws CategoryNotFoundException {
		for(CategoriePizza cp : CategoriePizza.values()){
			if(cp.toString().equals(constantName)){
				return cp;
			}
		}
		throw new CategoryNotFoundException("Category name " + constantName + " invalid");
	}
	
}
