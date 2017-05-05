package abstractTree.expression;

import symbol.Symbol;
import symbol.Type;
import utils.ErrorPrinter;
import utils.ExpressionEvaluator;

/**
 * @description: represents relationals expression that can only have integer type
 */
public abstract class RelationalIntegerExpression extends RelationalExpression {

    public RelationalIntegerExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * @description: checks that the left and right operand are integer expressions and can be defined
     * checks that the left and right operand are Integer or arithmetic expressions
     * Logs the errors into the ErrorPrinter
     * @return true if an error has been detected. Else returns false.
     */
    @Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
        // check for semantic errors on the left and right operand. (It will log errors if any found)
        boolean errorsDetected = super.semanticErrorsDetected(declarationLineNumber);

        // check that the left and right operand are Integer types
        if(leftOperand.getType() != Type.INTEGER){
            ErrorPrinter.getInstance().logError(leftOperand.toString()+" : The left operand has to be an Integer type.", declarationLineNumber);
            errorsDetected = true;
        }
        if(rightOperand.getType() != Type.INTEGER){
            ErrorPrinter.getInstance().logError(rightOperand.toString()+" : The left operand has to be an Integer type.", declarationLineNumber);
            errorsDetected = true;
        }

        // if any errors has been detected return here because it can crash the program. (If an expression is undefined, it will crash because of the reflective operations below
        if(errorsDetected){
            return errorsDetected;
        }

        // check that the left and right operands are only made of Arithmetic Expressions, IntNumber, Identifiers or FctCallExpression
        Class [] expectedSymbolClasses = {Symbol.class};
        Class [] expectedExpressionClasses = {ArithmeticExpression.class, Identifier.class, FctCallExpression.class, IntNumber.class};
        if(!ExpressionEvaluator.expressionContainsOnly(expectedExpressionClasses, expectedSymbolClasses, leftOperand)){
            ErrorPrinter.getInstance().logError(leftOperand.toString()+"Left operand has to be an Arithmetic Expression, a fctCall, or an Identifier", declarationLineNumber);
            errorsDetected = true;
        }
        if(!ExpressionEvaluator.expressionContainsOnly(expectedExpressionClasses, expectedSymbolClasses, rightOperand)){
            ErrorPrinter.getInstance().logError(rightOperand.toString()+"Right operand has to be an Arithmetic Expression, a fctCall, or an Identifier", declarationLineNumber);
            errorsDetected = true;
        }

        return errorsDetected;
    }


}
