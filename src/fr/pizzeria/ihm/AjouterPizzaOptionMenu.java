package fr.pizzeria.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.pizzeria.dao.PizzaDaoMemoire;
import fr.pizzeria.exception.CategoryNotFoundException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.CbxItem;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.util.Decimal;

public class AjouterPizzaOptionMenu extends OptionMenu{
	
	private JTextField txtCode;
	private JTextField txtNom;
	private JTextField txtPrix;
	private JComboBox<CbxItem> cbxCategorie;
	
	public AjouterPizzaOptionMenu(PizzaDaoMemoire dao, Menu m){
		super(dao, m);
		this.libelle = "Ajouter une Pizza";
	}

	@Override
	public boolean execute() {
		
		JPanel panel = new DefaultPanel();
		
		JLabel lblAjouterUnePizza = new JLabel("Ajouter une Pizza");
		lblAjouterUnePizza.setFont(new Font("Sylfaen", Font.BOLD, 18));
		lblAjouterUnePizza.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjouterUnePizza.setBounds(10, 11, 440, 27);
		panel.add(lblAjouterUnePizza);
		
		JLabel lblCode = new JLabel("Code (3 caract\u00E8res) :");
		lblCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCode.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblCode.setBounds(10, 67, 151, 20);
		panel.add(lblCode);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblNom.setBounds(10, 98, 151, 20);
		panel.add(lblNom);
		
		JLabel lblPrix = new JLabel("Prix :");
		lblPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrix.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblPrix.setBounds(10, 129, 151, 20);
		panel.add(lblPrix);
		
		txtCode = new JTextField();
		txtCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(txtCode.getText().length()+1==3){
					txtCode.setBackground(new Color(1f, 1f, 1f));
				}
				if(txtCode.getText().length()>2){
					txtCode.setText(txtCode.getText().substring(0, 2));
				}
			}
		});
		txtCode.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		txtCode.setBounds(171, 66, 175, 23);
		panel.add(txtCode);
		txtCode.setColumns(10);
		
		txtNom = new JTextField();
		txtNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				txtNom.setBackground(new Color(1f, 1f, 1f));
			}
		});
		txtNom.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		txtNom.setColumns(10);
		txtNom.setBounds(171, 98, 175, 23);
		panel.add(txtNom);
		
		txtPrix = new JTextField();
		txtPrix.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtPrix.setBackground(new Color(1f, 1f, 1f));
			}
		});
		txtPrix.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		txtPrix.setColumns(10);
		txtPrix.setBounds(171, 128, 162, 23);
		panel.add(txtPrix);
		
		JLabel lblPrixDevise = new JLabel("\u20AC");
		lblPrixDevise.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixDevise.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblPrixDevise.setBounds(330, 129, 16, 20);
		panel.add(lblPrixDevise);
		
		JLabel lblCatgorie = new JLabel("Cat\u00E9gorie :");
		lblCatgorie.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCatgorie.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblCatgorie.setBounds(10, 166, 151, 20);
		panel.add(lblCatgorie);
		
		cbxCategorie = new JComboBox<CbxItem>();
		cbxCategorie.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		cbxCategorie.setBounds(171, 162, 175, 23);
		panel.add(cbxCategorie);
		fillCategories(CategoriePizza.values(), 0);
		panel.add(cbxCategorie);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validFields()){
					try {
						dao.saveNewPizza(new Pizza(dao.getNextAvailableId(), txtCode.getText().toUpperCase(), txtNom.getText().trim(), Decimal.priceRound(Double.parseDouble(txtPrix.getText())), CategoriePizza.findCategoryById(((CbxItem) cbxCategorie.getSelectedItem()).getValue())));
						menu.setStatus("Pizza "+txtNom.getText()+" ajoutée !", 0);
						initFields();
						fillCategories(CategoriePizza.values(), 0);
					} catch (SavePizzaException | NumberFormatException | CategoryNotFoundException exc) {
						menu.setStatus("La pizza "+txtNom.getText()+" n'a pas pu être ajoutée !", 2);
						System.out.println("La pizza "+txtNom.getText()+" n'a pas pu être ajoutée !");
						System.out.println(exc.getMessage());
					}
				}
			}
		});
		btnValider.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		btnValider.setBounds(257, 208, 89, 23);
		panel.add(btnValider);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.hideContenu();
			}
		});
		btnAnnuler.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		btnAnnuler.setBounds(146, 208, 89, 23);
		panel.add(btnAnnuler);
		
		menu.setContenu(panel);
		
		return true;
	}
	
	/**
	 * Empties field for new Pizza
	 */
	private void initFields(){
		txtCode.setText("");
		txtNom.setText("");
		txtPrix.setText("");
	}
	
	/** Checks if all fields are correct and colors in red wrong ones
	 * @return true if fields are valid, false otherwise
	 */
	private boolean validFields(){
		boolean valid = true;
		if(txtCode.getText().length()!=3){
			txtCode.setBackground(new Color(0.8f, 0.2f, 0.2f));
			valid = false;
		}
		if(txtNom.getText().length()==0){
			txtNom.setBackground(new Color(0.8f, 0.2f, 0.2f));
			valid = false;
		}
		if(txtPrix.getText().length()==0){
			txtPrix.setBackground(new Color(0.8f, 0.2f, 0.2f));
			valid = false;
		}
		try{
			Double.parseDouble(txtPrix.getText());
		}catch(NumberFormatException e){
			txtPrix.setBackground(new Color(0.8f, 0.2f, 0.2f));
			return false;
		}
		return valid;
	}
	
	/**Fills the categories JComboBox
	 * @param categories of pizza
	 * @param selectedIndex by default
	 */
	private void fillCategories(CategoriePizza[] categories, int selectedIndex){
		cbxCategorie.removeAllItems();
		for(CategoriePizza cp : categories){
			this.cbxCategorie.addItem(new CbxItem(cp.toString(), cp.getDescription()));
		}
		cbxCategorie.setSelectedIndex(selectedIndex);
	}

}
