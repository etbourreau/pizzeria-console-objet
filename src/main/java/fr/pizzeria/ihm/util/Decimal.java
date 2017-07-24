package fr.pizzeria.ihm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Decimal {
	
	private Decimal() {
	}

	/**Round double to have 2 decimals (Currency)
	 * @param value
	 * @return
	 */
	public static double priceRound(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
