import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * FindX_Infix
 * 
 * The first major part of this problem is parsing the infix equations. With postfix/ prefix,
 * we could utilize a stack to help evaluate an equation, however, with infix, the easiest 
 * method of representation is with a tree. Each operator has two nodes operands, each of these
 * an expression.
 * 
 * The second major part of this problem is solving for x. To simplify the problem, all rational
 * equations and FOILing are removed. This leaves the problem with simple linear equations to 
 * solve. In this case we use a recursive solution to evaluate the equation, accounting for the
 * four basic arithmetic operators: +, -, *, /. We have a Polynomial class to make these operations
 * easier to carry out, and throwing exceptions when it encounters FOILing and rational polynomials.
 * 
 * @author Henry
 *
 */


public class FindX_Infix
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
		
		/**
		 * Determines whether if the this operator has a higher precedence than 
		 * the other operation.
		 * @param other the operation to test with
		 * @return true if this has equal or greater precedence, otherwise return false.
		 */
		public boolean operationPrecedence(Operation other)
		{
			int op1 = oper == '+' || oper == '-' ? 1 : 2;
			int op2 = other.oper == '+' || other.oper == '-' ? 1 : 2;
			return op1 >= op2;
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
			double x = solveForX(in.nextLine());
			System.out.printf("X = %.3f\n", x);
		}
	}
	
	public static double solveForX(String equation)
	{
		String[] parts = equation.split(" = ");
		Polynomial left = parseExpression(toTokens(parts[0])).evaluate();
		Polynomial right = parseExpression(toTokens(parts[1])).evaluate();
		left.subtract(right);
		return -left.constant / left.xCoeff;
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
		class State
		{
			Expression root; //The root value to be evaluated.
			Expression prev; //The expression we previously encountered.
		}
		
		Deque<State> parenths = new LinkedList<>();
		State current = new State();
		
		for (Object token : tokens)
		{
			boolean parenthesis = false;
			Expression expr;
			if (token instanceof Double)
				expr = new Value(false, (Double)token);
			else if (token.equals("x") || token.equals("X"))
				expr = new Value(true, 1);
			else if (token.equals("(") || token.equals(")"))
			{
				parenthesis = true;
				expr = null;
			}
			else 
				expr = new Operation(((String)token).charAt(0));
			
			if (parenthesis)
			{
				if (token.equals("("))
				{
					parenths.push(current);
					if (current.prev != null)
					{
						if (!(current.prev instanceof Operation) || ((Operation)current.prev).isFilled())
							throw new RuntimeException("Consecutive filled expressions.");
					}
					
					current = new State();
				}
				else //if (token.equals(")")
				{
					if (parenths.isEmpty()) //Stack underflow of parenthesis
						throw new RuntimeException("Dangling close parenthsis.");
					if (current.prev == null)
						throw new RuntimeException("No expression inside parenthesis.");
					if (current.prev instanceof Operation && !((Operation)current.prev).isFilled())
						throw new RuntimeException("Expression inside parenthesis is not complete.");
					
					State outside = parenths.pop();
					if (outside.prev == null)
					{
						outside.root = outside.prev = current.root;
					}
					else
					{
						((Operation)outside.prev).addOperand(current.root);
					}
					current = outside;
				}
			}
			else if (expr instanceof Value)
			{
				if (current.prev == null)
				{
					//First value we encountered.
					//We just wait until we see an operation
					current.root = expr; //Currently the root expression to evaluate.
				}
				else if (current.prev instanceof Operation)
				{ //We just encountered an operation, add this value into the operation.
					Operation oper = (Operation)current.prev;
					if (oper.isFilled())
						throw new RuntimeException("Operation should not have been filled!");
					oper.addOperand(expr);
				}
				else
				{ //We see two consecutive values/ expressions together.
					throw new RuntimeException("Two consecutive values/ filled expressions!");
				}
				current.prev = expr;
			}
			else //if (expr instanceof Operation)
			{
				if (current.prev == null)
				{
					//An operator should not be the first token we see.
					throw new RuntimeException("Dangling operator");
				}
				else if (current.prev instanceof Operation)
				{
					//We should not see two consecutive operator tokens
					//Although we might see an operation after a 
					//close parenthesis. So check if it is filled.
					//If it is, we just treat it as an expression.
					if (!((Operation)current.prev).isFilled())
						throw new RuntimeException("Consecutive operators!!!");
				}
				
				assert expr != null;
				Operation opExpr = (Operation)expr;
				
				Expression firstExpr = current.prev;
				Operation test = firstExpr.parent;
				while (firstExpr != current.root && test.operationPrecedence(opExpr))
				{
					firstExpr = test;
					test = test.parent;
				}
				
				if (firstExpr == current.root)
					current.root = expr;
				else
					test.replaceOperand(opExpr);
				opExpr.addOperand(firstExpr);
				current.prev = expr;
			}
			
		}
		if (current.prev instanceof Operation && !((Operation)current.prev).isFilled())
			throw new RuntimeException("Expression is not complete.");
		return current.root;
	}
	
	
}
