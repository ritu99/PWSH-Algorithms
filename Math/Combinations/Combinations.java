
public class Combinations {
    
    public static void main(String[] args)
    {
        combinations("abcde", 3);
    }
    
    public static void combinations(String str, int len)
    {
        combinations("", str, 0, str.length()-len);
    }
    
    public static void combinations(String prefix, String str, int index, int len)
    {
        if (len == str.length())
            System.out.println(prefix);
        else
        {
            for (int i=index; i <= len; i++)
            {
                combinations(prefix + str.charAt(i), str, i+1, len+1);
            }
        }
    }
    
}
