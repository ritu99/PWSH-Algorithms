/**
 * Business.java
 * Adds up a series of prices and checks the current balance.
 *
 * @author Henry Wang
 */
import java.io.*;
import java.util.Scanner;

public class Business
{
	public static void main(String[] args) throws IOException
	{
		//Faster reading option.
		BufferedReader reader = new BufferedReader(new
				FileReader(new File("business.in")));
		System.setOut(new PrintStream(new File("business.out")));

		Scanner line = new Scanner(reader.readLine().trim());
		int num = line.nextInt();
		double total = line.nextDouble();
		for (int i = 0; i < num; i++)
			total -= Double.parseDouble(reader.readLine().trim());
		System.out.printf("$%.2f\n", total);
		System.out.close();
	}
}
