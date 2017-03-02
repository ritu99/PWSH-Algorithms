
public class Knapsack {
    
    public static void main(String[] args)
    {
        System.out.println(knapsack01(new int[]{1, 3, 4, 5}, new int[]{1, 4, 5, 7}, 7));
    }
    
    /**
     * Knapsack where each item can be chosen 0 or 1 times
     * @param weights increasing order
     * @param values match up with weights
     * @param maxWeight
     * @return 
     */
    public static int knapsack01(int[] weights, int[] values, int maxWeight)
    {
        int[][] dp = new int[weights.length][maxWeight+1];
        
        // top row
        for (int i=0; i < maxWeight+1; i++)
            dp[0][i] = weights[0] <= i ? values[0] : 0;
        
        // rest
        for (int i=1; i < weights.length; i++)
        {
            for (int j=1; j < maxWeight+1; j++)
            {
                if (weights[i] > j)
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = Math.max(dp[i-1][j], values[i] + dp[i-1][j-weights[i]]);
            }
        }
        
        return dp[weights.length-1][maxWeight];
    }
    
}
