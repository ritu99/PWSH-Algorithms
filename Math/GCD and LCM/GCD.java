
public class GCD {
    
    public static void main(String[] args)
    {
        System.out.println(gcd(8, 24));
        System.out.println(gcd(15, 125));
        System.out.println(gcd(27, 90));
        System.out.println(gcd(10000, 1));
        System.out.println(gcd(20, 45));
        System.out.println(gcd(72, 54));
    }
    
    /**
    * Greatest Common Divisor of two numbers
    * @param a first number
    * @param b second number
    * return gcd of a and b
    */
    public static int gcd(int a, int b)
    {
        int x = Math.min(a, b);
        int y = Math.max(a, b);
        
        while (x != 0)
        {
            int t = x;
            x = y % x;
            y = t;
        }
        
        return y;
    }
    
    /**
    * Least Common Multiple of two numbers
    * @param a first number
    * @param b second number
    * @return lcm of a and b
    */
    public static int lcm(int a, int b)
    {
        return a / gcd(a, b) * b;
    }
}
