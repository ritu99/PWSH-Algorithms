import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Roads
{
	public static class Coord
	{
		public int row;
		public int col;
		public int steps;
		
		public Coord(int row, int col, int steps)
		{
			super();
			this.row = row;
			this.col = col;
			this.steps = steps;
		}
		
		
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("roads.in"));
		System.setOut(new PrintStream(new File("roads.out"))); //Comment this line to print to console.
		int tests = in.nextInt();
		for (int i = 0; i < tests; i++)
		{
			int h = in.nextInt();
			int rows = in.nextInt();
			int cols = in.nextInt();
			in.nextLine();
			
			int[][] weight = new int[rows][cols];
			ArrayList<Coord> towns = new ArrayList<>();
			for (int r = 0; r < rows; r++)
			{
				char[] line = in.nextLine().toCharArray();
				for (int c = 0; c < cols; c++)
				{
					if (line[c] == 'T')
						towns.add(new Coord(r, c, 0));
					else
						weight[r][c] = Math.abs(h - (line[c] - '0'));
				}
			}
			int min = bfs(weight, towns.get(0), towns.get(1));
			System.out.println(min);
		}
	}
	
	public static int bfs(int[][] weight, Coord start, Coord finish)
	{
		Queue<Coord> points = new LinkedList<>();
		
		int[][] min = new int[weight.length][weight[0].length];
		for (int[] row : min)
			Arrays.fill(row, -1);
		
		int minPath = -1;
		points.add(start);
		while (!points.isEmpty())
		{
			Coord pt = points.remove();
			int row = pt.row;
			int col = pt.col;
			if (row < 0 || row >= weight.length || col < 0 || col >= weight[0].length)
				continue;
			
			int newSteps = pt.steps + weight[row][col];
			
			if (min[row][col] != -1 && min[row][col] <= newSteps)
				continue; //We already found a more optimal path.
			min[row][col] = newSteps;
			
			if (row == finish.row && col == finish.col) 
			{
				if (minPath == -1 || minPath > newSteps)
					minPath = newSteps;
				continue; //Reached exit. Don't need to go further in this path.
			}
			
			//Iterate through all four directions.
			points.add(new Coord(row + 1, col, newSteps));
			points.add(new Coord(row - 1, col, newSteps));
			points.add(new Coord(row, col + 1, newSteps));
			points.add(new Coord(row, col - 1, newSteps));
		}
		return minPath;
	}
}
