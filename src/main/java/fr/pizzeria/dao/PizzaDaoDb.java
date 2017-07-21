package fr.pizzeria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public PizzaDaoDb(String dbDriver, String dbUrl, String dbUser, String dbPwd)
			throws SQLException, ClassNotFoundException {
		this.dbDriver = dbDriver;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
		createConnection();
	}

	private Connection createConnection() throws SQLException, ClassNotFoundException {
		Class.forName(dbDriver);
		try (Connection dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPwd)) {
			dbConnection.setAutoCommit(false);
			return dbConnection;
		}
	}

	@Override
	public List<Pizza> findAllPizzas() throws SQLException {
		List<Pizza> pizzas = new ArrayList<>();
		String query = "SELECT * FROM PIZZA";
		try (PreparedStatement ps = createConnection().prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pizzas.add(new Pizza(rs.getInt("id"), rs.getString("code"), rs.getString("nom"),
						rs.getDouble("prix"), CategoriePizza.values()[rs.getInt("idCategorie")]));
			}
		} catch (ClassNotFoundException e) {
			LOG.debug("Can't open connection !", e);
		}
		return pizzas;
	}

	@Override
	public List<Pizza> findPizzasByCategory(CategoriePizza cp) throws SQLException {
		return findAllPizzas().stream().filter(p -> p.getCategorie().equals(cp)).collect(Collectors.toList());
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException {
		try (Connection c = createConnection();
				PreparedStatement ps = c
						.prepareStatement("INSERT INTO PIZZA(code, nom, prix, idCategorie) VALUES (?,?,?,?);")) {
			ps.setString(1, pizza.getCode());
			ps.setString(2, pizza.getNom());
			ps.setDouble(3, pizza.getPrix());
			ps.setInt(4, pizza.getCategorie().ordinal());
			int lineAffected = ps.executeUpdate();
			if (lineAffected < 1) {
				throw new SavePizzaException("Can't save new pizza : no line affected");
			} else if (lineAffected > 1) {
				c.rollback();
				throw new SavePizzaException("Can't save pizza : more than one line affected");
			} else {
				c.commit();
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new SavePizzaException("Can't save new pizza", e);
		}

	}

	@Override
	public void updatePizza(Pizza pizza) throws UpdatePizzaException {
		try (Connection c = createConnection();
				PreparedStatement ps = c.prepareStatement(
						"UPDATE PIZZA SET code = ?, nom = ?, prix = ?, idCategorie = ? WHERE id = ?")) {
			ps.setString(1, pizza.getCode());
			ps.setString(2, pizza.getNom());
			ps.setDouble(3, pizza.getPrix());
			ps.setInt(4, pizza.getCategorie().ordinal());

			ps.setInt(5, pizza.getId());
			int lineAffected = ps.executeUpdate();
			if (lineAffected < 1) {
				throw new UpdatePizzaException("Can't update pizza : no line affected");
			} else if (lineAffected > 1) {
				c.rollback();
				throw new UpdatePizzaException("Can't update pizza : more than one line affected");
			}else {
				c.commit();
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new UpdatePizzaException("Can't update pizza", e);
		}
	}

	@Override
	public void deletePizza(Pizza pizza) throws DeletePizzaException {
		try (Connection c = createConnection();
				PreparedStatement ps = c.prepareStatement("DELETE FROM PIZZA WHERE id = ?")) {
			ps.setInt(1, pizza.getId());
			int lineAffected = ps.executeUpdate();
			if (lineAffected < 1) {
				throw new DeletePizzaException("Can't delete pizza : no line affected");
			} else if (lineAffected > 1) {
				c.rollback();
				throw new DeletePizzaException("Can't delete pizza : more than one line affected");
			} else {
				c.commit();
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new DeletePizzaException("Can't delete pizza", e);
		}
	}


	@Override
	public Optional<Pizza> getPizzaById(int id) throws InvalidPizzaException, SQLException {
		return this.findAllPizzas().stream().filter(p -> p.getId() == id).findAny();
	}

	@Override
	public Optional<Pizza> getPizzaByCode(String code) throws InvalidPizzaException, SQLException {
		return this.findAllPizzas().stream().filter(p -> p.getCode().equals(code)).findAny();
	}

}
