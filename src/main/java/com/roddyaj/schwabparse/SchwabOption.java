package com.roddyaj.schwabparse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record SchwabOption(String symbol, LocalDate expiryDate, double strike, String type)
{
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy");

	public static SchwabOption parse(String text)
	{
		if (!isOption(text))
			return null;

		String[] tokens = text.split(" ");
		String symbol = tokens[0];
		LocalDate expiryDate = LocalDate.parse(tokens[1], DATE_FORMAT);
		double strike = Double.parseDouble(tokens[2]);
		String type = tokens[3];
		return new SchwabOption(symbol, expiryDate, strike, type);
	}

	public static boolean isOption(String symbol)
	{
		return symbol.indexOf(' ') != -1 && (symbol.endsWith("C") || symbol.endsWith("P"));
	}
}
