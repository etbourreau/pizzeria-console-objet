package fr.pizzeria.ihm.optionmenu;

import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.util.DefaultPanel;
import fr.pizzeria.ihm.util.JFrameTools;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenu extends OptionMenu{
	
	Comparator<Pizza> sorter;
	
	public ListerPizzaOptionMenu(IPizzaDao dao, Menu m, Comparator<Pizza> sorter, String name) {
		super(dao, m);
		this.libelle = name;
		this.sorter = sorter;
	}

	@Override
	public boolean execute() {
		
		JPanel panel = new DefaultPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 460, 260);
		panel.setLayout(null);
		panel.add(scrollPane);
		
		this.dao.sort(this.sorter);

		JTable tablePizzas = new JTable();
		tablePizzas.setEnabled(false);
		tablePizzas.setBounds(0, 0, 460, 260);
		tablePizzas.setModel(JFrameTools.createPizzaModel(dao.findAllPizzas()));
		tablePizzas.getColumnModel().getColumn(0).setPreferredWidth(0);
		tablePizzas.getColumnModel().getColumn(1).setPreferredWidth(10);
		tablePizzas.getColumnModel().getColumn(3).setPreferredWidth(20);
		tablePizzas.getColumnModel().getColumn(4).setPreferredWidth(10);
		scrollPane.setViewportView(tablePizzas);
		
		menu.setContenu(panel);
		
		return true;
	}
	


}
