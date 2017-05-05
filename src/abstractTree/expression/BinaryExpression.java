package abstractTree.expression;


/**
 * @description:
 * This class is the parents of binary Expressions such as:
 * asdf = asdf (Relation type)
 * asdf + asdf (Arithmetic type).
 *
 * It is the parent of:
 * - ArithmeticExpression
 * - RelationalExpression
 *
 */
public abstract class BinaryExpression extends Expression {

	Expression leftOperand;
	Expression rightOperand;

	public BinaryExpression(Expression leftOperand, Expression rightOperand){
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	public Expression getLeftOperand(){
	    return this.leftOperand;
	}

	public Expression getRightOperand(){
	    return this.rightOperand;
	}

	/**
	 * @description: checks if the left and right operands have semantic errors.
	 * If errors are found, they will be logged into the ErrorPrinter
	 */
	@Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
	    if(leftOperand.semanticErrorsDetected(declarationLineNumber) || rightOperand.semanticErrorsDetected(declarationLineNumber)){
	        return true;
	    }else{
	        return false;
	    }
	}
}
