package com.roddyaj.schwabparse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SchwabPositionsReader
{
	private static final Pattern DATE_PATTERN = Pattern.compile("as of (.+?)\"");
	private static final DateTimeFormatter DATE_TIME_FORMAT_OLD = DateTimeFormatter.ofPattern("hh:mm a v, MM/dd/yyyy");
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a v, yyyy/MM/dd");

	public SchwabPositionsData read(Path file)
	{
		Map<Boolean, List<SchwabPosition>> map = Utils.readCsv(file, 2).stream().map(SchwabPosition::new)
			.collect(Collectors.partitioningBy(p -> p.quantity() == null));
		List<SchwabPosition> positions = map.get(false);
		List<SchwabPosition> otherPositions = map.get(true);
		SchwabPosition balancePosition = otherPositions.stream().filter(p -> p.symbol().contains("Account Total")).findAny().orElse(null);
		double balance = balancePosition != null ? balancePosition.marketValue() : 0;
		SchwabPosition cashPosition = otherPositions.stream().filter(p -> p.symbol().contains("Cash")).findAny().orElse(null);
		double cash = cashPosition != null ? cashPosition.marketValue() : 0;
		return new SchwabPositionsData(getTime(file), positions, balance, cash);
	}

	public static ZonedDateTime getTime(Path file)
	{
		ZonedDateTime time = null;
		try
		{
			List<String> lines = Files.readAllLines(file);

			Matcher matcher = DATE_PATTERN.matcher(lines.get(0));
			if (matcher.find())
			{
				try
				{
					time = ZonedDateTime.parse(matcher.group(1), DATE_TIME_FORMAT_OLD);
				}
				catch (DateTimeParseException e)
				{
					time = ZonedDateTime.parse(matcher.group(1), DATE_TIME_FORMAT);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return time;
	}
}
