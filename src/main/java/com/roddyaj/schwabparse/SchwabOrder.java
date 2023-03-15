package com.roddyaj.schwabparse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.csv.CSVRecord;

public record SchwabOrder(
	String symbol,
	String nameOfSecurity,
	String action,
	int quantity,
	String orderType,
	Double limitPrice,
	LocalDateTime timeAndDate_ET,
	String status,
	long orderNumber,
	SchwabOption option)
{
	private static final DateTimeFormatter TIME_AND_DATE_FORMAT = DateTimeFormatter.ofPattern("h:mm a MM/dd/yyyy");

	public SchwabOrder(CSVRecord record)
	{
		// @formatter:off
		this(
			record.get("Symbol"),
			record.get("Name of security"),
			record.get("Action"),
			Integer.parseInt(record.get("Quantity|Face Value").split(" ")[0]),
			getOrderType(record.get("Price")),
			getLimitPrice(record.get("Price")),
			LocalDateTime.parse(record.get("Time and Date(ET)"), TIME_AND_DATE_FORMAT),
			record.get("Status"),
			Utils.parseLong(record.get("Order Number")),
			SchwabOption.parse(record.get("Symbol")));
		// @formatter:on
	}

	public boolean isOption()
	{
		return option != null;
	}

	public String getActualSymbol()
	{
		return isOption() ? option.symbol() : symbol;
	}

	private static String getOrderType(String price)
	{
		return price.contains(" ") ? price.split(" ")[0] : price;
	}

	private static Double getLimitPrice(String price)
	{
		return price.contains(" ") ? Utils.parseDouble(price.split(" ")[1]) : null;
	}
}
