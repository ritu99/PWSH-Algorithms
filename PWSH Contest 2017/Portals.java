import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Portals
{
	public static class Coord
	{
		public int maze;
		public int row;
		public int col;
		public int steps;
		public boolean normal = true; //whether if move was automatic or normal
		
		public Coord(int maze, int row, int col, int steps)
		{
			super();
			this.maze = maze;
			this.row = row;
			this.col = col;
			this.steps = steps;
		}
		
		public Coord(Coord other, int steps)
		{
			this.maze = other.maze;
			this.row = other.row;
			this.col = other.col;
			this.steps = steps;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + col;
			result = prime * result + maze;
			result = prime * result + row;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coord other = (Coord) obj;
			if (col != other.col)
				return false;
			if (maze != other.maze)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
		
		public String toString()
		{
			return "M" + maze + "(" + row + ", " + col + ")" + " " + steps;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("portals.in"));
		//System.setOut(new PrintStream(new File("portals.out"))); //Comment this line to print to console.
		int tests = in.nextInt();
		for (int i = 0; i < tests; i++)
		{
			int levels = in.nextInt();
			int rows = in.nextInt();
			int cols = in.nextInt();
			
			char[][][] maze = new char[levels][rows][];
			Coord[][] teleports = new Coord[10][2];
			Coord start = null;
			for (int m = 0; m < levels; m++)
			{
				in.nextLine();
				for (int r = 0; r < rows; r++)
				{
					maze[m][r] = in.nextLine().toCharArray();
					for (int c = 0; c < cols; c++)
					{
						if (maze[m][r][c] == 'S')
							start = new Coord(m, r, c, 0);
						else if (Character.isDigit(maze[m][r][c]))
						{
							int num = maze[m][r][c] - '0';
							if (teleports[num][0] == null)
								teleports[num][0] = new Coord(m, r, c, 0);
							else
								teleports[num][1] = new Coord(m, r, c, 0);
						}
					}
				}
			}
			int min = bfs(maze, start, teleports);
			if (min == -1)
                System.out.println("Impossible, Cheater!");
			else
                System.out.println(min);
		}
	}
	
	public static int bfs(char[][][] maze, Coord start, Coord[][] teleports)
	{
		Queue<Coord> points = new LinkedList<>();
		
		int[][][] min = new int[maze.length][maze[0].length][maze[0][0].length];
		for (int[][] minMaze : min)
			for (int[] row : minMaze)
				Arrays.fill(row, -1);
		
		int minPath = -1;
		points.add(start);
		while (!points.isEmpty())
		{
			Coord pt = points.remove();
			int mInd = pt.maze;
			int row = pt.row;
			int col = pt.col;
			if (row < 0 || row >= maze[0].length || col < 0 || col >= maze[0][0].length)
				continue;
			
			if (maze[mInd][row][col] == 'X')
				continue;
			
			if (Character.isDigit(maze[mInd][row][col]) && pt.normal)
			{
				int num = maze[mInd][row][col] - '0';
				Coord move;
				if (teleports[num][0].equals(pt))
					move = new Coord(teleports[num][1], pt.steps);
				else
					move = new Coord(teleports[num][0], pt.steps);
				move.normal = false;
				points.add(move);
				min[mInd][row][col] = 0;
				continue;
			}
			
			int newSteps = pt.steps + 1;
			if (min[mInd][row][col] != -1 && min[mInd][row][col] <= newSteps)
				continue; //We already found a more optimal path.
			min[mInd][row][col] = newSteps;
			
			if (maze[mInd][row][col] == 'F') 
			{
				if (minPath == -1 || minPath > newSteps)
					minPath = pt.steps;
				continue; //Reached exit. Don't need to go further in this path.
			}
			
			//Iterate through all four directions.
			points.add(new Coord(mInd, row + 1, col, newSteps));
			points.add(new Coord(mInd, row - 1, col, newSteps));
			points.add(new Coord(mInd, row, col + 1, newSteps));
			points.add(new Coord(mInd, row, col - 1, newSteps));
		}
		return minPath;
	}
}

