package abstractTree.expression;


/**
 * @description:
 * This class represents an lesser-than-relation expression such as:
 * asdf < asdf
 *
 */
public class LesserThanExpression extends RelationalIntegerExpression {

	public LesserThanExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) < (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}
}
