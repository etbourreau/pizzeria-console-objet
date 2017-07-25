package fr.pizzeria.dao.client;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.exception.client.CommanderPizzaException;
import fr.pizzeria.exception.client.InvalidConnectionException;
import fr.pizzeria.exception.client.RegisterClientException;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Pizza;

public class ClientDaoJpa implements IClientDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientDaoJpa.class);
	
	private EntityManagerFactory emf;
	
	public ClientDaoJpa(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public List<Pizza> findAllPizzas() {
		LOG.debug("Finding all pizzas...");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Pizza> pizzas = em.createNamedQuery("findAllPizzas", Pizza.class).getResultList();
		em.getTransaction().commit();
		em.close();
		LOG.debug("...pizzas found");
		return pizzas;
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
		LOG.debug("Getting pizza by id {}...", id);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Pizza pizza = em.createNamedQuery("findPizzaById", Pizza.class).setParameter("id", id)
				.getSingleResult();
		em.getTransaction().commit();
		em.close();
		LOG.info("...pizza found");
		return pizza;
	}
	
	public void closeDao() {
		emf.close();
		LOG.debug("Dao closed");
	}
	
	private List<Client> findAllClients() {
		LOG.debug("Finding all clients...");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Client> clients = em.createNamedQuery("findAllClients", Client.class).getResultList();
		em.getTransaction().commit();
		em.close();
		LOG.debug("...clients found");
		return clients;
	}
	
	@Override
	public Client getClientByEmailAndPwd(String email, String pwd) {
		try {
			LOG.debug("Connecting client {}...", email);
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Optional<Client> client = Optional
					.ofNullable(em.createNamedQuery("connectClient", Client.class)
							.setParameter("email", email).setParameter("pwd", pwd).getSingleResult());
			em.getTransaction().commit();
			em.close();
			if (client.isPresent()) {
				LOG.debug("...client connected");
				return client.get();
			} else {
				throw new InvalidConnectionException("Email et/ou mot de passe erroné(s)");
			}
		} catch (Exception e) {
			throw new InvalidConnectionException("Can't connect user", e);
		}
	}
	
	@Override
	public List<Commande> findCommandesByClient(Client client) {
		LOG.debug("Finding commandes for client " + client.getId() + "...");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Commande> cmds = em.createNamedQuery("findCommandesByClientId", Commande.class)
				.setParameter("idClient", client.getId()).getResultList();
		em.getTransaction().commit();
		em.close();
		LOG.debug("...commandes found");
		return cmds;
	}
	
	@Override
	public void commanderPizza(Commande commande) {
		try {
			executeQuery(em -> em.persist(commande));
		} catch (Exception e) {
			throw new CommanderPizzaException("Can't order pizza", e);
		}
	}
	
	@Override
	public void registerUser(Client client) {
		try {
			executeQuery(em -> em.persist(client));
		} catch (Exception e) {
			throw new RegisterClientException("Can't register client", e);
		}
	}
	
	public Integer getNextNumeroCommande() {
		LOG.debug("Getting next numero commande...");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Integer numeroMax = Optional
				.ofNullable(em.createNamedQuery("findMaxNumeroCommande", Integer.class).getSingleResult())
				.orElse(0);
		em.getTransaction().commit();
		em.close();
		LOG.debug("...numero commande found {}", numeroMax);
		return numeroMax + 1;
	}
	
}
