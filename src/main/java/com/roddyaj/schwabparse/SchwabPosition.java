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
	String securityType,
	SchwabOption option)
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
			Utils.parseBoolean(Utils.getOrNull(record, "Reinvest Dividends?")),
			Utils.parseBoolean(Utils.getOrNull(record, "Capital Gains?")),
			Utils.parseDouble(record.get("% Of Account")),
			Utils.parseDouble(Utils.getOrNull(record, "Dividend Yield")),
			Utils.parseDouble(Utils.getOrNull(record, "Last Dividend")),
			Utils.parseDate(Utils.getOrNull(record, "Ex-Dividend Date")),
			Utils.parseDouble(Utils.getOrNull(record, "P/E Ratio")),
			Utils.parseDouble(Utils.getOrNull(record, "52 Week Low")),
			Utils.parseDouble(Utils.getOrNull(record, "52 Week High")),
			Utils.parseInt(Utils.getOrNull(record, "Volume")),
			Utils.parseDouble(Utils.getOrNull(record, "Intrinsic Value")),
			Utils.parseString(Utils.getOrNull(record, "In The Money")),
			Utils.getOrNull(record, "Security Type"),
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

	public String toCsvString()
	{
		return Arrays
			.asList(symbol, description, quantity, price, priceChange, priceChangePct, marketValue, dayChange, dayChangePct, costBasis, gainLoss,
				gainLossPct, reinvestDividends, capitalGains, percentOfAccount, dividendYield, lastDividend, exDividendDate, peRatio, _52WeekLow,
				_52WeekHigh, volume, intrinsicValue, inTheMoney, securityType)
			.stream().map(o -> o != null ? o.toString() : "").collect(Collectors.joining(","));
	}
}
