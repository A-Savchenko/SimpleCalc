import java.util.List;		// used by expression evaluator

/**
 *	<Description goes here>
 *
 *	@author	
 *	@since	
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	// constructor	
	public SimpleCalc() {
		utils = new ExprUtils(); 
		valueStack = new ArrayStack<Double>(); 
		operatorStack = new ArrayStack<String>(); 
	}
	
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		Prompt p = new Prompt(); 
		//String str = p.getString("");
		String str = "6 * (1 + 7)+2";
		System.out.println(str);
		List <String> t = utils.tokenizeExpression(str);
		evaluateExpression(t);  
	}
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {
		double value = 0;
		String operators = "+-*/%^()";
		double numOne = 0.0;  
		double numTwo = 0.0; 
		
		for(int i = 0; i < tokens.size(); i++)
		{
			if(operators.contains(tokens.get(i)))
			{
				if(!operatorStack.isEmpty())
				{
					switch(tokens.get(i))
					{
						case ")":
							while(!operatorStack.peek().equals(")"))
							{
								numOne = valueStack.peek();
								numOne = valueStack.pop();
								numTwo = valueStack.peek();
								numTwo = valueStack.pop();
								switch (operatorStack.peek())
								{
									
									case "+":
									{
										valueStack.push(numOne+numTwo);
										break;
									}
									case "-":
									{
										valueStack.push(numTwo - numOne);
										break;
									}
									case "*":
									{
										valueStack.push(numTwo * numOne);
										break; 
									}
									case "/":
									{
										valueStack.push(numTwo / numOne);
										break; 
									}
								}
							}
							operatorStack.pop();
						
					}
					if(hasPrecedence(operatorStack.peek(), tokens.get(i)))
					{
						operatorStack.push(tokens.get(i));
					} 
					else
					{
						String str = operatorStack.peek();
						operatorStack.pop(); 
						operatorStack.push(tokens.get(i));
						operatorStack.push(str);
					}
				}
				else
				{
					operatorStack.push(tokens.get(i));
				}
			}
			else		
				valueStack.push(Double.parseDouble(tokens.get(i)));
			System.out.println(operatorStack.toString()+valueStack.toString());
		}
		return value;
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	 
}
