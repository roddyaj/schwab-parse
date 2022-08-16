package com.roddyaj.schwabparse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class SchwabOrdersReader
{
	public SchwabOrdersData read(Path file)
	{
		List<SchwabOrder> orders;
		try
		{
			// Correct the file contents to be valid CSV
			List<String> lines = Files.lines(file).filter(line -> !line.isEmpty()).map(line -> line.replace("\" Shares", " Shares\"")
				.replace("\" Share", " Share\"").replace("\" Contracts", " Contracts\"").replace("\" Contract", " Contract\"")).toList();

			orders = Utils.readCsv(lines).stream().map(SchwabOrder::new).toList();
		}
		catch (IOException e)
		{
			orders = List.of();
			e.printStackTrace();
		}
		return new SchwabOrdersData(getTime(file), orders);
	}

	public static LocalDateTime getTime(Path file)
	{
		LocalDateTime time = null;
		try
		{
			time = LocalDateTime.ofInstant(Files.getLastModifiedTime(file).toInstant(), ZoneId.systemDefault());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return time;
	}
}
