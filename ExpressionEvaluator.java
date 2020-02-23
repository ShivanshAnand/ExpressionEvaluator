/*	  Written by Shivansh Anand
 *    Algorithmic help and inspirations were taken from following pages : 
 *    1. https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 *     2. https://blog.devartis.com/how-i-used-the-visitor-pattern-to-solve-the-shunting-yard-algorithm-b269985a1af0
 *    3. https://stackoverflow.com/questions/40389749/infix-expression-to-binary-tree-in-c/40389942
 *        (however bin tree wasn't used)
 *
 *     How this works ?
 *     1. Tokenize the expresison(infix) into operands and operators
 *     2. Convert it info postfix expression
 *     3. Evaluate postfix
*/


import java.util.ArrayList;
import java.util.Stack;

public class ExpressionEvaluator {
	
	private int getPrecedence(String opr) {
		if(opr.equals("+") || opr.equals("-"))
			return 1;
		if(opr.equals("*") || opr.equals("/")) {
			return 2;
		}
		return 0; // for brackets
	}
	
	private String evalPostfix(ArrayList<Token> postfixArr) {
		Stack<Double> stack = new Stack<>();
		for(int i=0; i<postfixArr.size(); i++) {
			Token e = postfixArr.get(i);
			if(e.getType().equals("num")) {
				stack.push(Double.parseDouble(e.getVal()));
			} else {
				double a,b;
				try {
					a = stack.pop();
					b = stack.pop();
				} catch(Exception e1) {
					return "Error in your expression";
				}
				if(e.getVal().equals("+")) {
					stack.push(b+a);
				} else if(e.getVal().equals("-")) {
					stack.push(b-a);
				} else if(e.getVal().equals("*")) {
					stack.push(b*a);
				} else {
					if(a == 0) {
						return "Can't divide by zero bud :(";
					}
					stack.push(b/a);
				}				
			}
		}
		return stack.pop() + "";	
	}
	
	private ArrayList<Token> infixToPostfix(ArrayList<Token> arr) {
		ArrayList<Token> postfixArr = new ArrayList<Token>();
		Stack<Token> stack = new Stack<Token>();
		for(int i=0; i<arr.size(); ++i) {
			Token e = arr.get(i);
			if(e.getType().equals("num")) {
				postfixArr.add(e);
			} else if(e.getType().equals("bo")) {
				stack.push(e);
			} else if(e.getType().equals("bc")) {
				while(!stack.isEmpty() && !stack.peek().getType().equals("bo")) {
					postfixArr.add(stack.pop());
				}
				if(!stack.isEmpty() && !stack.peek().getType().equals("bo")) {
					return new ArrayList<Token>();
				} else {
					stack.pop();
				}
			} else {
				while(!stack.isEmpty() && getPrecedence(e.getVal()) <= getPrecedence(stack.peek().getVal())) {
					if(stack.peek().getType().equals("bo")) {
						new ArrayList<Token>();;
					}
					postfixArr.add(stack.pop());
				}
				stack.push(e);
			}
		}
		
		while(!stack.isEmpty()) {
			if(stack.peek().getType().equals("bo")) {
				new ArrayList<Token>();
			}
			postfixArr.add(stack.pop());
		}
		return postfixArr;
	}

	private String edDjEValuate(String expression) {
		ArrayList<Token> arr = new ArrayList<>();	
		String expr = "(" + expression + ")";		
		for(int i=0; i<expr.length(); i++) {
			char ch = expr.charAt(i);
			String x = "" + ch;
			if(ch >= '0' && ch <= '9' || ch == '.') {
				for(int j = i+1; j<expr.length(); j++)  {
						if(expr.charAt(j) >= '0' && expr.charAt(j) <= '9' || expr.charAt(j) == '.') {
							x = x + expr.charAt(j);
						} else { 
							/*logic fails because else block is never
							*reached because the characters are always digits so value of i isn't updated
							*to handle this I've added "(" and ") at the starting and ending of the 
							*expr beforehand
							*/
							i = j-1;
							break;
						}
					}
				arr.add(new Token(x, "num"));
			} else if (x.equals("(")) {
				arr.add(new Token(x, "bo"));
			} else if(x.equals(")")) {
				arr.add(new Token(x, "bc"));
			} else {
				arr.add(new Token(x, "opr"));
			}
		}
		ExpressionEvaluator ob = new ExpressionEvaluator();
		if(ob.isBalanced(arr)) {
			if(!ob.infixToPostfix(arr).isEmpty())
				return ob.evalPostfix(ob.infixToPostfix(arr));
			else
				return "Some error occurred";
		}
		return "Unbalanced";
	}
	
	private boolean isBalanced(ArrayList<Token> arr) {
		int boCount = 0;
		int bcCount = 0;
		for(Token e : arr) {
			if(e.getType().equals("bo"))
				boCount++;
			if(e.getType().equals("bc"))
				bcCount++;
		}
		return boCount == bcCount;
	}
	
	public static void main(String args[]) {
		String expression = "8+(9*2))"; // sample
		ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
		System.out.println(expressionEvaluator.edDjEValuate(expression));
	}
}