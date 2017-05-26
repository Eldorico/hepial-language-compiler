package abstractTree.expression;

import codeProduction.JEvaluator;

/**
 * @description:
 * This class represents an OR expression such as:
 * asdf or asdf
 *
 */
public class OrExpression extends RelationalBooleanExpression {

	public OrExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) || (%s)", leftOperand, rightOperand);
	}

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }
}
