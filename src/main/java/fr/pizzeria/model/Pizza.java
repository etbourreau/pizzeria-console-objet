package fr.pizzeria.model;

import java.lang.reflect.Field;
import java.util.Objects;

import fr.pizzeria.bin.PizzeriaAdminConsoleApp;

public class Pizza implements Comparable<Object>{
	
	private int id;
	@ToString
	private String code;
	@ToString(upperCase = false)
	private String nom;
	@ToString
	private Double prix;
	@ToString
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
		this.code = code.toUpperCase();
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
		if (p instanceof Pizza) {
			return (id - ((Pizza) p).getId());
		}else{
			return 0;
		}
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

	/** return format string with annotated fields
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(Field f : Pizza.class.getDeclaredFields()){
			if(f.isAnnotationPresent(ToString.class)){
				if(first) first = false; else sb.append(" ");
				try {
					if(f.getDeclaredAnnotation(ToString.class).upperCase()){
						sb.append(String.valueOf(f.get(this)).toUpperCase());
					}else{
						sb.append(String.valueOf(f.get(this)));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					PizzeriaAdminConsoleApp.LOG.debug("Error accessing toString function : {}", e.getMessage());
				}
			}
		}
		return sb.toString();
	}
}
