/**
 * Forces.java
 * Sums up a series of forces and finds the direction and
 * magnitude of the sum of forces.
 *
 * @author Henry Wang
 */

import java.io.*;
import java.util.Scanner;

public class Forces
{
	public static void main(String[] args) throws IOException
	{
		//Faster reading option.
		BufferedReader reader = new BufferedReader(new
				FileReader(new File("forces.in")));
		System.setOut(new PrintStream(new File("forces.out")));

		int num = Integer.parseInt(reader.readLine().trim());
		double x = 0;
		double y = 0;
		for (int i = 0; i < num; i++)
		{
			Scanner line = new Scanner(reader.readLine().trim());
			int mag = line.nextInt();
			int ang = line.nextInt();

			x += mag * Math.cos(Math.toRadians(ang));
			y += mag * Math.sin(Math.toRadians(ang));

			//Such a lazy person
			double totalMag = Math.hypot(x, y);
			double totalAng = Math.toDegrees(Math.atan2(y, x));

			System.out.printf("%.3f @ %.2f degrees\n", totalMag, totalAng);
		}
		System.out.close();
	}
}
