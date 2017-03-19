package abstractTree.expression;

/**
 * @description:
 * This class represents an addition expression such as:
 * asdf + asdf
 *
 */
public class AdditionExpression extends ArithmeticExpression {

	public AdditionExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) + (%s)", this.leftOperand.toString(), this.rigthOperand.toString());
	}

}
