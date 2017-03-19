package abstractTree.expression;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @description:
 * This class represent an Index expression used to access a particular part of an array.
 * array[IndexExpression]
 *
 * The class can have multiple Indexes for multidimensional arrays.
 * Only one class is used to access a value of an multidimensional array:
 * array[IndexExpression][IndexExpression][IndexExpression]
 *
 */
public class IndexExpression extends Expression {

	LinkedList<Expression> indexes;

	public IndexExpression(Expression expression) {
		// if the expression is a IndexExpression, print error. (it should not be constructed
		// with a IndexExpression from the parser. Use the addIndex() instead.
		if(expression.getClass().equals(IndexExpression.class)){
			System.err.printf("IndexExpression.constructor: expression is allready a IndexExpression. IndexExpression should be constructed with a IndexExpression from the parser. Use the addIndex() instead.");
		}

		this.indexes = new LinkedList<Expression>();
		this.indexes.add(expression);
	}

	/**
	 * @description: adds the expression as a new index
	 * @param instruction: the instruction to add to the BlocInstruction
	 */
	public void addIndex(Expression expression){
		// if the expression is a IndexExpression, print error.
		if(expression.getClass().equals(IndexExpression.class)){
			System.err.printf("IndexExpression.addIndex(): instruction is allready a IndexExpression. IndexExpression should not be added to a IndexExpression from the parser. I think it is an error.");
		}
		indexes.addFirst(expression);
	}

	@Override
	public String toString() {
		String stringToReturn = new String("");
		Iterator<Expression> i = indexes.iterator();
		while(i.hasNext()){
			stringToReturn += String.format("[%s]", i.next().toString());
		}
		return stringToReturn;
	}

}
