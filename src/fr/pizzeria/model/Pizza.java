package fr.pizzeria.model;

public class Pizza implements Comparable<Object>{
	
	private int id;
	private String code;
	private String nom;
	private Double prix;

	/**Pizza's constructor
	 * @param id of tha pizza
	 * @param code of the pizza
	 * @param nom is the name of the pizza
	 * @param prix is the price of the pizza
	 */
	public Pizza(int id, String code, String nom, Double prix) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.prix = prix;
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
}
