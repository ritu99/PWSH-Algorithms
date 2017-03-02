
import java.util.*;


public class PascalsTriangle {
    
    public static void main(String[] args)
    {
        int[][] tri = makeTriangle(100);
        for (int[] row : tri)
            System.out.println(Arrays.toString(row));
        
        System.out.println();
        
        // be careful about overflow
        System.out.println(combinations(tri, 7, 2));
        System.out.println(combinations(tri, 17, 10));
        System.out.println(combinations(tri, 70, 65));
    }
    
    public static int[][] makeTriangle(int height)
    {
        int[][] tri = new int[height][];
        
        tri[0] = new int[]{1};
        tri[1] = new int[]{1,1};
        for (int i=2; i < height; i++)
        {
            tri[i] = new int[i+1];
            tri[i][0] = 1;
            tri[i][i] = 1;
            for (int j=1; j < i; j++)
                tri[i][j] = tri[i-1][j-1] + tri[i-1][j];
        }
        
        return tri;
    }
    
    public static int combinations(int[][] triangle, int a, int b)
    {
        return triangle[a][b];
    }
    
}
