import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Bases2048.java
 *
 * The main problem tested with this task is with converting bases.
 * Otherwise this is a pretty straight-forward problem. It can be noted
 * that we have four different directions to move (up, down, left, right),
 * but since we are only concerned about the number of combinations, we
 * could notice that up and down are the same thing, and left and right
 * are the same thing.
 *
 * Another thing to note is that we cannot use parseInt due to the fact
 * that we have utilize both upper-case and lower-case letters and
 * numbers to determine the digits.
 *
 * @author Henry
 *
 */
public class Bases2048
{

	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("2048.in"));
		System.setOut(new PrintStream(new File("2048.out"))); //Comment this line to print output to console

		int t = in.nextInt();
		for (int cases = 0; cases < t; cases++)
		{
			//Read grid
			int[][] grid = new int[4][4];
			for (int r = 0; r < grid.length; r++)
			{
				for (int c = 0; c < grid[r].length; c++)
				{
					String num = in.next();
					if (num.equals("-")) //Blank cell.
						grid[r][c] = -1;
					else
						grid[r][c] = parseNumber(num, in.nextInt());
				}
			}

			int prev = -1;

			//Combos going to the right
			int rightCombos = 0;
			for (int[] row : grid)
			{
				for (int element : row)
				{
					if (element != -1)
					{
						if (prev == element)
						{
							rightCombos++;
							prev = -1; //Can't match three tiles together.
						}
						else //No match.
							prev = element;
					}
				}
				prev = -1;
			}

			//Combos going to the bottom.
			int bottomCombos = 0;
			for (int c = 0; c < grid[0].length; c++)
			{
				for (int[] element : grid)
				{
					if (element[c] != -1)
					{
						if (prev == element[c])
						{
							bottomCombos++;
							prev = -1; //Can't match three tiles together.
						}
						else //No match.
							prev = element[c];
					}
				}
				prev = -1;
			}

			System.out.println(Math.max(rightCombos, bottomCombos));
		}
	}

	/**
	 * Parses a string representation number in base B
	 * and converts into an integer.
	 * @param number string representation of number
	 * @param base the base B of the number to convert
	 * @return integer representation of number
	 */
	public static int parseNumber(String number, int base)
	{
		byte[] digits = new byte[number.length()];

		//Parse into an array of digits.
		//Notice that we are putting digits in reverse.
		int digitNum = digits.length - 1;
		for (char dig : number.toCharArray())
		{
			if (dig >= '0' && dig <= '9')
				digits[digitNum] = (byte)(dig - '0');
			else if (dig >= 'A' && dig <= 'Z')
				digits[digitNum] = (byte)(dig - 'A' + 10);
			else //if (dig >= 'a' && dig <= 'z')
				digits[digitNum] = (byte)(dig - 'a' + 36);
			digitNum--;
		}

		//Convert to regular numbers
		int place = 1;
		int base10 = 0;
		for (byte element : digits)
		{
			base10 += element * place;
			place *= base;
		}
		return base10;
	}

}
