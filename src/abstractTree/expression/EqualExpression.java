package abstractTree.expression;

/**
 * @description:
 * This class represents an equal expression such as:
 * asdf = asdf
 *
 */
public class EqualExpression extends Relation {

	public EqualExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("%s = %s", this.leftOperand.toString(), this.rigthOperand.toString());
	}

}
