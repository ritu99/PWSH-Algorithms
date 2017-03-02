
import java.util.*;
import java.io.*;

public class Yazaxa
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("yazaxa.in"));
		//System.setOut(new PrintStream(new File("yazaxa.out"))); //Comment this line to print to console.
		long tests = in.nextInt();
		primeDict[0] = 2;
		for (long test = 0; test < tests; test++)
		{
			String yazaxawhwhwhat = in.next();
			
			int[] exponents = new int[yazaxawhwhwhat.length() / 2];
			long[] primes = new long[exponents.length];
			
			for (int i = 0; i < exponents.length; i++)
				exponents[i] = getInt(yazaxawhwhwhat.charAt(i * 2));
			
			//find primes
			for (int i = primes.length - 1, j = 0; i >= 0; i--, j++)
			{
				if (primeDict[j] == 0)
					primeDict[j] = findNextPrime(primeDict[j - 1]);
				primes[i] = primeDict[j];
			}
			
			long answer = 1;
			for (int i = 0; i < exponents.length; i++)
			    if (exponents[i] != 0)
			        answer *= (long) Math.pow(primes[i], exponents[i]);
			
			System.out.println(answer);
		}
	}
	
	public static long[] primeDict = new long[100_000];
	
	public static long findNextPrime(long prime)
	{
		if (prime == 2)
			return 3;
		if (prime == 3)
			return 5;
		
		if ((prime + 1) % 6 == 0)
		{
			long temp = prime + 2;
			if (isPrime(temp))
				return temp;
			else
				return findNextPrime(temp);
		}
		
		//if ((prime - 1) % 6 == 0)
		long temp = prime + 4;
		if (isPrime(temp))
			return temp;
		else
			return findNextPrime(temp);
	}
	
	//assumes number is greater than 3
	public static boolean isPrime(long n)
	{
		double temp = Math.sqrt(n);
		for (long i = 5; i <= temp; i += 6)
			if (n % i == 0 || n % (i + 2) == 0)
				return false;
		
		return true;
	}
	
	public static int getInt(char c)
	{
		return 25 - (c - 'a');
	}
}
