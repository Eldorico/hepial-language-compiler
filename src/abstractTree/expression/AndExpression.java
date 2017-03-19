package abstractTree.expression;


/**
 * @description:
 * This class represents an AND expression such as:
 * asdf and asdf
 *
 */
public class AndExpression extends RelationalExpression {

	public AndExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) && (%s)", leftOperand, rigthOperand);
	}

}
