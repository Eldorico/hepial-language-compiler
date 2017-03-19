package abstractTree.expression;

/**
 * @description:
 * This class represents an lesser-equal-relation expression such as:
 * asdf <= asdf
 *
 */
public class LesserEqualExpression extends RelationalExpression {

	public LesserEqualExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) <= (%s)", this.leftOperand.toString(), this.rigthOperand.toString());
	}


}
