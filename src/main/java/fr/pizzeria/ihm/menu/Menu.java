package fr.pizzeria.ihm.menu;

import javax.swing.JPanel;

import fr.pizzeria.model.Client;

public interface Menu {
	
	public void setStatus(String msg, int severity);
	
	public void hideContenu();
	
	public void setContenu(JPanel panel);
	
	default void setSession(Client session) {
	}
	
	default void stopSession() {
	}
	
}
