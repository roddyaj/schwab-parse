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
		List<SchwabOrder> orders = Utils.readCsv(file, 0).stream().map(SchwabOrder::new).toList();
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
