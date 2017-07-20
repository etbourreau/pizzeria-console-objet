package fr.pizzeria.ihm.optionmenu;

import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.util.DefaultPanel;
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
		tablePizzas.setModel(createModel(dao.findAllPizzas()));
		tablePizzas.getColumnModel().getColumn(0).setPreferredWidth(0);
		tablePizzas.getColumnModel().getColumn(1).setPreferredWidth(10);
		tablePizzas.getColumnModel().getColumn(3).setPreferredWidth(20);
		tablePizzas.getColumnModel().getColumn(4).setPreferredWidth(10);
		scrollPane.setViewportView(tablePizzas);
		
		menu.setContenu(panel);
		
		return true;
	}
	
	/**Creates the JTable model to fill the pizzas JTable
	 * @param ArrayList of pizzas
	 * @return the generated tableModel
	 */
	private DefaultTableModel createModel(List<Pizza> pizzas) {
		String[] columnsTable = { "ID", "Code", "Nom", "Catégorie", "Prix" };
		String[][] dataTable = new String[pizzas.size()][columnsTable.length];
		int index = 0;
		for(Pizza p : pizzas){
			dataTable[index][0] = String.valueOf(p.getId());
			dataTable[index][1] = p.getCode();
			dataTable[index][2] = p.getNom();
			dataTable[index][3] = p.getCategorie().getDescription();
			dataTable[index][4] = String.valueOf(p.getPrix()) + "€";
			index++;
		}
		return new DefaultTableModel(dataTable, columnsTable);
	}

}
