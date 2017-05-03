package abstractTree.expression;

import java.util.Iterator;
import java.util.LinkedList;

import symbol.Type;

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

	public ExpressionList(){
		this.expressionList = new LinkedList<Expression>();
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
	public String toString(){
		return new String("ExpressionList.toString(): SHOULD NOT BE USED!. Use ExpressionList.toString(booelan) INSTEAD\n");
	}

	public String toString(boolean isIndex) {
		String stringToReturn = new String("");
		Iterator<Expression> i = expressionList.iterator();
		while(i.hasNext()){
			if(isIndex){
				stringToReturn += String.format("[%s]", i.next().toString());
			}else{
				stringToReturn += String.format("%s, ", i.next().toString());
			}
		}
		if(isIndex){
			return stringToReturn;
		}else{
			if(stringToReturn.length() > 2){
				return stringToReturn.substring(0, stringToReturn.length()-2);
			}else{
				return stringToReturn;
			}
		}

	}

	@Override
	public Type getType(){
	    System.err.println("ExpressionList.getType(): This function should not be used!");
	    return null;
	}

}
