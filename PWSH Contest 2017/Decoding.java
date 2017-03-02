/**
 * Decoding.java
 * Decodes inputted strings
 *
 * @author Aditya Arjun
 */
import java.util.*;
import java.io.*;


public class Decoding
{
  public static void main(String[] args) throws IOException
  {
    Scanner in = new Scanner(new File("decoding.in"));
    int n = in.nextInt();
    in.nextLine();
    for(int i = 0; i < n; i++)
    {
      	String test = in.nextLine();
      	String give = "";
      	for(int j = 0; j < test.length(); j++)
        {
          	if(test.charAt(j) == 'y' || test.charAt(j) == 'Y' || test.charAt(j) == 'z' || test.charAt(j) == 'Z') //tests if should wrap around alphabet
               give += (char)(test.charAt(j) - 24);
          	else if(Character.isAlphabetic(test.charAt(j)))
            	give += (char)(test.charAt(j) + 2);							//shifts alphabetic by 2
          	else
              	give += test.charAt(j);									//places nonalphabetic characters in the string
        }
      
      	for(int j = 0; j < give.length() - 2; j++)
        {
          	if(give.charAt(j) == give.charAt(j+1) && give.charAt(j+1) == give.charAt(j+2)) //tests for 3 of same character in a row and replaces
            {
              	give = give.substring(0, j + 1) + give.substring(j+2);
              	j = 0;																	   //returns to beginning of the string to retest if there were more than 3 characters in a row (trick case)
            }
          		
        }
      
      	if(test.charAt(test.length() - 1) == '%')						//tests if string should reverse
        {
          	for(int j = give.length() - 2; j >= 0; j--)
              System.out.print(give.charAt(j));
            
           	System.out.println();
        }
      	else
         	System.out.println(give);
               
    }
  }
}

