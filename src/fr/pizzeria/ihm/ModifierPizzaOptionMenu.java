package fr.pizzeria.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.pizzeria.dao.PizzaDaoMemoire;
import fr.pizzeria.exception.CategoryNotFoundException;
import fr.pizzeria.exception.InvalidPizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.CbxItem;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.util.Decimal;

public class ModifierPizzaOptionMenu extends OptionMenu{
	
	private JComboBox<CbxItem> cbxPizza;
	private JTextField txtCode;
	private JTextField txtNom;
	private JTextField txtPrix;
	private JComboBox<CbxItem> cbxCategorie;
	private boolean lock = false;
	
	public ModifierPizzaOptionMenu(PizzaDaoMemoire dao, Menu m){
		super(dao, m);
		this.libelle = "Modifier une Pizza";
	}

	@Override
	public boolean execute() {
		
		JPanel panel = new DefaultPanel();
		
		JLabel lblModifierUnePizza = new JLabel("Modifier une Pizza");
		lblModifierUnePizza.setFont(new Font("Sylfaen", Font.BOLD, 18));
		lblModifierUnePizza.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifierUnePizza.setBounds(10, 11, 440, 27);
		panel.add(lblModifierUnePizza);
		
		JLabel lblPizza = new JLabel("Pizza :");
		lblPizza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPizza.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblPizza.setBounds(10, 55, 151, 20);
		panel.add(lblPizza);
		
		cbxPizza = new JComboBox<CbxItem>();
		cbxPizza.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		cbxPizza.setBounds(171, 54, 175, 23);
		panel.add(cbxPizza);
		fillPizzas(dao, 0);
		
		JLabel lblCode = new JLabel("Code (3 caract\u00E8res) :");
		lblCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCode.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblCode.setBounds(10, 89, 151, 20);
		panel.add(lblCode);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblNom.setBounds(10, 123, 151, 20);
		panel.add(lblNom);
		
		JLabel lblPrix = new JLabel("Prix :");
		lblPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrix.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblPrix.setBounds(10, 154, 151, 20);
		panel.add(lblPrix);
		
		txtCode = new JTextField();
		txtCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(txtCode.getText().length()+1==3){
					txtCode.setBackground(new Color(1f, 1f, 1f));
				}
				if(txtCode.getText().length()>2){
					txtCode.setText(txtCode.getText().substring(0, 2));
				}
			}
		});
		txtCode.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		txtCode.setBounds(171, 88, 175, 23);
		panel.add(txtCode);
		txtCode.setColumns(10);
		
		txtNom = new JTextField();
		txtNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtNom.setBackground(new Color(1f, 1f, 1f));
			}
		});
		txtNom.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		txtNom.setColumns(10);
		txtNom.setBounds(171, 122, 175, 23);
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
		txtPrix.setBounds(171, 153, 162, 23);
		panel.add(txtPrix);
		
		JLabel lblPrixDevise = new JLabel("\u20AC");
		lblPrixDevise.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixDevise.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblPrixDevise.setBounds(330, 154, 16, 20);
		panel.add(lblPrixDevise);
		
		cbxCategorie = new JComboBox<CbxItem>();
		cbxCategorie.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		cbxCategorie.setBounds(171, 187, 175, 23);
		panel.add(cbxCategorie);
		try {
			fillCategories(CategoriePizza.values(), dao.getPizzaById(Integer.parseInt((((CbxItem)cbxPizza.getSelectedItem()).getValue()))).getCategorie().toString());
		} catch (NumberFormatException exc) {
			System.out.println("La conversion de "+((CbxItem)cbxPizza.getSelectedItem()).getValue()+" en int est invalide");
			System.out.println(exc.getMessage());
		} catch (InvalidPizzaException exc) {
			menu.setStatus("La pizza sélectionnée est invalide", 2);
			System.out.println("La pizza sélectionnée est invalide");
			System.out.println(exc.getMessage());
		}
		
		JLabel lblCatgorie = new JLabel("Cat\u00E9gorie :");
		lblCatgorie.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCatgorie.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		lblCatgorie.setBounds(10, 188, 151, 20);
		panel.add(lblCatgorie);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(((CbxItem)cbxPizza.getSelectedItem()).getValue());
					Pizza p = dao.getPizzaById(id);
					if(validFields() && 
							(!txtCode.getText().equals(p.getCode()) || 
									!txtNom.getText().equals(p.getNom()) ||
									Decimal.priceRound(Double.parseDouble(txtPrix.getText()))!=p.getPrix() || 
									!((CbxItem)cbxCategorie.getSelectedItem()).getLabel().equalsIgnoreCase(p.getCategorie().getDescription()))){
						try {
							dao.updatePizza(new Pizza(id, txtCode.getText(), txtNom.getText(), Decimal.priceRound(Double.parseDouble(txtPrix.getText())), CategoriePizza.findCategoryById(((CbxItem) cbxCategorie.getSelectedItem()).getValue())));
							menu.setStatus("Pizza "+txtNom.getText()+" modifiée !", 0);
							lock = true;
							fillPizzas(dao, cbxPizza.getSelectedIndex());
							lock = false;
						} catch (UpdatePizzaException | NumberFormatException | CategoryNotFoundException exc) {
							menu.setStatus("La pizza "+txtNom.getText()+" n'a pas pu être ajoutée !", 2);
							System.out.println("La pizza "+txtNom.getText()+" n'a pas pu être ajoutée !");
							System.out.println(exc.getMessage());
						}
					}
				} catch (NumberFormatException | InvalidPizzaException exc) {
					System.out.println("La conversion de "+((CbxItem)cbxPizza.getSelectedItem()).getValue()+" en int est invalide");
					System.out.println(exc.getMessage());
				}
			}
		});
		btnValider.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		btnValider.setBounds(259, 226, 89, 23);
		panel.add(btnValider);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.hideContenu();
			}
		});
		btnAnnuler.setFont(new Font("Sylfaen", Font.PLAIN, 14));
		btnAnnuler.setBounds(146, 226, 89, 23);
		panel.add(btnAnnuler);
		
		cbxPizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!lock){
					initFields();
					try {
						fillCategories(CategoriePizza.values(), dao.getPizzaById(Integer.parseInt((((CbxItem)cbxPizza.getSelectedItem()).getValue()))).getCategorie().toString());
					} catch (NumberFormatException exc) {
						System.out.println("La conversion de "+((CbxItem)cbxPizza.getSelectedItem()).getValue()+" en int est invalide");
						System.out.println(exc.getMessage());
					} catch (InvalidPizzaException exc) {
						menu.setStatus("La pizza sélectionnée est invalide", 2);
						System.out.println("La pizza sélectionnée est invalide");
						System.out.println(exc.getMessage());
					}
				}
			}
		});
		
		initFields();
		
		menu.setContenu(panel);
		
		return true;
	}
	
	/**
	 * Fills fields with selected pizza informations
	 */
	private void initFields(){
		int id = Integer.parseInt(((CbxItem)cbxPizza.getSelectedItem()).getValue());
		Pizza p;
		try {
			p = dao.getPizzaById(id);
			txtCode.setText(p.getCode());
			txtCode.setBackground(new Color(1f, 1f, 1f));
			txtNom.setText(p.getNom());
			txtNom.setBackground(new Color(1f, 1f, 1f));
			txtPrix.setText(String.valueOf(p.getPrix()));
			txtPrix.setBackground(new Color(1f, 1f, 1f));
		} catch (InvalidPizzaException exc) {
			menu.setStatus("Pizza Invalide", 2);
			System.out.println("Pizza Invalide");
			System.out.println(exc.getMessage());
		}
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
	
	private void fillCategories(CategoriePizza[] categories, String selectedCategoryName){
		cbxCategorie.removeAllItems();
		CbxItem currentItem;
		for(CategoriePizza cp : categories){
			currentItem = new CbxItem(cp.toString(), cp.getDescription());
			this.cbxCategorie.addItem(currentItem);
			if(currentItem.getValue().equals(selectedCategoryName)){
				cbxCategorie.setSelectedItem(currentItem);
			}
		}
	}

}
