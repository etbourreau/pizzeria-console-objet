package fr.pizzeria.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Decimal {
	
	/**Round double to have 2 decimals (Currency)
	 * @param value
	 * @return
	 */
	public static double priceRound(double value) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
