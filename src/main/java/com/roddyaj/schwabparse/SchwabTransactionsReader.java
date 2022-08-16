package com.roddyaj.schwabparse;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchwabTransactionsReader
{
	private static final Pattern FILE_PATTERN = Pattern.compile("(.+?)_Transactions_([-\\d]+).csv");

	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

	public SchwabTransactionsData read(Path file)
	{
		List<SchwabTransaction> transactions = Utils.readCsv(file, 1).stream().map(SchwabTransaction::new).filter(t -> t.date() != null).toList();
		return new SchwabTransactionsData(getTime(file), transactions);
	}

	public static LocalDateTime getTime(Path file)
	{
		LocalDateTime time = null;
		Matcher m = FILE_PATTERN.matcher(file.getFileName().toString());
		if (m.find())
			time = LocalDateTime.parse(m.group(2), DATE_TIME_FORMAT);
		return time;
	}
}
