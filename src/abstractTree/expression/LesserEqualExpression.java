package abstractTree.expression;

import codeProduction.JEvaluator;

/**
 * @description:
 * This class represents an lesser-equal-relation expression such as:
 * asdf <= asdf
 *
 */
public class LesserEqualExpression extends RelationalIntegerExpression {

	public LesserEqualExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) <= (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }
}
