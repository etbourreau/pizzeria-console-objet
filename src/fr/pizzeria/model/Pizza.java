package fr.pizzeria.model;

public class Pizza implements Comparable<Object>{
	
	private int id;
	private String code;
	private String nom;
	private Double prix;
	CategoriePizza categorie;

	/**Pizza's constructor
	 * @param id of tha pizza
	 * @param code of the pizza
	 * @param nom is the name of the pizza
	 * @param prix is the price of the pizza
	 * @param categorie is the pizza's CategoriePizza
	 */
	public Pizza(int id, String code, String nom, Double prix, CategoriePizza categorie) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public CategoriePizza getCategorie() {
		return categorie;
	}

	public void setCategorie(CategoriePizza categorie) {
		this.categorie = categorie;
	}
	
	/**Override Comparable's method to sort pizzas in a List of Pizzas
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object p) {
		if(p instanceof Pizza){
			return (id - ((Pizza) p).getId());
		}else{
			return 0;
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Pizza ");
		sb.append(this.getNom());
		sb.append(" (code = ").append(this.getCode());
		sb.append(", id = ").append(this.getId());
		sb.append(", prix = ").append(this.getPrix());
		sb.append(", catégorie = ").append(this.getCategorie().getDescription()).append(")");
		return sb.toString();
	}
}
