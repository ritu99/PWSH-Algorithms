
public class PrimeTest {
    
    public static void main(String[] args)
    {
        System.out.println(isPrime(13));
        System.out.println(isPrime(26));
        System.out.println(isPrime(27));
        System.out.println(isPrime(81));
        System.out.println(isPrime(25));
        System.out.println(isPrime(29));
        
    }
    
    public static boolean isPrime(int x)
    {
        if (x == 1) return false;
        if (x == 2) return true;
        if (x % 2 == 0 || x % 3 == 0) return false;
        
        for (int i=3; i*i <= x; i++)
            if (x % i == 0) return false;
        
        return true;
    }
    
}
