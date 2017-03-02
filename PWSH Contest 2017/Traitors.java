/**
 * Traitors.java
 * Identifies whether if a list of secret codes are, in fact, valid.
 *
 * @author Henry Wang
 */
import java.io.*;
import java.util.Scanner;

public class Traitors
{
	public static void main(String[] args) throws IOException
	{
		//Faster reading option.
		BufferedReader reader = new BufferedReader(new
				FileReader(new File("traitors.in")));
		System.setOut(new PrintStream(new File("traitors.out")));

		int num = Integer.parseInt(reader.readLine().trim());
		for (int i = 0; i < num; i++)
		{
			Scanner line = new Scanner(reader.readLine());
			int val = line.nextInt();

			if (val > 0 && val <=  99 && (val % 3 == 0 || val % 5 == 0 || val % 7 == 0))
				System.out.println("AGENT");
			else
				System.out.println("TRAITOR");
		}
		System.out.close();
	}
}
