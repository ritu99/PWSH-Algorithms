import java.util.*;

public class Levenshtein {
    
    public static void main(String[] args)
    {
        System.out.println(leven("gumbo", "gambol"));
        System.out.println(leven("gambol", "gumbo"));
        System.out.println(leven("kitten", "sitting"));
        System.out.println(leven("saturday", "sunday"));
    }
    
    public static int leven(String a, String b)
    {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        
        for (int i=0; i <= b.length(); i++)
            dp[0][i] = i;
        
        for (int i=0; i <= a.length(); i++)
            dp[i][0] = i;
        
        for (int i=1; i <= a.length(); i++)
        {
            for (int j=1; j <= b.length(); j++)
            {
                int cost = a.charAt(i-1) == b.charAt(j-1) ? 0 : 1;
                
                dp[i][j] = Math.min(dp[i-1][j] + 1, Math.min(dp[i][j-1] + 1, dp[i-1][j-1] + cost));
            }
        }
        
        //System.out.println(Arrays.deepToString(dp));
        
        return dp[a.length()][b.length()];
    }
    
}
