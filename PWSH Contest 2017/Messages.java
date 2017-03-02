import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Messages
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("messages.in"));
		System.setOut(new PrintStream(new File("messages.out")));
		
		int n = in.nextInt();
		for (int i = 0; i < n; i++)
		{
			Point p1 = new Point(in.nextInt(), in.nextInt());
			Point p2 = new Point(in.nextInt(), in.nextInt());
			
			int maxDist = in.nextInt();
			int speed = in.nextInt();
			
			double dist = p1.distance(p2);
			if (dist > maxDist)
				System.out.println("His message cannot be transmitted.");
			else
			{
				int time = (int)Math.round(dist / speed);
				System.out.println("It will take " + time + " seconds for his message to transmit.");
			}
		}
		
	}
}
