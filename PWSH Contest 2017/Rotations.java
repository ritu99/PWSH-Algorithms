/**
 * Rotations.java
 * Encrypts words (uppercase) by positional rotation: rotate by k
 * for first letter, k+1 for second, k+3 for third, etc.
 *
 * @author Henry Wang
 */
import java.io.*;
import java.util.Scanner;

public class Rotations
{
	public static void main(String[] args) throws IOException
	{
		//Faster reading option.
		BufferedReader reader = new BufferedReader(new
				FileReader(new File("rotations.in")));
		System.setOut(new PrintStream(new File("rotations.out")));

		int num = Integer.parseInt(reader.readLine().trim());
		for (int i = 0; i < num; i++)
		{
			Scanner line = new Scanner(reader.readLine());

			//Read key (rotation amount)
			int k = line.nextInt();

			//Rotate character by character and
			//increment key by one each time (mod 26)
			String word = line.next();
			char[] encrypt = new char[word.length()];
			for (int j = 0; j < word.length(); j++)
			{
				encrypt[j] = rotate(word.charAt(j), k);
				k++;
				while (k >= 26)
					k -= 26;
			}

			System.out.println(new String(encrypt));
		}
		System.out.close();
	}

	/**
	 * Rotates a character (between A to Z)
	 * @param letter the letter to rotate
	 * @param key the amount of rotation
	 * @return the rotated character.
	 */
	public static char rotate(char letter, int key)
	{
		int ind = letter - 'A';
		ind += key;

		//Get the index to be between [0, 26)
		while (ind >= 26)
			ind -= 26;
		while (ind < 0)
			ind += 26;

		return (char)(ind + 'A');
	}
}
