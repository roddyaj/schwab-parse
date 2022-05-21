package com.roddyaj.schwabparse;

import java.nio.file.Path;

public final class Main
{
	public static void main(String[] args)
	{
		new Main().run(args);
	}

	public void run(String[] args)
	{
		Path file = Path.of(args[0]);
//		for (SchwabTransaction transaction : new SchwabTransactionsFile(file).getTransactions())
//			System.out.println(transaction);
		for (SchwabOpenOrder order : new SchwabOpenOrdersFile(file).getOpenOrders())
			System.out.println(order);
	}
}
