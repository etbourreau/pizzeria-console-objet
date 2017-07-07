package fr.pizzeria.ihm;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.pizzeria.dao.PizzaDaoMemoire;
import fr.pizzeria.util.*;

import javax.swing.JPanel;

/**
 * @author etbourreau
 * main menu frame
 */
public class Menu extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static JLabel statusBar;
	public static boolean busy = false;
	static ArrayList<OptionMenu> options;
	private PizzaDaoMemoire dao;
	private JPanel panelContenu;
	
	/**
	 * Main menu constructor
	 */
	public Menu() {
		this.dao = new PizzaDaoMemoire();
		this.init();
		setContentPane(new ImagePanel(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/fr/pizzeria/assets/background.jpg"))));
		setSize(592,418);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/fr/pizzeria/assets/icon.ico")));
		setTitle("Pizzeria Administration");
		getContentPane().setLayout(null);
		
		statusBar = new JLabel("");
		statusBar.setHorizontalAlignment(SwingConstants.LEFT);
		statusBar.setBounds(5, 343, 574, 25);
		getContentPane().add(statusBar);
		
		panelContenu = new JPanel();
		panelContenu.setBounds(61, 45, 460, 260);
		panelContenu.setVisible(false);
		getContentPane().add(panelContenu);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		loadOptionMenus(Menu.options, mnFichier);
		JMenuItem mntmQuit = new JMenuItem("Quitter");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				while(busy){
					try {
						wait(100l);
					} catch (InterruptedException exc){}
				}
				System.exit(0);
			}
		});
		mnFichier.add(mntmQuit);
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		JMenuItem mntmAPropos = new JMenuItem("A propos");
		mnAide.add(mntmAPropos);
		mntmAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Ce programme a été concçu par Etienne Bourreau dans le cadre de la formation DTA JavaEE 2017.\n"
						+ "Pour toute question ou information, vous pouvez me contacter par mail à \"et.bourreau@laposte.net\"\n"
						+ "ou vous aller voir mon portfolio à \"etiennebourreau.jimdo.com\"."
						+ "Merci :>";
				JOptionPane.showMessageDialog(null,message,"Aidez-moi",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		centreWindow(this);
		
		setVisible(true);
	}

	/** Center the window on the screen
	 * @param the frame object
	 */
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	
	/**
	 * Initializes default variables
	 */
	private void init(){
		PizzaDaoMemoire.init();
		options = new ArrayList<OptionMenu>();
		options.add(new ListerPizzaOptionMenu(dao, this));
		options.add(new AjouterPizzaOptionMenu(dao, this));
		options.add(new ModifierPizzaOptionMenu(dao, this));
		options.add(new SupprimerPizzaOptionMenu(dao, this));
	}
	
	/** loads all options in the JMenu
	 * @param ArrayList<OptionMenu> to add in the JMenu
	 * @param JMenu which receive options
	 */
	private void loadOptionMenus(ArrayList<OptionMenu> options, JMenu menu) {
		for(OptionMenu om : options){
			JMenuItem currentMenuItem = new JMenuItem(om.getLibelle());
			menu.add(currentMenuItem);
			currentMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					om.execute();
				}
			});
		}
	}

	/** Sends and show a message in status bar depending on severity (color)
	 * @param msg to show in status bar
	 * @param severity from 0 to 2 (green, black, red)
	 */
	public void setStatus(String msg, int severity){
		switch(severity){
		case 0:
			statusBar.setForeground(new Color(0.2f, 0.8f, 0.2f));
			break;
		case 1:
			statusBar.setForeground(new Color(0f, 0f, 0f));
			break;
		case 2:
			statusBar.setForeground(new Color(0.8f, 0.2f, 0.2f));
			break;
		default:
			return;
		}
		statusBar.setText(msg);
	}
	
	/** shows content JPanel
	 * @param the JPanel to show
	 */
	public void setContenu(JPanel contenu){
		this.remove(panelContenu);
		this.panelContenu.removeAll();
		this.panelContenu = contenu;
		getContentPane().add(panelContenu);
		this.panelContenu.revalidate();
		this.panelContenu.repaint();
	}
	
	/**
	 * hide content Jpanel
	 */
	public void hideContenu(){
		this.panelContenu.setVisible(false);
	}
}
