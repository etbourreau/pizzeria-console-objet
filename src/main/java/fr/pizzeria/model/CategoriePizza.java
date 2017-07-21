package fr.pizzeria.model;

public enum CategoriePizza {
	
	DEFAULT("Standard"), VIANDE("Viande"), POISSON("Poisson"), VEGAN("Vegan");
	
	private String categorie;
	
	private CategoriePizza(String categorie) {
		this.categorie = categorie;
	}
	
	/** Category's description
	 * @return the category description
	 */
	public String getDescription(){
		return this.categorie;
	}
	
}
