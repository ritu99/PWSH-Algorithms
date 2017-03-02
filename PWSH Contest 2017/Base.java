import java.io.*;
import java.util.*;

public class Base{

  public static void main(String[] args) throws IOException{
    Scanner in = new Scanner(new File("base.in"));

    int C = in.nextInt();

    for(int c = 0; c < C; c++){
      System.out.println(Integer.parseInt("" + in.nextInt(), in.nextInt()) % 12);
    }
  }
}
