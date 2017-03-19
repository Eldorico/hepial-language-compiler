package abstractTree.expression;

public class NotExpression extends UnaryExpression {

	public NotExpression(Expression expression) {
		super(expression);
	}

	@Override
	public String toString() {
		return String.format("!(%s)", this.expression.toString());
	}

}
