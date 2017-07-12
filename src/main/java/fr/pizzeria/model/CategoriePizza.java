package main.java.fr.pizzeria.model;

import main.java.fr.pizzeria.exception.CategoryNotFoundException;

public enum CategoriePizza {
	
	DEFAULT("Standard"), VIANDE("Viande"), POISSON("Poisson"), VEGAN("Vegan");
	
	private String categorie;
	
	private CategoriePizza(String categorie){
		this.categorie = categorie;
	}
	
	/** Category's description
	 * @return the category description
	 */
	public String getDescription(){
		return this.categorie;
	}

	/**
	 * @param constantName is the name of the category's constant
	 * @return the desired CategoriePizza
	 * @throws CategoryNotFoundException if constanName is invalid
	 */
	public static CategoriePizza findCategoryById(String constantName) throws CategoryNotFoundException {
		for(CategoriePizza cp : CategoriePizza.values()){
			if(cp.toString().equals(constantName)){
				return cp;
			}
		}
		throw new CategoryNotFoundException("Nom de catégorie "+constantName+" invalide !");
	}
	
}
