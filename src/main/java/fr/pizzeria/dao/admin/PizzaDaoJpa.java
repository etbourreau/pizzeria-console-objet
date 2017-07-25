package fr.pizzeria.dao.admin;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class PizzaDaoJpa implements IPizzaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(PizzaDaoJpa.class);
	
	private EntityManagerFactory emf;
	
	public PizzaDaoJpa() {
		emf = Persistence.createEntityManagerFactory("pizzeria-jpa-unit");
	}
	
	@Override
	public List<Pizza> findAllPizzas() {
		LOG.info("Finding all pizzas...");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Pizza> pizzas = em.createNamedQuery("findAllPizzas", Pizza.class).getResultList();
		em.getTransaction().commit();
		em.close();
		LOG.info("...pizzas found");
		return pizzas;
	}
	
	@Override
	public List<Pizza> findPizzasByCategory(CategoriePizza cp) {
		LOG.info("Finding all pizzas for category " + cp.getDescription());
		return findAllPizzas().stream().filter(p -> p.getCategorie().equals(cp))
				.collect(Collectors.toList());
	}
	
	@Override
	public void saveNewPizza(Pizza pizza) {
		LOG.info("Saving new pizza " + pizza.getCode() + " " + pizza.getNom() + " " + pizza.getPrix()
				+ " " + pizza.getCategorie().getDescription() + "...");
		
		executeQuery(em -> em.persist(pizza));
		LOG.info("...pizza saved");
	}
	
	@Override
	public void updatePizza(Pizza pizza) {
		LOG.info("Updating pizza " + pizza.getCode() + " " + pizza.getNom() + " " + pizza.getPrix()
				+ " " + pizza.getCategorie().getDescription() + "...");
		
		executeQuery(em -> {
			Pizza p = em.find(Pizza.class, pizza.getId());
			Optional.ofNullable(p).orElseThrow(UpdatePizzaException::new);
			em.merge(pizza);
		});
		LOG.info("...pizza updated");
	}
	
	@Override
	public void deletePizza(Pizza pizza) {
		LOG.info("Deleting pizza " + pizza.getCode() + " " + pizza.getNom() + " " + pizza.getPrix()
				+ " " + pizza.getCategorie().getDescription() + "...");
		
		executeQuery(em -> {
			Pizza p = em.find(Pizza.class, pizza.getId());
			Optional.ofNullable(p).orElseThrow(DeletePizzaException::new);
			em.remove(p);
		});
		
		LOG.info("...pizza deleted");
	}
	
	private void executeQuery(Consumer<EntityManager> bc) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		bc.accept(em);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public Pizza getPizzaById(int id) {
		LOG.info("Getting pizza by id {}...", id);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Pizza pizza = em.createNamedQuery("findPizzaById", Pizza.class).setParameter("id", id)
				.getSingleResult();
		em.getTransaction().commit();
		em.close();
		LOG.info("...pizza found");
		return pizza;
	}
	
	@Override
	public Pizza getPizzaByCode(String code) {
		LOG.info("Getting pizza by code {}...", code);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Pizza pizza = em.createNamedQuery("findPizzaByCode", Pizza.class).setParameter("code", code)
				.getSingleResult();
		em.getTransaction().commit();
		em.close();
		LOG.info("...pizza found");
		return pizza;
	}
	
	public void closeDao() {
		emf.close();
		LOG.info("Dao closed");
	}
	
}
