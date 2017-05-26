package abstractTree.expression;

import symbol.Symbol;
import symbol.Type;
import utils.ErrorPrinter;
import utils.ExpressionEvaluator;

/**
 * @description:
 *	This class represents Arithmetic expressions such as:
 *	asdf + asdf
 *  asdf - 1234
 *	asdf * asdf
 *	asdf / asdf
 *
 *	It is the parent of
 * - AdditionExpression
 * - SubstractionExpression
 * - MultiplyExpression
 * - DivideExpression
 *
 */
public abstract class ArithmeticExpression extends BinaryExpression {

	public ArithmeticExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

   /**
    *
    */
   @Override
   public Type getType(){
       return Type.INTEGER;
   }

   @Override
   public abstract Integer evaluateIntValue();

  /**
   * @description: checks for semantic errors for the left and right operand
   * checks that the Types of the left and right operand are Integers
   * logs Error into ErrorPrinter if found errors
   */
   @Override
   public boolean semanticErrorsDetected(int declarationLineNumber){
       // check for semantic errors on the left and right operand. (It will log errors if any found)
       boolean errorsDetected = super.semanticErrorsDetected(declarationLineNumber);

       // checks that the types of the left and right operand are Integers
       if(leftOperand.getType() != Type.INTEGER){
           ErrorPrinter.getInstance().logError(leftOperand.toString()+" : Operand has to be an Integer", declarationLineNumber);
           errorsDetected = true;
       }
       if(rightOperand.getType() != Type.INTEGER){
           ErrorPrinter.getInstance().logError(rightOperand.toString()+" : Operand has to be an Integer", declarationLineNumber);
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
