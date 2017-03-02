import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Bridges.java
 *
 * This problem requires understanding Euler's curcuit. The theorem goes
 * that with any unweighted graph G, all its nodes or vertices MUST have
 * an even number of connections or edges, in order for one to be able
 * to travel each edge exactly once, and return to his original spot.
 *
 * The trick to this problem is testing whether if all the bridges are
 * actually connected with each other. In order to test this, we created
 * an adjacency list, that tracks the sites that are adjacent to each other.
 * The idea is to move these adjacencies to the lowest site number as
 * possible. When we finish, we figure out a series of sites that are either
 * directly or implicitly adjacent to site 0 (since it is the minimum site).
 *
 * If some bridges were disconnected to site 0, then they would still remain
 * in the adjacency list of some site A and site B where A, B != 0
 *
 * @author Henry
 */
public class Bridges
{
	public static class Site {
		public HashSet<Integer> adjacency = new HashSet<>();
		public int minAdjacent = -1;

		@Override
		public String toString()
		{
			return adjacency.toString();
		}
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("bridges.in"));
		//System.setOut(new PrintStream(new File("bridges.out"))); //Comment this line to print output to console

		int t = in.nextInt();

		for (int i = 0; i < t; i++)
		{
			int n = in.nextInt();
			int x = in.nextInt();

			int sites[] = new int[n];
			Site[] adjs = new Site[n];
			for (int j = 0; j < n; j++)
			{
				adjs[j] = new Site();
				adjs[j].minAdjacent = j;
			}

			//Read in bridges.
			for (int j = 0; j < x; j++)
			{
				int a = in.nextInt();
				int b = in.nextInt();

				flattenAdjacency(adjs, a, b);

				sites[a]++;
				sites[b]++;
			}

			boolean circuit = true;
			for (int site : sites)
			{
				if (site % 2 != 0)
				{
					circuit = false;
					break;
				}
			}

			if (circuit)
			{
				for (int j = 1; j < adjs.length; j++)
				{
					if (!adjs[j].adjacency.isEmpty())
					{
						circuit = false;
						break;
					}
				}
			}

			System.out.println(circuit ? "In your face!" : "Oops I'm Lost.");
		}
	}

	/**
	 * Adds new bridge connection to adjacency list. This tries to flatten
	 * all adjacencies (direct or indirect) into the lowest indexed site.
	 *
	 * Pre-condition: adjs MUST be in its optimal flat adjacency.
	 * Post-condition: adjs will be in its optimal flat adjacency.
	 *
	 * @param adjs current state of adjacencies
	 * @param a bridge connection a
	 * @param b bridge connection b
	 */
	private static void flattenAdjacency(Site[] adjs, int a, int b)
	{
		int min = Math.min(adjs[a].minAdjacent,
				adjs[b].minAdjacent);
		HashSet<Integer> tmp = new HashSet<>();

		adjs[a].adjacency.add(b);
		adjs[b].adjacency.add(a);

		//Flatten the parents
		int[] flatten = {a, b};
		for (int node : flatten)
		{
			while (min != node)
			{
				int parent = adjs[node].minAdjacent;
				adjs[node].minAdjacent = min;
				adjs[min].adjacency.add(node);
				tmp.addAll(adjs[node].adjacency);
				adjs[node].adjacency.clear();
				node = parent;
			}
		}

		//Flatten the adjacencies
		adjs[min].adjacency.addAll(tmp);
		for (Integer site : tmp)
			adjs[site].minAdjacent = min;
	}
}
