package com.roddyaj.schwabparse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;

public record SchwabPosition(
	String symbol,
	String description,
	Integer quantity,
	Double price,
	Double priceChange,
	Double priceChangePct,
	double marketValue,
	Double dayChange,
	Double dayChangePct,
	Double costBasis,
	Double gainLoss,
	Double gainLossPct,
	boolean reinvestDividends,
	boolean capitalGains,
	Double percentOfAccount,
	Double dividendYield,
	Double lastDividend,
	LocalDate exDividendDate,
	Double peRatio,
	Double _52WeekLow,
	Double _52WeekHigh,
	Integer volume,
	Double intrinsicValue,
	String inTheMoney,
	String securityType)
{
	public SchwabPosition(CSVRecord record)
	{
		// @formatter:off
		this(
			record.get("Symbol"),
			Utils.parseString(record.get("Description")),
			Utils.parseInt(record.get("Quantity")),
			Utils.parseDouble(record.get("Price")),
			Utils.parseDouble(record.get("Price Change $")),
			Utils.parseDouble(record.get("Price Change %")),
			Utils.parseDouble(record.get("Market Value")),
			Utils.parseDouble(record.get("Day Change $")),
			Utils.parseDouble(record.get("Day Change %")),
			Utils.parseDouble(Utils.getOrNull(record, "Cost Basis")),
			Utils.parseDouble(Utils.getOrNull(record, "Gain/Loss $")),
			Utils.parseDouble(Utils.getOrNull(record, "Gain/Loss %")),
			Utils.parseBoolean(record.get("Reinvest Dividends?")),
			Utils.parseBoolean(record.get("Capital Gains?")),
			Utils.parseDouble(record.get("% Of Account")),
			Utils.parseDouble(record.get("Dividend Yield")),
			Utils.parseDouble(record.get("Last Dividend")),
			Utils.parseDate(record.get("Ex-Dividend Date")),
			Utils.parseDouble(record.get("P/E Ratio")),
			Utils.parseDouble(record.get("52 Week Low")),
			Utils.parseDouble(record.get("52 Week High")),
			Utils.parseInt(record.get("Volume")),
			Utils.parseDouble(Utils.getOrNull(record, "Intrinsic Value")),
			Utils.parseString(Utils.getOrNull(record, "In The Money")),
			Utils.getOrNull(record, "Security Type"));
		// @formatter:on
	}

	public String toCsvString()
	{
		return Arrays
			.asList(symbol, description, quantity, price, priceChange, priceChangePct, marketValue, dayChange, dayChangePct, costBasis, gainLoss,
				gainLossPct, reinvestDividends, capitalGains, percentOfAccount, dividendYield, lastDividend, exDividendDate, peRatio, _52WeekLow,
				_52WeekHigh, volume, intrinsicValue, inTheMoney, securityType)
			.stream().map(o -> o != null ? o.toString() : "").collect(Collectors.joining(","));
	}
}
