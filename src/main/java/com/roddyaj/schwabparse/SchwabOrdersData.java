package com.roddyaj.schwabparse;

import java.time.LocalDateTime;
import java.util.List;

public record SchwabOrdersData(LocalDateTime time, List<SchwabOrder> orders)
{
	public List<SchwabOrder> getOpenOrders()
	{
		return orders.stream().filter(o -> "OPEN".equals(o.status())).toList();
	}
}
