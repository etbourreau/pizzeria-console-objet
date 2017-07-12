package test.java;

import main.java.fr.pizzeria.model.CategoriePizza;
import main.java.fr.pizzeria.model.Pizza;

public class TestToStringAnnotation {

	public static void main(String[] args) {
		Pizza p = new Pizza(0, "MON", "Montagnarde", 15.00, CategoriePizza.VIANDE);
		System.out.println(p.toString());

	}

}
