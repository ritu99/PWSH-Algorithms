import java.util.*;
import java.io.*;


//We write MP as short for moneypenny
public class Change {
    
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(new File("change.in"));
        
        int cases = in.nextInt();
        
        for (int c=0; c < cases; c++)
        {
            int value = in.nextInt();
            int numMPCoins = in.nextInt();
            int numCashierCoins = in.nextInt();
            
            int[] posCoins = new int[numMPCoins];
            int[] negCoins = new int[numCashierCoins];
            
            // Getting MP's coins, which are considered positive
            for (int i=0; i < numMPCoins; i++)
                posCoins[i] = in.nextInt();
            
            // Getting cashier's coins, which are considered negative because
            // they are given back to MP
            for (int i=0; i < numCashierCoins; i++)
                negCoins[i] = in.nextInt();
            
            System.out.println(minCoins(posCoins, negCoins, value));
        }
    }
    
    /**
     * Calculates the minimum number coins needed to be exchanged in order
     * to reach a certain value.
     * @param posCoins the positive coin set (MP's coins)
     * @param negCoins the negative coin set (cashier's coins)
     * @param value how much money MP is trying to reach
     * @return the minimum coins exchanged to reach value
     */
    public static int minCoins(int[] posCoins, int[] negCoins, int value)
    {   
        // Dynamic Programming algorithm used (unintuitive coins): 
        // http://algorithms.tutorialhorizon.com/dynamic-programming-coin-change-problem/
        
        // Array for storing minimum coins needed to reach 0...dp.length
        // Array is of arbitrarily large number for calculating negative coins
        int[] dp = new int[1_000_000];
        
        
        // First run of unintuitive coins algorithm with the positives
        // This is the standard implementation
        for (int i=1; i < dp.length; i++)
        {
            int min = Integer.MAX_VALUE;
            
            for (int j=0; j < posCoins.length; j++)
                if (i - posCoins[j] > -1)
                    min = Math.min(min, dp[i-posCoins[j]] + 1);
            
            dp[i] = min;
        }
        
        // Second run of algorithm this time for the negatives
        // Differs in that it starts from high end and subtracts
        // coin values rather than adding them
        for (int i = dp.length - negCoins[0] - 1; i >= 0; i--)
        {
            int min = dp[i];
            
            for (int j=0; j < negCoins.length; j++)
                if (i + negCoins[j] < dp.length)
                    min = Math.min(min, dp[i + negCoins[j]] + 1);
            
            dp[i] = min;
        }
        
        return dp[value];
    }
    
}
