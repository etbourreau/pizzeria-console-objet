package fr.pizzeria.ihm.admin.util;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * @author Utilisateur
 * Default panel for OptionMenus
 */
public class DefaultPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public DefaultPanel(){
		super();
		setBounds(61, 45, 460, 260);
		setBackground(Color.LIGHT_GRAY);
		setVisible(true);
		setOpaque(true);
		setLayout(null);
		removeAll();
	}
}
