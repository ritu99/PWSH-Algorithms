
/**
 * Decimals.java
 * This reads a super decimal (a number with multiple decimal
 * points) and converts it into a regular decimal number.
 * This also contains a second option, which generates the
 * input cases to test.
 *
 * @author Henry Wang
 */
import java.io.*;

public class Decimals
{

	/**
	 * Generates test cases
	 */
	public static void generateTestCase()
	{
		//Have 100 test cases
		for (int i = 0; i < 100; i++)
		{
			//generate super decimals with n groupings of range [2, 9999].
			int groups = (int)(squareRandom() * 9_998) + 2;
			for (int j = 0; j < groups; j++)
			{
				//generate numbers of range [1, 9999].
				int num = (int)(squareRandom() * 9_999) + 1;

				if (j > 0)
					System.out.print(".");
				System.out.print(num);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException
	{
		if (args.length > 0 && (args[0].equalsIgnoreCase("generate")
				|| args[0].equalsIgnoreCase("g")))
		{
			// Use try-with-resource, so that the reader would
			// automatically close once it is done
			try (PrintStream write = new PrintStream
					(new File("decimal.in")))
			{
				System.setOut(write);
				generateTestCase();
			}
		}
		else
		{
			//Faster reading option.
			BufferedReader reader = new BufferedReader(new
					FileReader(new File("decimal.in")));
			System.setOut(new PrintStream(new File("decimal.out")));

			int cases = Integer.parseInt(reader.readLine().trim());
			for (int i = 0; i < cases; i++)
			{
				// Read superdecimal, splitting it between the
				// decimal points
				String[] numbers = reader.readLine().split("\\.");

				// Add each decimal part together.
				double result = 0;
				for (int j = 0; j < numbers.length - 1; j++)
				{
					double num = Double.parseDouble(numbers[j] + "."
							+ numbers[j + 1]);
					result += num;
				}

				System.out.printf("%.5f%n", result);
			}
			System.out.close();
		}
	}

	/**
	 * Obtains a random value more weighted towards zero rather than
	 * with a uniform spread.
	 * @return the random value from 0.0 to 1.0
	 */
	public static double squareRandom()
	{
		double val = Math.random();
		return val * val;
	}
}
