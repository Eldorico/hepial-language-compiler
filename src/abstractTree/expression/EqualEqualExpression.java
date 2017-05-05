package abstractTree.expression;

/**
 * @description:
 * This class represents an equalequal expression such as:
 * asdf == asdf
 *
 */
public class EqualEqualExpression extends RelationalMixedExpression {

	public EqualEqualExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) == (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}

}
