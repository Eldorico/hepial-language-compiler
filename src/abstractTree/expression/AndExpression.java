package abstractTree.expression;

import codeProduction.JEvaluator;


/**
 * @description:
 * This class represents an AND expression such as:
 * asdf and asdf
 *
 */
public class AndExpression extends RelationalBooleanExpression {

	public AndExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) && (%s)", leftOperand, rightOperand);
	}

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }
}
