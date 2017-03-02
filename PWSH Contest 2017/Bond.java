import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class Bond
{

	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("bond.in"));
		System.setOut(new PrintStream(new File("bond.out")));

		HashMap<String, Integer> cmpds = new HashMap<>();
		int n = in.nextInt();
		for (int i = 0; i < n; i++)
		{
			cmpds.put(in.next(), in.nextInt());
		}

		n = in.nextInt();
		for (int i = 0; i < n; i++)
		{
			int react = in.nextInt();
			int prod = in.nextInt();
			int reactMass = 0;
			int prodMass = 0;

			for (int j = 0; j < react; j++)
				reactMass += in.nextInt() * cmpds.get(in.next());
			in.next();
			for (int j = 0; j < prod; j++)
			{
				prodMass += in.nextInt() * cmpds.get(in.next());
			}

			System.out.println(prodMass == reactMass ? "Possible candidate" : "Not possible");
		}
	}

}
