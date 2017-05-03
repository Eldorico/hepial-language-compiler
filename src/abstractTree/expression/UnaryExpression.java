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
}
