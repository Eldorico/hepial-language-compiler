package abstractTree.expression;


/**
 * @description:
 * This class represents an different-than-relation expression such as:
 * asdf != asdf
 *
 */
public class DifferentThanExpression extends RelationalMixedExpression {

	public DifferentThanExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) != (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}

}
