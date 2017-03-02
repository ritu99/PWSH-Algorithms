/**
 * Allies.java
 * Reads a list of names and determines whether or not if 
 * these people are captured.
 *
 * @author Henry Wang
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class Allies
{
	private static final String[] NAMES = {"General Gogol", "Marc-Ange Draco", "Sheriff J.W. Pepper", "Tiger Tanaka", 
			"Jack Wade", "Valentin Dmitrovich Zukovsky"};
	
	public static void main(String[] args) throws IOException
	{
		//Faster reading option.
		BufferedReader reader = new BufferedReader(new
				FileReader(new File("allies.in")));
		System.setOut(new PrintStream(new File("allies.out")));

		Set<String> names = new HashSet<>();
		for (String n : NAMES)
			names.add(n);
		
		int num = Integer.parseInt(reader.readLine().trim());
		for (int i = 0; i < num; i++)
		{
			String name = reader.readLine().trim();
			if (names.contains(name))
				System.out.println(name + " was captured!");
		}
		
		System.out.close();
	}
}
