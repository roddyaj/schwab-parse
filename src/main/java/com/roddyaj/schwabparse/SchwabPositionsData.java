package com.roddyaj.schwabparse;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SchwabPositionsData(ZonedDateTime time, List<SchwabPosition> positions, double balance, double cash)
{
	public String toCsvString()
	{
		return positions.stream().map(SchwabPosition::toCsvString).collect(Collectors.joining("\n"));
	}
}
