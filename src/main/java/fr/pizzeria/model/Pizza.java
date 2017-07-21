package fr.pizzeria.model;

import java.util.Objects;

public class Pizza {
	
	private Integer id;
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
	public Pizza(Integer id, String code, String nom, Double prix, CategoriePizza categorie) {
		super();
		this.id = id;
		this.code = code.toUpperCase();
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	@Override
	public boolean equals(Object p){
		if(p instanceof Pizza){
			return this.hashCode() == p.hashCode();
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.code, this.nom, this.prix, this.categorie);
	}
}
