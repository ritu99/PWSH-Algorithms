import java.util.*;

/**
 * Description
 *
 * @author creativitRy
 *         Date: 12/17/2016.
 */

public class CtryUtil
{
	static
	{
		System.out.println("[[WARNING: DEBUGGING]]\n");
	}
	
	/**
	 * Concats all with a space in between
	 *
	 * @param objects stuff to concat
	 * @return formatted string
	 */
	public static String toStringAll(Object... objects)
	{
		if (objects.length == 0)
			return "";
		String s = "";
		for (Object object : objects)
		{
			if (object == null)
				s += " null";
			else
				s += " " + object;
		}
		
		return s.substring(1);
	}
	
	/**
	 * for printing 2D int arrays in a rectangle with a space in between all ints
	 *
	 * @param arr 2D int array
	 * @return formatted 2D int array
	 */
	public static String deepToString(int[][] arr)
	{
		String s = "";
		for (int[] a : arr)
		{
			for (int b : a)
			{
				s += String.format("%-3d", b);
			}
			s += "\n";
		}
		
		return s;
	}
	
	/**
	 * for printing 2D char arrays in a rectangle
	 *
	 * @param arr 2D char array
	 * @return formatted 2D char array
	 */
	public static String deepToString(char[][] arr)
	{
		String s = "";
		for (char[] a : arr)
		{
			for (char b : a)
			{
				s += b;
			}
			s += "\n";
		}
		
		return s;
	}
	
	/**
	 * for printing 2D boolean arrays in a rectangle
	 *
	 * @param arr       2D boolean array
	 * @param charFalse char to print if false
	 * @param charTrue  char to print if true
	 * @return formatted 2D boolean array
	 */
	public static String deepToString(boolean[][] arr, char charFalse, char charTrue)
	{
		String s = "";
		for (boolean[] a : arr)
		{
			for (boolean b : a)
			{
				s += b ? charTrue : charFalse;
			}
			s += "\n";
		}
		
		return s;
	}
	
	/**
	 * generates random uppercase alphabet
	 *
	 * @return random uppercase alphabet
	 */
	public static char randomAlphabet()
	{
		return (char) (Math.random() * ('Z' - 'A' + 1) + 'A');
	}
	
	
}
