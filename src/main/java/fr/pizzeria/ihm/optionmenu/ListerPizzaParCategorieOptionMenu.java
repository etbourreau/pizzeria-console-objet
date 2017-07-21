package fr.pizzeria.ihm.optionmenu;

import java.awt.Font;
import java.util.Comparator;
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

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.pizza.CategoryNotFoundException;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.util.CbxItem;
import fr.pizzeria.ihm.util.DefaultPanel;
import fr.pizzeria.ihm.util.JFrameTools;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class ListerPizzaParCategorieOptionMenu extends OptionMenu {

	private static final Logger LOG = LoggerFactory.getLogger(ListerPizzaParCategorieOptionMenu.class);

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

		this.dao.sort(Comparator.comparing(Pizza::getId));

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
		try {
			int categoryId = Integer.parseInt(((CbxItem) cbxCategory.getSelectedItem()).getValue());
			tablePizzas.setModel(JFrameTools
					.createPizzaModel(dao.findPizzasByCategory(CategoriePizza.findCategoryById(categoryId))));
		} catch (CategoryNotFoundException exc) {
			String categoryName = ((CbxItem) cbxCategory.getSelectedItem()).getLabel();
			menu.setStatus("La catégorie \"" + categoryName + "\" est invalide", 2);
			LOG.info("La catégorie \"" + categoryName + "\" est invalide");
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
		Stream.of(CategoriePizza.values()).filter(cp -> !dao.findPizzasByCategory(cp).isEmpty())
				.forEach(cp -> this.cbxCategory.addItem(new CbxItem(String.valueOf(cp.getId()), cp.getDescription())));
		cbxCategory.setSelectedIndex(selectedIndex);
	}

}
