package symbol;

import utils.ErrorPrinter;
import utils.ExpressionEvaluator;
import abstractTree.expression.ArithmeticExpression;
import abstractTree.expression.BooleanKeyword;
import abstractTree.expression.Expression;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;
import abstractTree.expression.RelationalExpression;
import abstractTree.expression.UnaryExpression;

public class CstIntBoolSymbol extends IntBoolSymbol {

	Expression value;

	public CstIntBoolSymbol(int declarationLinesNumber, Type type, Expression value) {
		super(declarationLinesNumber, type);
		this.value = value;
	}

	@Override
	public String toString(){
		return String.format("Const Variable %s. No Ligne: %d. Valeur: %s", getType(), declarationLineNumber, value.toString());
	}

	/**
	 * @description:   checks the semantic declaration of a Integer of Boolean Constant.
	 *     It checks that the expression type matches the Symbol type, and that the expression is a combination of constants.
	 * @return true if an error has been detected. Else, returns false
	 */
	@Override
    public boolean semanticErrorsDetected(){
	    // check if the expression is undefined
	    Type expressionType = value.getType();
	    if(expressionType == null){
	        ErrorPrinter.getInstance().logError(this.value.toString()+" : Expression undefined", declarationLineNumber);
	        return true;
	    }

        // check that the expression type matches the Symbol type
	    if(this.type != expressionType){
            ErrorPrinter.getInstance().logError("Value has to be a "+Type.strType(this.type)+" value.", declarationLineNumber);
            return true;
        }

        // check that the expression is composed of coherent constant components
        Class [] expectedSymbolClasses = {CstIntBoolSymbol.class};

        //  if integer: check is only made of Numbers, arithmetics operations, and constants identifiers
        if(type == Type.INTEGER){
            Class [] expectedCstIntegerExpressionClasses = {IntNumber.class, ArithmeticExpression.class, Identifier.class};
            if(ExpressionEvaluator.expressionContainsOnly(expectedCstIntegerExpressionClasses, expectedSymbolClasses, value)){
               return false;
            }else{
                ErrorPrinter.getInstance().logError("Right assignment has to be a "+Type.strType(this.type)+" constant expression.", declarationLineNumber);
                return true;
            }
        // if boolean: check is only made of IntNumber, ArithmeticExpression, constants Identifier for Integers
        }else{
            Class [] expectedCstBooleanExpressionClasses = {BooleanKeyword.class, RelationalExpression.class, UnaryExpression.class, Identifier.class};
            if(ExpressionEvaluator.expressionContainsOnly(expectedCstBooleanExpressionClasses, expectedSymbolClasses, value)){
                return false;
             }else{
                 ErrorPrinter.getInstance().logError("Right assignment has to be a "+Type.strType(this.type)+" constant expression.", declarationLineNumber);
                 return true;
             }
        }

        }


}
