package abstractTree.expression;

import symbol.Type;
import utils.ErrorPrinter;

/**
 * @description: represents relationals expression that can have a boolean or integer types
 */
public abstract class RelationalMixedExpression extends RelationalExpression {

    public RelationalMixedExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * @description: checks for semantic errors into the operands
     * checks if the types are the same for the left and right operand.
     * Errors will be logged into the ErrorPrinter
     *
     * @return: true if any error has been detected. false if not.
     */
    @Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
        // check for semantic errors on the left and right operand. (It will log errors if any found)
        boolean errorsDetected = super.semanticErrorsDetected(declarationLineNumber);

        // check that the left and right operand have the same type
        Type leftOperandType = leftOperand.getType();
        Type rightOperandType = rightOperand.getType();
        if(leftOperandType != rightOperandType){
            ErrorPrinter.getInstance().logError(this.toString()+" : the types have to be the same.", declarationLineNumber);
            errorsDetected = true;
        }

        return errorsDetected;
     }
}
