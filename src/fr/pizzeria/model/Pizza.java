package fr.pizzeria.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pizza implements Comparable<Object> {
	public static List<Pizza> allPizzas;
	
	public static void initPizzas(){
		Pizza.allPizzas = new ArrayList<Pizza>();
		new Pizza(0, "PEP", "Pépéroni", 12.50);
		new Pizza(1, "MAR", "Margherita", 14.00);
		new Pizza(2, "REI", "La Reine", 11.50);
		new Pizza(3, "FRO", "La 4 fromages", 12.00);
		new Pizza(4, "CAN", "La cannibale", 12.50);
		new Pizza(5, "SAV", "La savoyarde", 13.00);
		new Pizza(6, "ORI", "L'orientale", 13.50);
		new Pizza(7, "IND", "L'indienne", 14.00);
	}
	
	public static Pizza getPizzaById(int id){
		for(Pizza p : Pizza.allPizzas){
			if(p.getId() == id){
				return p;
			}
		}
		return null;
	}
	
	public static Pizza getPizzaByCode(String code){
		for (Pizza p : Pizza.allPizzas) {
			if (p.getCode().equalsIgnoreCase(code)){
				return p;
			}
		}
		return null;
	}
	
	public static int getNextAvailableId(){
		int maxId = Pizza.allPizzas.get(0).getId();
		for(Pizza p : Pizza.allPizzas){
			if(p.getId() > maxId){
				maxId = p.getId();
			}
		}
		return maxId+1;
	}
	
	@SuppressWarnings("unchecked")
	public static void sortPizzasById(){
		Collections.sort((List)Pizza.allPizzas);
	}
	
	private int id;
	private String code;
	private String nom;
	private Double prix;

	public Pizza(int id, String code, String nom, Double prix) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		Pizza.allPizzas.add(this);
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
	
	@Override
	public int compareTo(Object p) {
		if(p instanceof Pizza){
			return (id - ((Pizza) p).getId());
		}else{
			return 0;
		}
	}
}
