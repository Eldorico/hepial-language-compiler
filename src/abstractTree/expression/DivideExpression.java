package abstractTree.expression;

public class DivideExpression extends ArithmeticExpression {

	public DivideExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) / (%s)", this.leftOperand.toString(), this.rigthOperand.toString());
	}

}
