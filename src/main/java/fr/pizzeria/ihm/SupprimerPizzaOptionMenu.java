package main.java.fr.pizzeria.ihm;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.fr.pizzeria.dao.PizzaDaoMemoire;
import main.java.fr.pizzeria.exception.DeletePizzaException;
import main.java.fr.pizzeria.exception.InvalidPizzaException;
import main.java.fr.pizzeria.model.CbxItem;
import main.java.fr.pizzeria.model.Pizza;

public class SupprimerPizzaOptionMenu extends OptionMenu{

	private JComboBox<CbxItem> cbxPizza;
	
	public SupprimerPizzaOptionMenu(PizzaDaoMemoire dao, Menu m){
		super(dao, m);
		this.libelle = "Supprimer une Pizza";
	}

	@Override
	public boolean execute() {
		
		JPanel panel = new DefaultPanel();

		JLabel lblSupprimerUnePizza = new JLabel("Supprimer une Pizza");
		lblSupprimerUnePizza.setFont(new Font("Sylfaen", Font.BOLD, 18));
		lblSupprimerUnePizza.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupprimerUnePizza.setBounds(10, 11, 440, 27);
		panel.add(lblSupprimerUnePizza);
		
		JLabel lblPizza = new JLabel("Pizza :");
		lblPizza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPizza.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblPizza.setBounds(10, 102, 151, 20);
		panel.add(lblPizza);
		
		cbxPizza = new JComboBox<CbxItem>();
		cbxPizza.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		cbxPizza.setBounds(171, 101, 175, 23);
		panel.add(cbxPizza);
		fillPizzas(dao, 0);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int id = Integer.parseInt(((CbxItem)cbxPizza.getSelectedItem()).getValue());
				Pizza p;
				try {
					p = dao.getPizzaById(id);
					if(JOptionPane.showConfirmDialog((Component) null, "Voulez-vous vraiment supprimer la pizza "+p.getNom()+" ?", "alert", JOptionPane.OK_CANCEL_OPTION) == 0){
						dao.deletePizza(p);
						menu.setStatus("Pizza "+p.getNom()+" supprimée !", 0);
						fillPizzas(dao, 0);
					}
				} catch (DeletePizzaException | InvalidPizzaException exc) {
					menu.setStatus("La pizza n'a pas pu être supprimée !", 2);
					System.out.println("La pizza n'a pas pu être supprimée !");
					System.out.println(exc.getMessage());
				}
			}
		});
		btnSupprimer.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		btnSupprimer.setBounds(244, 166, 102, 23);
		panel.add(btnSupprimer);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.hideContenu();
			}
		});
		btnAnnuler.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		btnAnnuler.setBounds(137, 166, 89, 23);
		panel.add(btnAnnuler);
		
		menu.setContenu(panel);
		
		return true;
	}
	
	/**Fills the selection combobox with pizzas
	 * @param list of pizzas
	 * @param selectedIndex by default
	 */
	private void fillPizzas(PizzaDaoMemoire dao, int selectedIndex){
		List<Pizza> pizzas = dao.findAllPizzas();
		cbxPizza.removeAllItems();
		for(Pizza p : pizzas){
			this.cbxPizza.addItem(new CbxItem(String.valueOf(p.getId()), p.getId()+" "+p.getNom()));
		}
		cbxPizza.setSelectedIndex(selectedIndex);
	}

}
