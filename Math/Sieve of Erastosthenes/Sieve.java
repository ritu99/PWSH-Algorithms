import java.util.*;

public class Sieve {
    
    public static void main(String[] args)
    {
        boolean[] primes = getPrimes(100);
        for (int i=0; i < primes.length; i++)
            if (primes[i]) System.out.println(i);
    }
    
    public static boolean[] getPrimes(int x)
    {
        boolean[] primes = new boolean[x+1];
        
        for (int i=2; i <= x; i++)
            primes[i] = true;
        
        for (int i=2; i <= x/2; i++)
            for (int j=2; j <= x/i; j++)
                primes[i*j] = false;
        
        return primes;
    }
    
}
