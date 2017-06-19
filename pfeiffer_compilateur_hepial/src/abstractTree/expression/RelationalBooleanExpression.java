package abstractTree.expression;

import symbol.Type;
import utils.ErrorPrinter;

/**
 * @description this class represents Relational expression that can only have boolean types.
 */
public abstract class RelationalBooleanExpression extends RelationalExpression {

    public RelationalBooleanExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * @description: checks for semantic errors into the left and right operand
     * checks that the left and right operand are Boolean types
     */
    @Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
        // check for semantic errors on the left and right operand. (It will log errors if any found)
        boolean errorsDetected = super.semanticErrorsDetected(declarationLineNumber);

        // checks that the types of the left and right operand are Integers
        if(leftOperand.getType() != Type.BOOLEAN){
            ErrorPrinter.getInstance().logError(leftOperand.toString()+" : Operand has to be a boolean", declarationLineNumber);
            errorsDetected = true;
        }
        if(rightOperand.getType() != Type.BOOLEAN){
            ErrorPrinter.getInstance().logError(rightOperand.toString()+" : Operand has to be a boolean value", declarationLineNumber);
            errorsDetected = true;
        }

        return errorsDetected;
    }

}
