package fr.pizzeria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.exception.pizza.CategoryNotFoundException;
import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.exception.pizza.SavePizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class PizzaDaoDb implements IPizzaDao {

	private static final Logger LOG = LoggerFactory.getLogger(PizzaDaoDb.class);

	private String dbDriver;
	private String dbUrl;
	private String dbUser;
	private String dbPwd;
	private Connection dbConnection;

	private List<Pizza> allPizzas = new ArrayList<>();

	public PizzaDaoDb(String dbDriver, String dbUrl, String dbUser, String dbPwd) throws SQLException {
		this.dbDriver = dbDriver;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
		getConnection();
		this.init();
	}

	public PizzaDaoDb(Connection c) throws SQLException {
		this.dbConnection = c;
		this.init();
	}

	private Connection getConnection() throws SQLException {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			LOG.debug(e.getMessage());
		}
		this.dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		this.dbConnection.setAutoCommit(false);
		return dbConnection;
	}

	public void closeConnection() throws SQLException {
		dbConnection.close();
	}

	private void commit() throws SQLException {
		try (PreparedStatement commit = dbConnection.prepareStatement("COMMIT;")) {
			commit.execute();
		}
	}

	private void rollback() throws SQLException {
		try (PreparedStatement rollback = dbConnection.prepareStatement("ROLLBACK;")) {
			rollback.execute();
		}
	}

	@Override
	public void init() throws SQLException {
		this.allPizzas = new ArrayList<>();
		String query = "SELECT * FROM PIZZA";
		try (PreparedStatement ps = this.dbConnection.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				this.allPizzas.add(new Pizza(rs.getInt("id"), rs.getString("code"), rs.getString("nom"),
						rs.getDouble("prix"), CategoriePizza.findCategoryById(rs.getInt("idCategorie"))));
			}
		} catch (CategoryNotFoundException e) {
			LOG.debug("Error during pizzas importation! Invalid category : " + e.getMessage());
		}
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return new ArrayList<>(this.allPizzas);
	}

	@Override
	public List<Pizza> findPizzasByCategory(CategoriePizza cp) {
		return this.allPizzas.stream().filter(p -> p.getCategorie().equals(cp)).collect(Collectors.toList());
	}

	@Override
	public void sort(Comparator<Pizza> sorter) {
		Collections.sort(allPizzas, sorter);

	}

	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException {
		try (PreparedStatement ps = dbConnection
				.prepareStatement("INSERT INTO PIZZA(code, nom, prix, idCategorie) VALUES (?,?,?,?);")) {
			ps.setString(1, pizza.getCode());
			ps.setString(2, pizza.getNom());
			ps.setDouble(3, pizza.getPrix());
			ps.setInt(4, pizza.getCategorie().getId());
			int lineAffected = ps.executeUpdate();
			if (lineAffected < 1) {
				throw new SavePizzaException("Can't save new pizza : no line affected");
			} else if (lineAffected > 1) {
				rollback();
				throw new SavePizzaException("Can't save pizza : more than one line affected");
			} else {
				commit();
			}
			this.init();
		} catch (SQLException e) {
			throw new SavePizzaException("Can't save new pizza : " + e.getMessage());
		}

	}

	@Override
	public void updatePizza(Pizza pizza) throws UpdatePizzaException{
		try (PreparedStatement ps = dbConnection
				.prepareStatement("UPDATE PIZZA SET code = ?, nom = ?, prix = ?, idCategorie = ? WHERE id = ?")) {
			ps.setString(1, pizza.getCode());
			ps.setString(2, pizza.getNom());
			ps.setDouble(3, pizza.getPrix());
			ps.setInt(4, pizza.getCategorie().getId());
			ps.setInt(5, pizza.getId());
			int lineAffected = ps.executeUpdate();
			if (lineAffected < 1) {
				throw new UpdatePizzaException("Can't update pizza : no line affected");
			} else if (lineAffected > 1) {
				rollback();
				throw new UpdatePizzaException("Can't update pizza : more than one line affected");
			}else {
				commit();
			}
			this.init();
		} catch (SQLException e) {
			throw new UpdatePizzaException("Can't update pizza : " + e.getMessage());
		}
	}

	@Override
	public void deletePizza(Pizza pizza) throws DeletePizzaException {
		try (PreparedStatement ps = dbConnection.prepareStatement("DELETE FROM PIZZA WHERE id = ?")) {
			ps.setInt(1, pizza.getId());
			int lineAffected = ps.executeUpdate();
			if (lineAffected < 1) {
				throw new DeletePizzaException("Can't delete pizza : no line affected");
			} else if (lineAffected > 1) {
				rollback();
				throw new DeletePizzaException("Can't delete pizza : more than one line affected");
			} else {
				commit();
			}
			this.init();
		} catch (SQLException e) {
			throw new DeletePizzaException("Can't delete pizza : " + e.getMessage());
		}
	}

	@Override
	public int getNextAvailableId() {
		return this.allPizzas.stream().mapToInt(Pizza::getId).max().getAsInt() + 1;
	}

	@Override
	public Pizza getPizzaById(int id) throws InvalidPizzaException {
		Optional<Pizza> found = this.allPizzas.stream().filter(p -> p.getId() == id).findAny();
		if (found.isPresent()) {
			return found.get();
		} else {
			throw new InvalidPizzaException();
		}
	}

	@Override
	public Pizza getPizzaByCode(String code) throws InvalidPizzaException {
		Optional<Pizza> found = this.allPizzas.stream().filter(p -> p.getCode().equals(code)).findAny();
		if (found.isPresent()) {
			return found.get();
		} else {
			throw new InvalidPizzaException();
		}
	}

}
