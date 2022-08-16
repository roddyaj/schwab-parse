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
//		for (SchwabTransaction transaction : new SchwabTransactionsReader().read(file).transactions())
//			System.out.println(transaction);
//		for (SchwabOrder order : new SchwabOrdersReader().read(file).orders())
//			System.out.println(order);
		for (SchwabPosition position : new SchwabPositionsReader().read(file).positions())
			System.out.println(position);
	}
}
