package fr.pizzeria.ihm.util;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import fr.pizzeria.model.Pizza;

public class JFrameTools {
	
	private JFrameTools(){}

	/**
	 * Creates the JTable model to fill the pizzas JTable
	 * 
	 * @param ArrayList
	 *            of pizzas
	 * @return the generated tableModel
	 */
	public static DefaultTableModel createPizzaModel(List<Pizza> pizzas) {
		String[] columnsTable = { "Code", "Nom", "Catégorie", "Prix" };
		String[][] dataTable = new String[pizzas.size()][columnsTable.length];
		int index = 0;
		for (Pizza p : pizzas) {
			dataTable[index][0] = p.getCode();
			dataTable[index][1] = p.getNom();
			dataTable[index][2] = p.getCategorie().getDescription();
			dataTable[index][3] = String.valueOf(p.getPrix()) + "€";
			index++;
		}
		return new DefaultTableModel(dataTable, columnsTable);
	}

}
