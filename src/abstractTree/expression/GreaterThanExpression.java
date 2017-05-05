package abstractTree.expression;


/**
 * @description:
 * This class represents an greater-than-relation expression such as:
 * asdf > asdf
 *
 */
public class GreaterThanExpression extends RelationalIntegerExpression {

	public GreaterThanExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) > (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}


}
