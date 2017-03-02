/**
 * Pyramid.java
 * Encrypts a message using the pyramid cipher.
 *
 * @author Henry Wang
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Pyramid
{
	public static void main(String[] args) throws IOException
	{
		//Faster reading option.
		BufferedReader reader = new BufferedReader(new
				FileReader(new File("pyramid.in")));
		System.setOut(new PrintStream(new File("pyramid.out")));
		
		int num = Integer.parseInt(reader.readLine().trim());
		for (int i = 0; i < num; i++)
		{
			String msg = reader.readLine().trim();
			int n = calcPyramidSize(msg.length());
			char[][] pyramid = new char[n][];
			
			int ele = 0;
			for (int r = 0; r < n; r++)
			{
				pyramid[r] = new char[r + 1];
				for (int c = 0; c <= r; c++)
				{
					pyramid[r][c] = msg.charAt(ele);
					ele++;
				}
			}
			
			int pos = 0;
			char[] encrypt = new char[n * (n + 1) / 2];
			for (int c = 0; c < n; c++)
			{
				for (int r = c; r < n; r++)
				{
					encrypt[pos] = pyramid[r][c];
					pos++;
				}
			}
			System.out.println(new String(encrypt) + msg.substring(ele));
		}
		System.out.close();
	}
	
	/**
	 * Calculates the size of the pyramid for encrypting
	 * @param length length of text to encrypt
	 * @return the size of pyramid.
	 */
	private static int calcPyramidSize(int length)
	{
		double n = (-1 + Math.sqrt(1 + 8 * length)) / 2;
		return (int)n; //Smallest value.
	}
}
