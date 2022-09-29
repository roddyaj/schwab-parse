package com.roddyaj.schwabparse;

import java.time.LocalDate;

import org.apache.commons.csv.CSVRecord;

public record SchwabTransaction(
	LocalDate date,
	String action,
	String symbol,
	String description,
	Double quantity,
	Double price,
	Double feesAndComm,
	Double amount,
	SchwabOption option)
{
	public SchwabTransaction(CSVRecord record)
	{
		// @formatter:off
		this(
			Utils.parseDate(record.get("Date")),
			record.get("Action"),
			Utils.parseString(record.get("Symbol")),
			record.get("Description"),
			Utils.parseDouble(record.get("Quantity")),
			Utils.parseDouble(record.get("Price")),
			Utils.parseDouble(record.get("Fees & Comm")),
			Utils.parseDouble(record.get("Amount")),
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
}
