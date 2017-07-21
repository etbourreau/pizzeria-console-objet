package fr.pizzeria.ihm.optionmenu;

import java.awt.Font;
import java.sql.SQLException;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.util.CbxItem;
import fr.pizzeria.ihm.util.DefaultPanel;
import fr.pizzeria.ihm.util.JFrameTools;
import fr.pizzeria.model.CategoriePizza;

public class ListerPizzaParCategorieOptionMenu extends OptionMenu {

	JComboBox<CbxItem> cbxCategory;
	JTable tablePizzas;

	public ListerPizzaParCategorieOptionMenu(IPizzaDao dao, Menu m) {
		super(dao, m);
		this.libelle = "Lister les pizzas par catégorie";
	}

	@Override
	public boolean execute() {

		JPanel panel = new DefaultPanel();

		Font titleFont = new Font("Sylfaen", Font.BOLD, 18);
		Font textFont = new Font("Sylfaen", Font.PLAIN, 14);

		JLabel lblTitle = new JLabel("Liste des Pizzas par Catégorie");
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 440, 27);
		panel.add(lblTitle);

		JLabel lblPizza = new JLabel("Catégorie :");
		lblPizza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPizza.setFont(textFont);
		lblPizza.setBounds(10, 42, 151, 20);
		panel.add(lblPizza);

		cbxCategory = new JComboBox<>();
		cbxCategory.setFont(textFont);
		cbxCategory.setBounds(171, 41, 175, 23);
		panel.add(cbxCategory);
		fillCategories(dao, 0);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 73, 460, 187);
		panel.setLayout(null);
		panel.add(scrollPane);

		tablePizzas = new JTable();
		tablePizzas.setBounds(0, 0, 460, 260);
		tablePizzas.setEnabled(false);
		fillTablePizzas();
		tablePizzas.getColumnModel().getColumn(0).setPreferredWidth(0);
		tablePizzas.getColumnModel().getColumn(1).setPreferredWidth(10);
		tablePizzas.getColumnModel().getColumn(3).setPreferredWidth(20);
		tablePizzas.getColumnModel().getColumn(4).setPreferredWidth(10);
		scrollPane.setViewportView(tablePizzas);

		cbxCategory.addActionListener(e -> fillTablePizzas());

		menu.setContenu(panel);

		return true;
	}

	private void fillTablePizzas() {
		int categoryId = Integer.parseInt(((CbxItem) cbxCategory.getSelectedItem()).getValue());
		try {
			tablePizzas.setModel(
					JFrameTools.createPizzaModel(dao.findPizzasByCategory(CategoriePizza.values()[categoryId])));
		} catch (SQLException e) {
			menu.setStatus("Ne peut pas charger les pizzas", 2);
			Menu.LOG.debug("Can't load pizzas", e);
		}
	}

	/**
	 * Fills the selection combobox with pizzas
	 * 
	 * @param list
	 *            of pizzas
	 * @param selectedIndex
	 *            by default
	 */
	private void fillCategories(IPizzaDao dao, int selectedIndex) {
		cbxCategory.removeAllItems();
		Stream.of(CategoriePizza.values()).filter(cp -> {
			try {
				return !dao.findPizzasByCategory(cp).isEmpty();
			} catch (SQLException e) {
				menu.setStatus("Ne peut pas remplir la catégorie " + cp.getDescription(), 2);
				Menu.LOG.debug("Can't load category " + cp.getDescription(), e);
				return false;
			}
		})
				.forEach(
						cp -> this.cbxCategory.addItem(new CbxItem(String.valueOf(cp.ordinal()), cp.getDescription())));
		cbxCategory.setSelectedIndex(selectedIndex);
	}

}
