package abstractTree.expression;

import symbol.Type;
import utils.ErrorPrinter;

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

  /**
   * @description: checks for semantic errors for the left and right operand
   * checks that the Types of the left and right operand are Integers
   * logs Error into ErrorPrinter if found errors
   */
   @Override
   public boolean semanticErrorsDetected(int declarationLineNumber){
       boolean errorsDetected = false;

       // check for semantic errors
       if(leftOperand.semanticErrorsDetected(declarationLineNumber)){
           errorsDetected = true;
       }
       if(rightOperand.semanticErrorsDetected(declarationLineNumber)){
           errorsDetected = true;
       }

       // checks that the types of the left and right operand are Integers
       if(leftOperand.getType() != Type.INTEGER){
           ErrorPrinter.getInstance().logError(leftOperand.toString()+" : Operand has to be an Integer", declarationLineNumber);
           errorsDetected = true;
       }
       if(rightOperand.getType() != Type.INTEGER){
           ErrorPrinter.getInstance().logError(rightOperand.toString()+" : Operand has to be an Integer", declarationLineNumber);
           errorsDetected = true;
       }

       return errorsDetected;
   }
}
