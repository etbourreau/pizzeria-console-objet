package fr.pizzeria.ihm.admin.optionmenu;

import java.awt.Font;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.DaoGeneral;
import fr.pizzeria.dao.admin.IPizzaDao;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.admin.menu.MenuAdmin;
import fr.pizzeria.ihm.admin.util.CbxItem;
import fr.pizzeria.ihm.admin.util.DefaultPanel;
import fr.pizzeria.ihm.admin.util.JFrameTools;
import fr.pizzeria.model.CategoriePizza;

public class ListerPizzaParCategorieOptionMenu extends OptionMenu {

	private static final Logger LOG = LoggerFactory.getLogger(ListerPizzaParCategorieOptionMenu.class);

	JComboBox<CbxItem> cbxCategory;
	JTable tablePizzas;

	public ListerPizzaParCategorieOptionMenu(IPizzaDao dao, MenuAdmin m) {
		super(dao, m);
		LOG.info("Creating lister pizzas par categorie frame...");
		this.libelle = "Lister les pizzas par catégorie";
		LOG.info("...lister pizzas par categorie frame created");
	}

	@Override
	public boolean execute() {
		LOG.info("Launching lister pizza par categorie frame...");

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
		tablePizzas.getColumnModel().getColumn(0).setPreferredWidth(10);
		tablePizzas.getColumnModel().getColumn(2).setPreferredWidth(20);
		tablePizzas.getColumnModel().getColumn(3).setPreferredWidth(10);
		scrollPane.setViewportView(tablePizzas);

		cbxCategory.addActionListener(e -> fillTablePizzas());

		menu.setContenu(panel);

		LOG.info("...lister pizzas par categorie launched");
		return true;
	}

	private void fillTablePizzas() {
		LOG.info("Filling table with pizzas");
		int categoryId = Integer.parseInt(((CbxItem) cbxCategory.getSelectedItem()).getValue());
		try {
			tablePizzas.setModel(
					JFrameTools.createPizzaModel(dao.findPizzasByCategory(CategoriePizza.values()[categoryId])));
		} catch (InvalidPizzaException e) {
			menu.setStatus("Ne peut pas charger les pizzas", 2);
			MenuAdmin.LOG.info("Can't load pizzas", e);
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
	private void fillCategories(DaoGeneral dao, int selectedIndex) {
		LOG.info("Filling categories combobox with index {}", selectedIndex);
		cbxCategory.removeAllItems();
		Stream.of(CategoriePizza.values()).filter(cp -> {
			try {
				return !dao.findPizzasByCategory(cp).isEmpty();
			} catch (InvalidPizzaException e) {
				menu.setStatus("Ne peut pas remplir la catégorie " + cp.getDescription(), 2);
				MenuAdmin.LOG.info("Can't load category " + cp.getDescription(), e);
				return false;
			}
		})
				.forEach(
						cp -> this.cbxCategory.addItem(new CbxItem(String.valueOf(cp.ordinal()), cp.getDescription())));
		cbxCategory.setSelectedIndex(selectedIndex);
	}

}
