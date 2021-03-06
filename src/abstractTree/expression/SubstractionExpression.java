package abstractTree.expression;


/**
 * @description:
 * This class represents a substraction expression such as:
 * asdf - asdf
 *
 */
public class SubstractionExpression extends ArithmeticExpression {

	public SubstractionExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public String toString() {
		return String.format("(%s) - (%s)", this.leftOperand.toString(), this.rightOperand.toString());
	}

    @Override
    public Integer evaluateIntValue(){
        Integer left = leftOperand.evaluateIntValue();
        Integer right = leftOperand.evaluateIntValue();
        if(left == null || right == null){
            return null;
        }else{
            return left - right;
        }
    }
}
