package fr.pizzeria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllCommandes", query = "select c from Commande c"),
		@NamedQuery(name = "findMaxNumeroCommande", query = "select max(c.numeroCommande) from Commande c"),
		@NamedQuery(name = "findCommandesByClientId", query = "select distinct c from Commande c JOIN FETCH c.pizzas where c.client.id=:idClient")
})
public class Commande {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "numero_commande")
	private Integer numeroCommande;
	private String statut;
	@Column(name = "date_commande")
	private LocalDateTime dateCommande;
	@ManyToOne
	@JoinColumn(name = "idLivreur")
	private Livreur livreur;
	@ManyToOne
	@JoinColumn(name = "idClient")
	private Client client;
	@ManyToMany
	@JoinTable(name = "commande_pizza", joinColumns = @JoinColumn(name = "id_commande", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_pizza", referencedColumnName = "id"))
	private List<Pizza> pizzas = new ArrayList<>();
	
	public Commande() {
		super();
	}
	
	public Commande(Integer numeroCommande, String statut, LocalDateTime dateCommande,
			Livreur livreur, Client client) {
		super();
		this.numeroCommande = numeroCommande;
		this.statut = statut;
		this.dateCommande = dateCommande;
		this.livreur = livreur;
		this.client = client;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNumeroCommande() {
		return numeroCommande;
	}
	
	public void setNumeroCommande(Integer numeroCommande) {
		this.numeroCommande = numeroCommande;
	}
	
	public String getStatut() {
		return statut;
	}
	
	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	public LocalDateTime getDateCommande() {
		return dateCommande;
	}
	
	public void setDateCommande(LocalDateTime dateCommande) {
		this.dateCommande = dateCommande;
	}
	
	public Livreur getLivreur() {
		return livreur;
	}
	
	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void addPizza(Pizza p) {
		this.pizzas.add(p);
	}
	
	public List<Pizza> getPizzas() {
		return new ArrayList<>(this.pizzas);
	}
	
}
