package fr.pizzeria.ihm.optionmenu;

import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.pizza.DeletePizzaException;
import fr.pizzeria.exception.pizza.InvalidPizzaException;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.util.CbxItem;
import fr.pizzeria.ihm.util.DefaultPanel;
import fr.pizzeria.model.Pizza;

public class SupprimerPizzaOptionMenu extends OptionMenu {

	private static final Logger LOG = LoggerFactory.getLogger(SupprimerPizzaOptionMenu.class);

	private JComboBox<CbxItem> cbxPizza;

	public SupprimerPizzaOptionMenu(IPizzaDao dao, Menu m) {
		super(dao, m);
		LOG.info("Creating supprimer pizza frame...");
		this.libelle = "Supprimer une Pizza";
		LOG.info("...supprimer pizza frame created");
	}

	@Override
	public boolean execute() {
		LOG.info("Launching supprimer pizza frame...");

		JPanel panel = new DefaultPanel();

		Font titleFont = new Font("Sylfaen", Font.BOLD, 18);
		Font textFont = new Font("Sylfaen", Font.PLAIN, 14);

		JLabel lblSupprimerUnePizza = new JLabel("Supprimer une Pizza");
		lblSupprimerUnePizza.setFont(titleFont);
		lblSupprimerUnePizza.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupprimerUnePizza.setBounds(10, 11, 440, 27);
		panel.add(lblSupprimerUnePizza);

		JLabel lblPizza = new JLabel("Pizza :");
		lblPizza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPizza.setFont(textFont);
		lblPizza.setBounds(10, 102, 151, 20);
		panel.add(lblPizza);

		cbxPizza = new JComboBox<>();
		cbxPizza.setFont(textFont);
		cbxPizza.setBounds(171, 101, 175, 23);
		panel.add(cbxPizza);
		fillPizzas(dao, 0);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(e -> {
			int id = Integer.parseInt(((CbxItem) cbxPizza.getSelectedItem()).getValue());
			Pizza p;
			try {
				p = dao.getPizzaById(id);
				if (JOptionPane.showConfirmDialog((Component) null,
						"Voulez-vous vraiment supprimer la pizza " + p.getNom() + " ?", "alert",
						JOptionPane.OK_CANCEL_OPTION) == 0) {
					dao.deletePizza(p);
					menu.setStatus("Pizza " + p.getNom() + " supprimée !", 0);
					LOG.info("Pizza has been removed (" + p.getNom() + ")");
					fillPizzas(dao, 0);
				}
			} catch (DeletePizzaException | InvalidPizzaException exc) {
				menu.setStatus("La pizza n'a pas pu être supprimé !", 2);
				LOG.info("Can't remove pizza : {}", exc.getMessage());
			}
		});
		btnSupprimer.setFont(textFont);
		btnSupprimer.setBounds(244, 166, 102, 23);
		panel.add(btnSupprimer);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(e -> menu.hideContenu());
		btnAnnuler.setFont(textFont);
		btnAnnuler.setBounds(137, 166, 89, 23);
		panel.add(btnAnnuler);

		menu.setContenu(panel);

		LOG.info("...supprimer pizza frame launched");
		return true;
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
		LOG.info("Filling pizzas combobox with index {}", selectedIndex);
		List<Pizza> pizzas;
		try {
			pizzas = dao.findAllPizzas();
			cbxPizza.removeAllItems();
			for (Pizza p : pizzas) {
				this.cbxPizza.addItem(new CbxItem(String.valueOf(p.getId()), p.getCode() + " " + p.getNom()));
			}
			cbxPizza.setSelectedIndex(selectedIndex);
		} catch (InvalidPizzaException e) {
			menu.setStatus("La liste des pizza ne peut pas être récupérée", 2);
			LOG.info("Can't fill pizzas", e);
		}
	}

}
