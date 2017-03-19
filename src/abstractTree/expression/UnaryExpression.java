package abstractTree.expression;

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
}
