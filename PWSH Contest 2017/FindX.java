import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * FindX
 * 
 * The first major part of this problem is parsing the post-fix equations. With postfix/ prefix,
 * we could utilize a stack to help evaluate an equation.
 * 
 * The second major part of this problem is solving for x. To simplify the problem, all rational
 * equations and FOILing are removed. This leaves the problem with simple linear equations to 
 * solve. In this case we use a recursive solution to evaluate the equation, accounting for the
 * four basic arithmetic operators: +, -, *, /. We have a Polynomial class to make these operations
 * easier to carry out, and throwing exceptions when it encounters FOILing and rational polynomials.
 * 
 * The only edge case to account for is with cases such as X = X or X + 1 = X
 * 
 * @author Henry
 *
 */


public class FindX
{
	/**
	 * Polynomial class with the form ax + b.
	 */
	public static class Polynomial
	{
		private double xCoeff;
		private double constant;
		
		public Polynomial(double xCoeff, double constant)
		{
			this.xCoeff = xCoeff;
			this.constant = constant;
		}
		
		public void add(Polynomial other)
		{
			xCoeff += other.xCoeff;
			constant += other.constant;
		}
		
		public void subtract(Polynomial other)
		{
			xCoeff -= other.xCoeff;
			constant -= other.constant;
		}
		
		public void multiply(Polynomial other)
		{
			if (other.xCoeff != 0 && xCoeff != 0)
				throw new RuntimeException("No FOILing allowed!"); //Should not happen!!!!
			
			double factor;
			if (other.xCoeff != 0)
			{
				xCoeff = other.xCoeff;
				factor = constant;
				constant = other.constant;
			}
			else
			{
				factor = other.constant;
			}
			xCoeff *= factor;
			constant *= factor;
		}
		
		public void divide(Polynomial other)
		{
			if (other.xCoeff != 0)
				throw new RuntimeException("No rational polynomials allowed!"); //Should not happen!!!!
			
			double factor = other.constant;
			xCoeff /= factor;
			constant /= factor;
		}
		
		@Override
		public String toString()
		{
			return xCoeff + "x + " + constant;
		}
	}
	
	public static abstract class Expression
	{
		Operation parent;
		/**
		 * Attempts to turn this operation into a polynomial in the form ax + b
		 * @return a simplified polynomial
		 */
		public abstract Polynomial evaluate();
		
		public Operation getParent()
		{
			return parent;
		}
	}
	
	public static class Value extends Expression
	{
		private boolean isVariable;
		private double coefficent; //if this is not a variable, 
			                    //then the coefficient represents 
		                        //the numeric value.
		
		public Value(boolean isVariable, double coefficent)
		{
			this.isVariable = isVariable;
			this.coefficent = coefficent;
		}
		
		@Override
		public String toString()
		{
			if (isVariable)
				return coefficent + "x";
			else
				return "" + coefficent;
		}

		@Override
		public Polynomial evaluate()
		{
			if (isVariable)
				return new Polynomial(coefficent, 0);
			else
				return new Polynomial(0, coefficent);
		}
	}
	
	public static class Operation extends Expression
	{
		private char oper;
		private Expression operand1;
		private Expression operand2;
		private int numFilled;
		
		public Operation(char operator)
		{
			this.oper = operator;
		}

		/**
		 * Adds an operand to the next space, or false
		 * @param val the expression (value or operation) to add as an operand.
		 */
		public void addOperand(Expression val)
		{
			if (numFilled == 2)
				return;
			
			val.parent = this;
			
			numFilled++;
			if (numFilled == 1)
				operand1 = val;
			else
				operand2 = val;
		}
		
		/**
		 * Replaces the top operand with this value
		 * @param val the value to replace with.
		 */
		public void replaceOperand(Expression val)
		{
			if (numFilled == 0)
				return; //ERROR right here.
			
			val.parent = this;
			
			if (numFilled == 1)
				operand1 = val;
			else
				operand2 = val;
		}
		
		/**
		 * Determines if all it's operands are all filled.
		 * @return true if all filled, false otherwise.
		 */
		public boolean isFilled()
		{
			return numFilled >= 2;
		}
		
		@Override
		public String toString()
		{
			if (!isFilled())
				return "?";
			else
				return "(" + operand1 + " " + oper + " " + operand2 + ")";
		}

		@Override
		public Polynomial evaluate()
		{
			Polynomial poly1 = operand1.evaluate();
			Polynomial poly2 = operand2.evaluate();
			
			switch (oper)
			{
			case '+':
				poly1.add(poly2);
				return poly1;
			case '-':
				poly1.subtract(poly2);
				return poly1;
			case '*':
				poly1.multiply(poly2);
				return poly1;
			case '/':
				poly1.divide(poly2);
				return poly1;
			default:
				throw new RuntimeException("Illegal operator: '" + oper + "'.");
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		//Scanner equation = new Scanner("");
		Scanner in = new Scanner(new File("findx.in"));
		System.setOut(new PrintStream(new File("findx.out"))); //Comment this line to print to console.
		int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++)
		{
			String statement = solveForX(in.nextLine());
			System.out.println(statement);
		}
	}
	
	public static String solveForX(String equation)
	{
		Expression equ = parseExpression(toTokens(equation));
		if (equ instanceof Value)
			throw new RuntimeException("This is an expression");
		else //if (equ instanceof Operation)
		{
			Operation oper = (Operation)equ;
			if (oper.oper != '=')
				throw new RuntimeException("This is an expression");
			
			Polynomial left = oper.operand1.evaluate();
			Polynomial right = oper.operand2.evaluate();
			left.subtract(right);
			
			if (left.xCoeff == 0)
			{
				if (left.constant == 0)
					return "X = ?";
				else
					return "Undefined";
			}
			else
				return String.format("X = %.3f", -left.constant / left.xCoeff);
		}
		
	}
	
	public static Object[] toTokens(String expression)
	{
		Scanner tokenizer = new Scanner(expression);
		ArrayList<Object> tokens = new ArrayList<>();
		
		while (tokenizer.hasNext())
		{
			if (tokenizer.hasNextDouble())
				tokens.add(tokenizer.nextDouble());
			else
				tokens.add(tokenizer.next());
		}
		return tokens.toArray();
	}
	
	/**
	 * Parses an expression (without the = stuff).
	 * @param tokens an array of parsed tokens (numbers as ints, variables and operators as strings)
	 * @return a parsed Expression object representative of the expression.
	 */
	public static Expression parseExpression(Object[] tokens)
	{
		Deque<Expression> values = new LinkedList<>(); 
		for (Object token : tokens)
		{
			Expression expr;
			if (token instanceof Double)
				expr = new Value(false, (Double)token);
			else if (token.equals("x") || token.equals("X"))
				expr = new Value(true, 1);
			else 
				expr = new Operation(((String)token).charAt(0));
			
			if (expr instanceof Operation)
			{
				Expression val1, val2;
				val2 = values.pop();
				val1 = values.pop();
				
				Operation oper = (Operation)expr;
				oper.addOperand(val1);
				oper.addOperand(val2);
			}
			values.push(expr);
		}
		
		Expression root = values.pop();
		if (!values.isEmpty())
			throw new RuntimeException("Dangling expressions");
		return root;
	}
	
	
}
