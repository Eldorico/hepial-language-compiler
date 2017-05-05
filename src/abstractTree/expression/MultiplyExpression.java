package abstractTree.expression;

/**
 * @description:
 * This class represents an addition expression such as:
 * asdf * asdf
 *
 */
public class MultiplyExpression extends ArithmeticExpression {

	public MultiplyExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) * (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}

}
