package abstractTree.expression;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @description:
 * This class represent a list of expression. It can be used to access a particular part of an array.
 * array[ExpressionList] or fct(ExpressionList)
 *
 * The class can have multiple Expression for multidimensional arrays.
 * Only one class is used to access a value of an multidimensional array:
 * array[ExpressionList][ExpressionList][ExpressionList]
 *
 * The class can also be used to parameters in fonction call. (one ExpressionList for multiple parameters)
 * fct(ExpressionList, ExpressionList, ExpressionList)
 *
 */
public class ExpressionList extends Expression {

	LinkedList<Expression> expressionList;

	public ExpressionList(Expression expression) {
		// if the expression is a IndexExpression, print error. (it should not be constructed
		// with a IndexExpression from the parser. Use the addExpression() instead.
		if(expression.getClass().equals(ExpressionList.class)){
			System.err.printf("ExpressionList.constructor: expression is allready a ExpressionList. ExpressionList should be constructed with a ExpressionList from the parser. Use the addExpression() instead.");
		}

		this.expressionList = new LinkedList<Expression>();
		this.expressionList.add(expression);
	}

	/**
	 * @description: adds the expression as a new index
	 * @param instruction: the instruction to add to the BlocInstruction
	 */
	public void addExpression(Expression expression){
		// if the expression is a IndexExpression, print error.
		if(expression.getClass().equals(ExpressionList.class)){
			System.err.printf("ExpressionList.addExpression(): instruction is allready a ExpressionList. ExpressionList should not be added to a ExpressionList from the parser. I think it is an error.");
		}
		expressionList.addFirst(expression);
	}

	@Override
	public String toString() {
		String stringToReturn = new String("");
		Iterator<Expression> i = expressionList.iterator();
		while(i.hasNext()){
			stringToReturn += String.format("[%s]", i.next().toString());
		}
		return stringToReturn;
	}

}
