import java.util.*;

public class Coins {
    
    public static void main(String[] args)
    {
        System.out.println("Intuitive:");
        System.out.println(intuitive(new int[]{1, 5, 10, 25}, 48));
        System.out.println(intuitive(new int[]{1, 2, 5, 10}, 25));
        
        System.out.println();
        
        System.out.println("Unintuitive:");
        System.out.println(unintuitive(new int[]{1, 5, 10, 25}, 48));
        System.out.println(unintuitive(new int[]{1, 2, 5, 10}, 25));
        System.out.println(unintuitive(new int[]{1, 5, 10, 24, 25}, 48));
        System.out.println(unintuitive(new int[]{1, 10, 25}, 40));
        
        System.out.println();

        System.out.println("Special:")
        System.out.println(special(new int[]{1, 5, 10, 25, -7}, 43));
        System.out.println(special(new int[]{1, 7, 11, 13, 17, -4, 2, 15, 35, -7, -3, 10, -10}, 5));
    }
    
    // assume coins is sorted min->max
    public static int intuitive(int[] coins, int value)
    {
        int count = 0;
        int left = value;
        
        for (int i=coins.length-1; i >=0; i--)
        {
            if (coins[i] <= left)
            {
                int times = left / coins[i];
                left -= times * coins[i];
                count += times;
            }
        }
        
        return count;
    }
    
    public static int unintuitive(int[] coins, int value)
    {
        int[] dp = new int[value+1];
        
        for (int i = 1; i <= value; i++)
        {
            int min = Integer.MAX_VALUE;
            
            for (int j = 0; j < coins.length; j++)
                if (i - coins[j] > -1)
                    min = Math.min(min, dp[i - coins[j]] + 1);
            
            dp[i] = min;
        }
        
        return dp[value];
    }
    
    public static int special(int[] coins, int value)
    {
        Arrays.sort(coins);
        
        int[] dp = new int[1_000_000];
        
        for (int i=1; i < dp.length; i++)
        {
            int min = Integer.MAX_VALUE;
            
            for (int j=0; j < coins.length; j++)
                if (coins[j] > 0 && i - coins[j] > -1)
                    min = Math.min(min, dp[i-coins[j]] + 1);
            
            dp[i] = min;
        }
        
        ArrayList<Integer> negs = new ArrayList<>();
        for (int x : coins)
            if (x < 0)
                negs.add(x);
        
        // probably wrong; use lowest magnitude negative from the end
        for (int i=dp.length + negs.get(0)-1; i >= 0; i--)
        {
            int min = dp[i];
            
            for (int j=0; j < negs.size(); j++)
                min = Math.min(min, dp[i - negs.get(j)] + 1);
            
            dp[i] = min;
        }
        
        return dp[value];
    }
    
}
