
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
    
    public static int lcm(int a, int b)
    {
        return a / gcd(a, b) * b;
    }
}
