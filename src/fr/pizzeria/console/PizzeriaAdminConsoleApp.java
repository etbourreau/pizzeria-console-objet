package fr.pizzeria.console;

import java.util.Locale;
import java.util.Scanner;

import fr.pizzeria.model.Pizza;

/**
 * @author etbourreau
 * Main Launcher class
 */
public class PizzeriaAdminConsoleApp {

	static Scanner sc = new Scanner(System.in).useLocale(Locale.US);

	public static void main(String[] args) {

		Pizza.initPizzas();
		int choice;

		do {
			do {
				System.out.println("\n***** Pizzeria Administration *****\n" + "1. Lister les pizzas\n"
						+ "2. Ajouter une nouvelle pizza\n" + "3. Mettre à jour une pizza\n"
						+ "4. Supprimer une pizza\n" + "99. Sortir");
				choice = sc.nextInt();
			} while (choice < 1 || choice > 4 && choice != 99);

			switch (choice) {
			case 1:
				showPizzas();
				break;
			case 2:
				addPizza();
				break;
			case 3:
				modPizza();
				break;
			case 4:
				delPizza();
				break;
			}

		} while (choice != 99);
		cleanConsole(50);
		System.out.println("Aurevoir \u2639");
		sc.close();
	}

	public static void cleanConsole(int occ) {
		System.out.println("");
		if(occ>0){
			cleanConsole(--occ);
		}
	}

	private static void showPizzas() {
		cleanConsole(50);
		System.out.println("Liste des pizzas\n");
		for (Pizza p : Pizza.allPizzas) {
			System.out.println(p.getCode() + " -> " + p.getNom() + " (" + p.getPrix() + "€)");
		}
		System.out.println("Appuyez sur Entrée pour continuer...");
		sc.nextLine();
		sc.nextLine();
		cleanConsole(50);
	}

	private static void addPizza() {
		String code, nom, prix;

		System.out.println("Ajout d'une nouvelle pizza");

		do {
			System.out.println("\nVeuillez saisir le code (3 caractères):");
			System.out.println("(99 pour abandonner)");
			code = sc.next();
		} while (code.length() != 3 && !code.equals("99"));
		if (code.equals("99")) {
			return;
		}

		System.out.println("\nVeuillez saisir le nom:");
		sc.nextLine();
		nom = sc.nextLine();

		do {
			System.out.println("\nVeuillez saisir le prix:");
			prix = sc.nextLine();
		} while (!isDouble(prix));

		new Pizza(Pizza.getNextAvailableId(), code, nom, Double.parseDouble(prix));
		cleanConsole(50);
		System.out.println("Pizza ajoutée !\n");
	}

	private static void modPizza() {
		int selectPizza;
		String code, nom, prix;

		System.out.println("Mise à jour d'une pizza\n");
		for (Pizza p : Pizza.allPizzas) {
			System.out.println(p.getId() + " " + p.getCode() + " - " + p.getNom() + " (" + p.getPrix() + "€)");
		}
		do {
			System.out.println("Veuillez choisir la pizza à modifier.");
			System.out.println("(99 pour abandonner)");
			selectPizza = sc.nextInt();
		} while (!((Integer) selectPizza instanceof Integer)
				&& (Pizza.getPizzaById(selectPizza) == null || selectPizza != 99));
		if (selectPizza == 99) {
			return;
		}
		Pizza currentPizza = Pizza.getPizzaById(selectPizza);
		do {
			System.out.println("Veuillez saisir le code: (" + currentPizza.getCode() + ")");
			sc.nextLine();
			code = sc.nextLine();
			if (code.equals("")) {
				code = currentPizza.getCode();
			}
		} while (code.length() != 3);

		System.out.println("Veuillez saisir le nom (sans espace): (" + currentPizza.getNom() + ")");
		nom = sc.nextLine();
		if (nom.equals("")) {
			nom = currentPizza.getNom();
		}

		do {
			System.out.println("Veuillez saisir le prix: (" + currentPizza.getPrix() + ")");
			prix = sc.nextLine();
			if (prix.equals("")) {
				prix = String.valueOf(currentPizza.getPrix());
			}
		} while (!isDouble(prix));

		if (!Pizza.getPizzaById(selectPizza).getCode().equals(code)) {
			Pizza.getPizzaById(selectPizza).setCode(code);
		}

		if (!Pizza.getPizzaById(selectPizza).getNom().equals(nom)) {
			Pizza.getPizzaById(selectPizza).setNom(nom);
		}

		if (!String.valueOf(Pizza.getPizzaById(selectPizza).getPrix()).equals(prix)) {
			Pizza.getPizzaById(selectPizza).setPrix(Double.valueOf(prix));
		}
		cleanConsole(50);
		System.out.println("Pizza modifiée !\n");
	}

	private static void delPizza() {
		String selectPizza;

		System.out.println("Suppression d'une pizza\n");
		for (Pizza p : Pizza.allPizzas) {
			System.out.println(p.getId() + " " + p.getCode() + " - " + p.getNom() + " (" + p.getPrix() + "€)");
		}
		sc.nextLine();
		do {
			System.out.println("Veuillez choisir la pizza à supprimer.");
			System.out.println("(99 pour abandonner)");
			selectPizza = sc.nextLine();
		} while(Pizza.getPizzaByCode(selectPizza)==null && (!isInt(selectPizza) && Pizza.getPizzaById(Integer.parseInt(selectPizza))==null));
		if (selectPizza.equals("99")) {
			return;
		}
		Pizza delPizza = (isInt(selectPizza))? Pizza.getPizzaById(Integer.parseInt(selectPizza)) : Pizza.getPizzaByCode(selectPizza);
		Pizza.allPizzas.remove(Pizza.allPizzas.indexOf(delPizza));
		Pizza.sortPizzasById();
		cleanConsole(50);
		System.out.println("Pizza supprimée !\n");
	}

	private static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
