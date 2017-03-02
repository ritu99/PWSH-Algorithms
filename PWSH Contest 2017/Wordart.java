/**
 * Wordart.java
 * Super evil problem
 *
 * @author Gahwon Lee
 */

import java.io.*;
import java.util.Scanner;

public class Wordart
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("wordart.in"))
		System.setOut(new PrintStream(new File("wordart.out")));
		String word = in.next();

		for (char c : word.toCharArray())
		{
			if (c == 'C')
			{
				System.out.println("  _____");
				System.out.println(" / ____|");
				System.out.println("| |");
				System.out.println("| |");
				System.out.println("| |____");
				System.out.println(" \\_____|");

			}
			else if (c == 'E')
			{
				System.out.println(" ______");
				System.out.println("|  ____|");
				System.out.println("| |__");
				System.out.println("|  __|");
				System.out.println("| |____");
				System.out.println("|______|");
			}
			else if (c == 'S')
			{
				System.out.println("  _____");
				System.out.println(" / ____|");
				System.out.println("| (___");
				System.out.println(" \\___ \\");
				System.out.println(" ____) |");
				System.out.println("|_____/");
			}
			else if (c == 'T')
			{
				System.out.println(" _______");
				System.out.println("|__   __|");
				System.out.println("   | |");
				System.out.println("   | |");
				System.out.println("   | |");
				System.out.println("   |_|");
			}
			else
			{
				System.out.println("__          __");
				System.out.println("\\ \\        / /");
				System.out.println(" \\ \\  /\\  / /");
				System.out.println("  \\ \\/  \\/ /");
				System.out.println("   \\  /\\  /");
				System.out.println("    \\/  \\/");
			}

			System.out.println();
		}

	}
}
