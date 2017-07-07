package fr.pizzeria.model;

import fr.pizzeria.exception.CategoryNotFoundException;

public enum CategoriePizza {
	
	DEFAULT("Standard"), VIANDE("Viande"), POISSON("Poisson"), VEGAN("Vegan");
	
	private String categorie;
	private CategoriePizza(String categorie){
		this.categorie = categorie;
	}
	
	public String getDescription(){
		return this.categorie;
	}

	public static CategoriePizza findCategoryById(String value) throws CategoryNotFoundException {
		for(CategoriePizza cp : CategoriePizza.values()){
			if(cp.toString().equals(value)){
				return cp;
			}
		}
		throw new CategoryNotFoundException("Nom de catégorie "+value+" invalide !");
	}
	
}
