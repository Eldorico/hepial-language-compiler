package abstractTree.expression;

import symbol.Type;

/**
 * This class represents unary Expressions as:
 * !asdf
 * It is the parent of:
 * - NotExpression
 *
 */
public abstract class UnaryExpression extends Expression {

	Expression expression;

	public UnaryExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public Type getType(){
	    return expression.getType();
	}

	public Expression getExpression(){
	    return expression;
	}

	/**
	 * @description: checks for semantic errors into the expression.
	 * If any error found, it will be logged into the ErrorPrinter
	 * @return: true if any errors has been detected. Else false.
	 */
	@Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
	    return expression.semanticErrorsDetected(declarationLineNumber);
	}
}
