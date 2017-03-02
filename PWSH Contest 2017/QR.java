import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * QR.java
 *
 * Parses a QR matrix of 0s and 1s, and outputs the string.
 *
 *
 * Some important points for the QR code:
 *
 * 1. Each two bits of each block can be grouped together
 * into a "bit-pair", and always, the less significant
 * bit (LSB) in this "bit-pair" is on the adjacent left
 * to the more significant bit (MSB).
 *
 * 2. There are basically two orientations: UP and DOWN.
 * The turns can actually be defined as 4 bits UP and 4
 * bits DOWN, or 4 bits DOWN and 4 bits UP. This should
 * also take into account that a few turnarounds,
 * (example in between E3 and E4) there is a jump.
 *
 * 3. Make sure to skip over the dotted lines on the row
 * and columns.
 *
 * 4. For searching that QR marker, you would have to do
 * a double nested for-loop so to iterate each point in
 * reading order (left to right, then top to bottom).
 * This way, we can ensure that the marker we get is
 * guaranteed to be the top-left one. Also note that the
 * index of the row/column must be in the range
 * [0, ROWS - QR_SIZE], because beyond that point, the
 * resulting QR would overflow out of our data.
 *
 * 5. Make sure to honor the mask row % 2 == 0. This could
 * easily be done by this if statement (where row and col
 * defines the position of the square to set, and bit is
 * the value to set):
 *
 *     if (row % 2 == 0)
 *       qr[row][col] = !bit;
 *     else
 *       qr[row][col] = bit;
 *
 * It appears that this code can be simplified to:
 *
 *     qr[row][col] = bit ^ (row % 2 == 0);
 *
 * By using the XOR (eXclusive OR) operation.
 * It is used in this case as a conditional inverter,
 * when the row is even, the above statement simplifies to:
 *
 *     qr[row][col] = bit ^ true;
 *
 * And essentially serves to invert the boolean bit, i.e.
 * when bit = false, then qr[row][col] = true, and vice versa.
 *
 * @author Henry Wang
 */

public class QR
{
	public static class Coord {
		int row;
		int col;

		public Coord(int row, int col)
		{
			super();
			this.row = row;
			this.col = col;
		}
	}

	public static class Range {
		int min;
		int max;

		public Range(int min, int max)
		{
			super();
			this.min = min;
			this.max = max;
		}


	}

	private static final int UP_ORIENT = 0;
	private static final int DOWN_ORIENT = 1;

	public static final int MARKER_SIZE = 7;

	public static final int QR_SIZE = 21;

	public static final char[][] MARKER = {
			"1111111".toCharArray(),
			"1000001".toCharArray(),
			"1011101".toCharArray(),
			"1011101".toCharArray(),
			"1011101".toCharArray(),
			"1000001".toCharArray(),
			"1111111".toCharArray()
	};


	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("qr.in"));
		System.setOut(new PrintStream(new File("qr.out"))); //Comment this line to print output to console

		int n = in.nextInt();

		for (int i = 0; i < n; i++)
		{
			int rows = in.nextInt();
			int cols = in.nextInt();
			in.nextLine();

			//Read raw data
			char[][] data = new char[rows][];
			for (int j = 0; j < rows; j++)
				data[j] = in.nextLine().toCharArray();

			//Find top right marker.
			boolean found = false;
			boolean[][] qr = new boolean[QR_SIZE][QR_SIZE];
			for (int r = 0; r <= rows - QR_SIZE; r++)
			{
				for (int c = 0; c <= cols - QR_SIZE; c++)
				{
					if (matchesMarker(data, r, c))
					{
						found = true;
						for (int dr = 0; dr < QR_SIZE; dr++)
							for (int dc = 0; dc < QR_SIZE; dc++)
								qr[dr][dc] = data[r + dr][c + dc] == '1';
					}
				}
			}
			if (!found)
				throw new IllegalArgumentException("Cannot find QR marker");

			//Decode QR
			System.out.println(readQR(qr));
		}
	}

	/**
	 * Checks for whether if data matches marker.
	 *
	 * @param data data that the marker is in
	 * @param r row to search from
	 * @param c column to search from
	 * @return true if this matches QR marker, false otherwise.
	 */
	public static boolean matchesMarker(char[][] data, int r, int c)
	{
		//Check if marker would go out of bounds
		if (MARKER_SIZE + r > data.length || MARKER_SIZE + c > data[0].length)
			return false;
		for (int mr = 0; mr < MARKER_SIZE; mr++)
			for (int mc = 0; mc < MARKER_SIZE; mc++)
				if (data[r + mr][c + mc] != MARKER[mr][mc])
					return false;

		return true;
	}

	/**
	 * Reads one bit of the QR block, accounting for
	 * masking the bits on even rows.
	 * @param qr qr block
	 * @param row row to read from
	 * @param col column to read from
	 * @return the boolean bit of that coordinate
	 */
	public static boolean readBit(boolean[][] qr, int row, int col)
	{
		return qr[row][col] ^ (row % 2 == 0);
	}

	/**
	 * Reads one data byte or block
	 * @param qr qr matrix array to read from
	 * @param loc location (row, column) of the block, defined by the MSB
	 * @param blockSize either 4 bits or 8 bits size
	 * @param orient numeric orientation (UP, DOWN)
	 * @return character value read from the data block
	 */
	public static char readBlock(boolean[][] qr, Coord loc, int blockSize, int orient)
	{
		if (blockSize != 8 && blockSize != 4)
			throw new IllegalArgumentException("Block size must be 4 or 8");
		char data = 0;
		for (int i = 0; i < blockSize / 2; i++)
		{
			data <<= 2;
			data |= (readBit(qr, loc.row, loc.col) ? 2 : 0) |
					(readBit(qr, loc.row, loc.col - 1) ? 1 : 0);

			if (orient == UP_ORIENT)
				loc.row--;
			else
				loc.row++;
		}

		return data;
	}

	/**
	 * Reads a QR matrix.
	 * @param qr the matrix to read from
	 * @return the string that this qr represents.
	 */
	public static String readQR(boolean[][] qr)
	{
		Coord loc = new Coord(QR_SIZE - 1, QR_SIZE - 1); //Start from bottom right.
		readBlock(qr, loc, 4, 0); //Encoding
		int len = readBlock(qr, loc, 8, 0);

		char[] text = new char[len];
		int orient = UP_ORIENT;
		for (int i = 0; i < len; i++)
		{
			checkDash(loc, orient);

			//Check for out of bounds.
			Range rowRange = rowRangeAt(loc.col);
			if (orient == UP_ORIENT)
			{
				if (loc.row < rowRange.min)
				{ //Changing orientation directly to DOWN
					loc.row = rowRange.min;
					loc.col -= 2;
					checkDash(loc, orient);
					orient = DOWN_ORIENT;
				}
				else if (loc.row - 1 == rowRange.min)
				{ //Read a 4 block up, then a 4 block down
					int c = readBlock(qr, loc, 4, orient) << 4;
					orient = DOWN_ORIENT;
					loc.col -= 2;
					checkDash(loc, orient);
					loc.row = rowRangeAt(loc.col).min;
					c |= readBlock(qr, loc, 4, orient);
					text[i] = (char)c;
					continue;
				}
			}
			else //if (orient == DOWN_ORIENT)
			{
				if (loc.row > rowRange.max)
				{
					loc.row = rowRange.max;
					loc.col -= 2;
					checkDash(loc, orient);
					orient = UP_ORIENT;
				}
				else if (loc.row + 1 == rowRange.max)
				{ //Read a 4 block down, a 4 block up
					int c = readBlock(qr, loc, 4, orient) << 4;
					orient = UP_ORIENT;
					loc.col -= 2;
					checkDash(loc, orient);
					loc.row = rowRangeAt(loc.col).max;
					c |= readBlock(qr, loc, 4, orient);
					text[i] = (char)c;
					continue;
				}
			}

			//Read a character
			text[i] = readBlock(qr, loc, 8, orient);
		}

		return new String(text);
	}

	/**
	 * Obtains the range of possible rows given the current column
	 * @param col column to test on
	 * @return the inclusive range of rows.
	 */
	public static Range rowRangeAt(int col)
	{
		if (col >= QR_SIZE - (MARKER_SIZE + 1))
			return new Range(MARKER_SIZE + 2, QR_SIZE - 1);
		else if (col < MARKER_SIZE + 2)
			return new Range(MARKER_SIZE + 2, MARKER_SIZE + 5);
		else
			return new Range(0, QR_SIZE - 1);
	}

	/**
	 * Checks for the dashed marks, and offsets it.
	 * @param loc location to test.
	 * @param orient direction to move in.
	 */
	private static void checkDash(Coord loc, int orient) {
		if (loc.row == MARKER_SIZE - 1)
			loc.row += orient == UP_ORIENT ? -1 : 1;
		if (loc.col == MARKER_SIZE - 1)
			loc.col--;
	}
}
