package fr.pizzeria.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllPizzas", query = "select p from Pizza p"),
		@NamedQuery(name = "findPizzaById", query = "select p from Pizza p where p.id=:id"),
		@NamedQuery(name = "findPizzaByCode", query = "select p from Pizza p where p.code=:code") })
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private String nom;
	private Double prix;
	@MapKeyEnumerated
	@Column(name = "idCategorie")
	private CategoriePizza categorie;

	public Pizza() {
	}

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
