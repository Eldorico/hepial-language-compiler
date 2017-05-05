package abstractTree.expression;

import symbol.Type;
import utils.ErrorPrinter;

public class NotExpression extends UnaryExpression {

	public NotExpression(Expression expression) {
		super(expression);
	}

	@Override
	public String toString() {
		return String.format("!(%s)", this.expression.toString());
	}

	/**
	 * @description: checks if any error is detected on the expression
	 * checks that the expression is a boolean value.
	 */
	@Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
	       // check for semantic errors on the left and right operand. (It will log errors if any found)
	       boolean errorsDetected = super.semanticErrorsDetected(declarationLineNumber);

	       if(expression.getType() != Type.BOOLEAN){
	           ErrorPrinter.getInstance().logError(expression.toString()+" : Expression has to be a boolean value.", declarationLineNumber);
	           errorsDetected = true;
	       }

	       return errorsDetected;
	}
}
