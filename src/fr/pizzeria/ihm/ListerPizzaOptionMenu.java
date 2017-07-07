package fr.pizzeria.ihm;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import fr.pizzeria.dao.PizzaDaoMemoire;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenu extends OptionMenu{
	
	
	public ListerPizzaOptionMenu(PizzaDaoMemoire dao, Menu m){
		super(dao, m);
		this.libelle = "Liste des Pizzas";
	}

	@Override
	public boolean execute() {
		
		JPanel panel = new DefaultPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 460, 260);
		panel.setLayout(null);
		panel.add(scrollPane);
		
		JTable table = new JTable();
		table.setEnabled(false);
		table.setBounds(0, 0, 460, 260);
		table.setModel(createModel(dao.findAllPizzas()));
		//table.getColumnModel().getColumn(0).setPreferredWidth(50);
		scrollPane.setViewportView(table);
		
		menu.setContenu(panel);
		
		return true;
	}
	
	/**Creates the JTable model to fill the pizzas JTable
	 * @param ArrayList of pizzas
	 * @return the generated tableModel
	 */
	private DefaultTableModel createModel(List<Pizza> pizzas) {
		String[] columnsTable = {"ID", "Code", "Nom", "Prix"};
		String[][] dataTable = new String[pizzas.size()][columnsTable.length];
		int index = 0;
		for(Pizza p : pizzas){
			dataTable[index][0] = String.valueOf(p.getId());
			dataTable[index][1] = p.getCode();
			dataTable[index][2] =p.getNom();
			dataTable[index][3] = String.valueOf(p.getPrix())+"€";
			index++;
		}
		return new DefaultTableModel(dataTable, columnsTable);
	}

}
