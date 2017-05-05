package abstractTree.expression;

/**
 * @description:
 * This class represents an OR expression such as:
 * asdf or asdf
 *
 */
public class OrExpression extends RelationalExpression {

	public OrExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) || (%s)", leftOperand, rightOperand);
	}
}
