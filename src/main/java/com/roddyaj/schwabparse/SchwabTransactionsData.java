package com.roddyaj.schwabparse;

import java.time.LocalDateTime;
import java.util.List;

public record SchwabTransactionsData(LocalDateTime time, List<SchwabTransaction> transactions)
{
}
