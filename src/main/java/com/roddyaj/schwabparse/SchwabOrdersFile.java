package com.roddyaj.schwabparse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class SchwabOrdersFile
{
	private final Path file;

	private final LocalDateTime time;

	private List<SchwabOrder> orders;

	public SchwabOrdersFile(Path file)
	{
		this.file = file;
		this.time = getTime(file);
	}

	public LocalDateTime getTime()
	{
		return time;
	}

	public synchronized List<SchwabOrder> getOrders()
	{
		if (orders == null)
		{
			try
			{
				// Correct the file contents to be valid CSV
				List<String> lines = Files.lines(file).filter(line -> !line.isEmpty()).map(line -> line.replace("\" Shares", " Shares\"")
					.replace("\" Share", " Share\"").replace("\" Contracts", " Contracts\"").replace("\" Contract", " Contract\"")).toList();

				orders = Utils.readCsv(lines).stream().map(SchwabOrder::new).toList();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return orders;
	}

	public List<SchwabOrder> getOpenOrders()
	{
		return getOrders().stream().filter(o -> "OPEN".equals(o.status())).toList();
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
