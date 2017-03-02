
import java.util.*;


public class Permutations {
    
    public static void main(String[] args)
    {
        permute("abc");
        System.out.println();
        permute(new String[]{"a", "b", "c"});
    }
    
    public static void permute(String s)
    {
        permute("", s);
    }
    
    public static void permute(String prefix, String str)
    {
        if (str.length() == 0)
            System.out.println(prefix);
        else
        {
            for (int i=0; i < str.length(); i++)
            {
                String newPrefix = prefix + str.charAt(i);
                String newStr = str.substring(0, i) + str.substring(i+1);
                
                permute(newPrefix, newStr);
            }
        }
    }
    
    public static void permute(String[] arr)
    {
        permute(new String[0], arr);
    }
    
    public static void permute(String[] prefix, String[] arr)
    {
        if (arr.length == 0)
            System.out.println(Arrays.toString(prefix));
        else
        {
            for (int i=0; i < arr.length; i++)
            {
                String[] newPrefix = Arrays.copyOf(prefix, prefix.length+1);
                newPrefix[prefix.length] = arr[i];
                
                String[] newArr = new String[arr.length-1];
                for (int j=0; j < i; j++)
                    newArr[j] = arr[j];
                for (int j=i; j < arr.length-1; j++)
                    newArr[j] = arr[j+1];
                
                permute(newPrefix, newArr);
            }
        }
    }
    
}
