import java.io.*;
import java.util.*;

public class Locks{


  static long[][] pascals = new long[102][102];
  public static void main(String[] args) throws IOException{
    Scanner in = new Scanner(new File("locks.in"));

    int C = in.nextInt();


    generatePascalsTriangle();

    for(int ca = 0; ca < C; ca++){
      int r = in.nextInt();
      int c = in.nextInt();

      System.out.println(pascals[r+1][c]);
    }

  }

  public static void generatePascalsTriangle(){
    for(int i = 0; i < 102; i++){
      for(int j = 0; j < i; j++){
        if(i+1 == j) break;
        if(i == j)
          pascals[i][j] = 1;
        if(j == 0)
          pascals[i][j] = 1;
        else
          pascals[i][j] = (pascals[i-1][j-1] + pascals[i-1][j]) % 1_000_007;
      }
    }
  }
}
