package fr.pizzeria.ihm.optionmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.pizzeria.bin.PizzeriaAdminInterfaceApp;
import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.exception.pizza.UpdatePizzaException;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.util.CbxItem;
import fr.pizzeria.ihm.util.Decimal;
import fr.pizzeria.ihm.util.DefaultPanel;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class ModifierPizzaOptionMenu extends OptionMenu {

	private JComboBox<CbxItem> cbxPizza;
	private JTextField txtCode;
	private JTextField txtNom;
	private JTextField txtPrix;
	private JComboBox<CbxItem> cbxCategorie;
	private boolean lock = false;

	private String invalidConversionString = "Invalid conversion ({}) : {}";
	private String invalidPizzaString = "Invalid pizza : {}";

	public ModifierPizzaOptionMenu(IPizzaDao dao, Menu m) {
		super(dao, m);
		this.libelle = "Modifier une Pizza";
	}

	@Override
	public boolean execute() {

		JPanel panel = new DefaultPanel();

		Font titleFont = new Font("Sylfaen", Font.BOLD, 18);
		Font textFont = new Font("Sylfaen", Font.PLAIN, 14);

		JLabel lblModifierUnePizza = new JLabel("Modifier une Pizza");
		lblModifierUnePizza.setFont(titleFont);
		lblModifierUnePizza.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifierUnePizza.setBounds(10, 11, 440, 27);
		panel.add(lblModifierUnePizza);

		JLabel lblPizza = new JLabel("Pizza :");
		lblPizza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPizza.setFont(textFont);
		lblPizza.setBounds(10, 55, 151, 20);
		panel.add(lblPizza);

		cbxPizza = new JComboBox<>();
		cbxPizza.setFont(textFont);
		cbxPizza.setBounds(171, 54, 175, 23);
		panel.add(cbxPizza);
		fillPizzas(dao, 0);

		JLabel lblCode = new JLabel("Code (3 caract\u00E8res) :");
		lblCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCode.setFont(textFont);
		lblCode.setBounds(10, 89, 151, 20);
		panel.add(lblCode);

		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setFont(textFont);
		lblNom.setBounds(10, 123, 151, 20);
		panel.add(lblNom);

		JLabel lblPrix = new JLabel("Prix :");
		lblPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrix.setFont(textFont);
		lblPrix.setBounds(10, 154, 151, 20);
		panel.add(lblPrix);

		txtCode = new JTextField();
		txtCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (txtCode.getText().length() + 1 == 3) {
					txtCode.setBackground(new Color(1f, 1f, 1f));
				}
				if (txtCode.getText().length() > 2) {
					txtCode.setText(txtCode.getText().substring(0, 2));
				}
			}
		});
		txtCode.setFont(textFont);
		txtCode.setBounds(171, 88, 175, 23);
		panel.add(txtCode);
		txtCode.setColumns(10);

		txtNom = new JTextField();
		txtNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtNom.setBackground(new Color(1f, 1f, 1f));
			}
		});
		txtNom.setFont(textFont);
		txtNom.setColumns(10);
		txtNom.setBounds(171, 122, 175, 23);
		panel.add(txtNom);

		txtPrix = new JTextField();
		txtPrix.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtPrix.setBackground(new Color(1f, 1f, 1f));
			}
		});
		txtPrix.setFont(textFont);
		txtPrix.setColumns(10);
		txtPrix.setBounds(171, 153, 162, 23);
		panel.add(txtPrix);

		JLabel lblPrixDevise = new JLabel("€");
		lblPrixDevise.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixDevise.setFont(textFont);
		lblPrixDevise.setBounds(330, 154, 16, 20);
		panel.add(lblPrixDevise);

		cbxCategorie = new JComboBox<>();
		cbxCategorie.setFont(textFont);
		cbxCategorie.setBounds(171, 187, 175, 23);
		panel.add(cbxCategorie);
		try {
			fillCategories(CategoriePizza.values(),
					dao.getPizzaById(Integer.parseInt((((CbxItem) cbxPizza.getSelectedItem()).getValue())))
							.get().getCategorie().toString());
		} catch (NumberFormatException exc) {
			PizzeriaAdminInterfaceApp.LOG.debug(invalidConversionString,
					((CbxItem) cbxPizza.getSelectedItem()).getValue(), exc.getMessage());
		} catch (InvalidPizzaException | SQLException exc) {
			menu.setStatus("La pizza sélectionnée est invalide", 2);
			PizzeriaAdminInterfaceApp.LOG.debug(invalidPizzaString, exc.getMessage());
		}

		JLabel lblCatgorie = new JLabel("Catégorie :");
		lblCatgorie.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCatgorie.setFont(textFont);
		lblCatgorie.setBounds(10, 188, 151, 20);
		panel.add(lblCatgorie);

		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(e -> {
			try {
				int id = Integer.parseInt(((CbxItem) cbxPizza.getSelectedItem()).getValue());
				Pizza p = dao.getPizzaById(id).get();
				if (validFields() && (!txtCode.getText().equals(p.getCode()) || !txtNom.getText().equals(p.getNom())
						|| Decimal.priceRound(Double.parseDouble(txtPrix.getText())) != p.getPrix()
						|| !((CbxItem) cbxCategorie.getSelectedItem()).getLabel()
								.equalsIgnoreCase(p.getCategorie().getDescription()))) {
					dao.updatePizza(new Pizza(id, txtCode.getText(), txtNom.getText(),
							Decimal.priceRound(Double.parseDouble(txtPrix.getText())),
							CategoriePizza.valueOf(((CbxItem) cbxCategorie.getSelectedItem()).getValue())));
					menu.setStatus("La pizza " + txtNom.getText() + " a été modifié !", 0);
					PizzeriaAdminInterfaceApp.LOG.info("Pizza has been modified (" + txtNom.getText() + ")");
					lock = true;
					fillPizzas(dao, cbxPizza.getSelectedIndex());
					lock = false;
				}
			} catch (NumberFormatException | InvalidPizzaException exc) {
				PizzeriaAdminInterfaceApp.LOG.debug(invalidConversionString,
						((CbxItem) cbxPizza.getSelectedItem()).getValue(), exc.getMessage());
			} catch (UpdatePizzaException | SQLException exc) {
				String msg = "Can't modify pizza " + txtNom.getText();
				menu.setStatus(msg, 2);
				PizzeriaAdminInterfaceApp.LOG.debug(msg + " : {}", exc.getMessage());
			}
		});
		btnValider.setFont(textFont);
		btnValider.setBounds(259, 226, 89, 23);
		panel.add(btnValider);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(e -> menu.hideContenu());
		btnAnnuler.setFont(textFont);
		btnAnnuler.setBounds(146, 226, 89, 23);
		panel.add(btnAnnuler);

		cbxPizza.addActionListener(e -> {
			if (!lock) {
				initFields();
				try {
					fillCategories(CategoriePizza.values(),
							dao.getPizzaById(Integer.parseInt((((CbxItem) cbxPizza.getSelectedItem()).getValue())))
									.get().getCategorie().toString());
				} catch (NumberFormatException exc) {
					PizzeriaAdminInterfaceApp.LOG.debug(invalidConversionString, ((CbxItem) cbxPizza.getSelectedItem()).getValue(), exc.getMessage());
				} catch (InvalidPizzaException | SQLException exc) {
					menu.setStatus("La pizza sélectionnée est invalide", 2);
					PizzeriaAdminInterfaceApp.LOG.debug(invalidPizzaString, exc.getMessage());
				}
			}
		});

		initFields();

		menu.setContenu(panel);

		return true;
	}

	/**
	 * Fills fields with selected pizza informations
	 */
	private void initFields() {
		int id = Integer.parseInt(((CbxItem) cbxPizza.getSelectedItem()).getValue());
		Pizza p;
		try {
			p = dao.getPizzaById(id).get();
			txtCode.setText(p.getCode());
			txtCode.setBackground(new Color(1f, 1f, 1f));
			txtNom.setText(p.getNom());
			txtNom.setBackground(new Color(1f, 1f, 1f));
			txtPrix.setText(String.valueOf(p.getPrix()));
			txtPrix.setBackground(new Color(1f, 1f, 1f));
		} catch (InvalidPizzaException | SQLException exc) {
			menu.setStatus("Pizza Invalide", 2);
			PizzeriaAdminInterfaceApp.LOG.debug(invalidPizzaString, exc.getMessage());
		}
	}

	/**
	 * Checks if all fields are correct and colors in red wrong ones
	 * 
	 * @return true if fields are valid, false otherwise
	 */
	private boolean validFields() {
		boolean valid = true;
		if (txtCode.getText().length() != 3) {
			txtCode.setBackground(new Color(0.8f, 0.2f, 0.2f));
			valid = false;
		}
		if (txtNom.getText().length() == 0) {
			txtNom.setBackground(new Color(0.8f, 0.2f, 0.2f));
			valid = false;
		}
		if (txtPrix.getText().length() == 0) {
			txtPrix.setBackground(new Color(0.8f, 0.2f, 0.2f));
			valid = false;
		}
		try {
			Double.parseDouble(txtPrix.getText());
		} catch (NumberFormatException e) {
			txtPrix.setBackground(new Color(0.8f, 0.2f, 0.2f));
			return false;
		}
		return valid;
	}

	/**
	 * Fills the selection combobox with pizzas
	 * 
	 * @param list
	 *            of pizzas
	 * @param selectedIndex
	 *            by default
	 */
	private void fillPizzas(IPizzaDao dao, int selectedIndex) {
		List<Pizza> pizzas;
		try {
			pizzas = dao.findAllPizzas();
			cbxPizza.removeAllItems();
			for (Pizza p : pizzas) {
				this.cbxPizza.addItem(new CbxItem(String.valueOf(p.getId()), p.getId() + " " + p.getNom()));
			}
			cbxPizza.setSelectedIndex(selectedIndex);
		} catch (SQLException e) {
			menu.setStatus("La liste des pizza est invalide", 2);
			PizzeriaAdminInterfaceApp.LOG.debug("Can't fill pizzas", e);
		}
	}

	private void fillCategories(CategoriePizza[] categories, String selectedCategoryName) {
		cbxCategorie.removeAllItems();
		CbxItem currentItem;
		for (CategoriePizza cp : categories) {
			currentItem = new CbxItem(cp.toString(), cp.getDescription());
			this.cbxCategorie.addItem(currentItem);
			if (currentItem.getValue().equals(selectedCategoryName)) {
				cbxCategorie.setSelectedItem(currentItem);
			}
		}
	}

}
