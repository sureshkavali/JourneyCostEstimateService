package com.screenmedia.demo.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * The helper class to format currency for sale and discounts
 * 
 * @author Suresh
 *
 */
public class CurrencyUtil {

	private static final String MINOR_CURRENCY_TEXT = "%sp";

	/**
	 * Format the sale price in to currency. It is assumed the value will be a
	 * positive value.
	 * 
	 * @param value the sale price (subtotal/total)
	 * @return the currency formatted value (show value in minor currency if value
	 *         is less than £1 and format in to major currency if the value is more
	 *         than £1)
	 */
	public static String formatMonetaryValue(BigDecimal value) {
		if (BigDecimal.valueOf(99).compareTo(value) > 0) {
			return String.format(MINOR_CURRENCY_TEXT, value.setScale(0, RoundingMode.HALF_UP));
		}

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
		return currencyFormat.format(value.divide(BigDecimal.valueOf(100)));
	}

}
