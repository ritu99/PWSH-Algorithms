import java.util.*;


public class Palindrome {
    
    public static void main(String[] args)
    {
        System.out.println("Palindrome:");
        System.out.println(isPalindrome("a"));
        System.out.println(isPalindrome("aa"));
        System.out.println(isPalindrome("aba"));
        System.out.println(isPalindrome("123"));
        System.out.println(isPalindrome("abcdcba"));
        System.out.println(isPalindrome("abccab"));
        System.out.println(isPalindrome("abc"));
        
    }
    
    public static boolean isPalindrome(String str)
    {
        for (int i=0; i < str.length()/2; i++)
            if (str.charAt(i) != str.charAt(str.length()-1-i))
                return false;
        
        return true;
    }
    
}
