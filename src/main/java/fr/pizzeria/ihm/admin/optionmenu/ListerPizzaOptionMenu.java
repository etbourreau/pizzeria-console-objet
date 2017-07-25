package fr.pizzeria.ihm.admin.optionmenu;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.admin.IPizzaDao;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.ihm.OptionMenu;
import fr.pizzeria.ihm.admin.menu.MenuAdmin;
import fr.pizzeria.ihm.admin.util.DefaultPanel;
import fr.pizzeria.ihm.admin.util.JFrameTools;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenu extends OptionMenu{
	
	private static final Logger LOG = LoggerFactory.getLogger(ListerPizzaOptionMenu.class);

	Comparator<Pizza> sorter;
	
	public ListerPizzaOptionMenu(IPizzaDao dao, MenuAdmin m, Comparator<Pizza> sorter, String name) {
		super(dao, m);
		LOG.info("Creating lister pizzas frame...");
		this.libelle = name;
		this.sorter = sorter;
		LOG.info("...lister pizzas frame");
	}

	@Override
	public boolean execute() {
		LOG.info("Launching lister pizzas frame...");
		
		JPanel panel = new DefaultPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 460, 260);
		panel.setLayout(null);
		panel.add(scrollPane);
		
		List<Pizza> pizzas;
		try {
			pizzas = dao.findAllPizzas();
			Collections.sort(pizzas, sorter);

			JTable tablePizzas = new JTable();
			tablePizzas.setEnabled(false);
			tablePizzas.setBounds(0, 0, 460, 260);
			tablePizzas.setModel(JFrameTools.createPizzaModel(pizzas));
			tablePizzas.getColumnModel().getColumn(0).setPreferredWidth(10);
			tablePizzas.getColumnModel().getColumn(2).setPreferredWidth(20);
			tablePizzas.getColumnModel().getColumn(3).setPreferredWidth(10);
			scrollPane.setViewportView(tablePizzas);
		} catch (InvalidPizzaException e) {
			menu.setStatus("Les pizzas ne peuvent pas afficher", 2);
			MenuAdmin.LOG.info("Can't show pizzas", e);
		}
		
		menu.setContenu(panel);

		LOG.info("...lister pizzas frame launched");
		return true;
	}
	


}
