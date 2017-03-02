import java.util.*;


public class Pangram {
    
    public static void main(String[] args)
    {
        System.out.println("Pangram:");
        System.out.println(isPangram("abcdefghijklmnopqrstuvwxyz"));
        System.out.println(isPangram("abcdeghijklmnopqrsuvwxyz"));
        System.out.println(isPangram("cats and bats are cool and awesome!"));
        
    }
    
    /**
    * Returns an ArrayList of missing characters (empty if pangram)
    */
    public static ArrayList<Character> isPangram(String str)
    {
        boolean[] letters = new boolean[26];
        str = str.toLowerCase();
        
        for (char c : str.toCharArray())
            if (c - 'a' >= 0 && c - 'a' < 26)
                letters[c-'a'] = true;
        
        ArrayList<Character> missing = new ArrayList<>();
        
        for (int i=0; i < 26; i++)
            if (!letters[i])
                missing.add((char) (i + 'a'));
        
        return missing;
    }
    
}
