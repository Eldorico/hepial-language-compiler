package abstractTree.expression;

import java.util.ArrayList;

import symbol.Type;

/**
 * @description:
 * This class represent a list of expression. It can be used to access a particular part of an array.
 * array[ExpressionList] or fct(ExpressionList)
 * It is part of the Expression heritage because the grammar adds it into the abstractTreeStack
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

	ArrayList<Expression> expressionList;

	public ExpressionList(Expression expression) {
		// if the expression is a IndexExpression, print error. (it should not be constructed
		// with a IndexExpression from the parser. Use the addExpression() instead.
		if(expression.getClass().equals(ExpressionList.class)){
			System.err.printf("ExpressionList.constructor: expression is allready a ExpressionList. ExpressionList should be constructed with a ExpressionList from the parser. Use the addExpression() instead.");
		}

		this.expressionList = new ArrayList<Expression>();
		this.expressionList.add(expression);
	}

	public ExpressionList(){
		this.expressionList = new ArrayList<Expression>();
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
		expressionList.add(0,expression);
	}

	/**
	 * @description this method should not be used.
	 */
	@Override
	public String toString(){
	    String errMsg = "ExpressionList.toString(): SHOULD NOT BE USED!. Use ExpressionList.toString(booelan) INSTEAD\n";
	    System.err.print(errMsg);
		return new String(errMsg);
	}

	/**
	 * @description: prints the expression list
	 * @param isIndex : if true, format the string as '[expression][expression][...]'
	 *  else format as 'expression, expression, ...'
	 * @return the formated string version of the expressionList
	 */
	public String toString(boolean isIndex) {
		String stringToReturn = new String("");
		for(Expression expression : expressionList){
		    if(isIndex){
                stringToReturn += String.format("[%s]", expression.toString());
            }else{
                stringToReturn += String.format("%s, ", expression.toString());
            }
		}

		// if index, return the string as it is
		if(isIndex){
			return stringToReturn;
	    // else, remove the ', ' before returning
		}else{
			if(stringToReturn.length() > 2){
				return stringToReturn.substring(0, stringToReturn.length()-2);
			}else{
				return stringToReturn;
			}
		}
	}

	/**
     * @description this method should not be used.
     */
	@Override
	public Type getType(){
	    System.err.println("ExpressionList.getType(): This function should not be used!");
	    return null;
	}


	/**
	 * @description: returns the size of the expression list
	 * @return returns the size of the expression list
	 */
	public int getSize(){
	    return expressionList.size();
	}

}
