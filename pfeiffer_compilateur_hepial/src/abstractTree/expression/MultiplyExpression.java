package abstractTree.expression;


/**
 * @description:
 * This class represents an addition expression such as:
 * asdf * asdf
 *
 */
public class MultiplyExpression extends ArithmeticExpression {

	public MultiplyExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) * (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}

    @Override
    public Integer evaluateIntValue(){
        Integer left = leftOperand.evaluateIntValue();
        Integer right = leftOperand.evaluateIntValue();
        if(left == null || right == null){
            return null;
        }else{
            return left * right;
        }
    }
}
