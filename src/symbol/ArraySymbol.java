package symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import utils.ErrorPrinter;
import utils.ExpressionEvaluator;
import abstractTree.expression.ArithmeticExpression;
import abstractTree.expression.Expression;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;

public class ArraySymbol extends VariableSymbol {

	ArrayList<SimpleEntry< Expression, Expression>> dimensionsList; // Array[Expression .. Expression]

	public ArraySymbol(int declarationLinesNumber, Type type, ArrayList< SimpleEntry< Expression, Expression>> dimensionsList) {
		super(declarationLinesNumber, type);
		this.dimensionsList = dimensionsList;
	}

	@Override
	public String toString(){
		String strToReturn = String.format("Array %s. No Ligne: %d. Dimensions: ", getType(), declarationLineNumber);
		for(SimpleEntry<Expression, Expression> entry : dimensionsList){
			strToReturn += String.format("[%s..%s]", entry.getKey().toString(), entry.getValue().toString());
		}
		return strToReturn;
	}

    /**
     * @descrpition: check that every [Expression .. Expression] is a Number or an Arithmetic expression.
     *  if errors or incoherences are found, logs the errors and return true
     */
    @Override
    public boolean semanticErrorsDetected() {
        boolean errorsDetected = false;
        // for each boundaries
        for(int i=0; i<dimensionsList.size(); i++){
            // check if a boundary expression is undefined, or not an Integer
            if(!logErrorIfUndefinedOrNotInteger(dimensionsList.get(i).getKey(), 1, i+1)){
                errorsDetected = true;
            }
            if(!logErrorIfUndefinedOrNotInteger(dimensionsList.get(i).getValue(), 2, i+1)){
                errorsDetected = true;
            }

            // check that the elements composing the boundaries are cohérent. (they are composed of Integer or arithmetic expressions
            if(!logErrorIfBoundaryComponentsNotCoherents(dimensionsList.get(i).getKey(), 1, i+1)){
                errorsDetected = true;
            }
            if(!logErrorIfBoundaryComponentsNotCoherents(dimensionsList.get(i).getValue(), 2, i+1)){
                errorsDetected = true;
            }

        }
        return errorsDetected;
    }

    /**
     * @description:
     * @param boundary
     * @param expressionNumber for logging: 1 for the left boundary, 2 for the right boundary
     * @param dimensionNumber for logging: the dimension number. [dimension1][dimension2] etc.
     * @return
     */
    private boolean logErrorIfBoundaryComponentsNotCoherents(Expression boundary, int expressionNumber, int dimensionNumber){
        Class [] expectedSymbolClasses = {IntBoolSymbol.class};
        Class [] expectedExpressionClasses = {IntNumber.class, ArithmeticExpression.class, Identifier.class};
        if(!ExpressionEvaluator.expressionContainsOnly(expectedExpressionClasses, expectedSymbolClasses, boundary)){
            ErrorPrinter.getInstance().logError("The expression "+expressionNumber+" of the dimension "+dimensionNumber+" should only be composed of arithmetic expression or Integer variables", declarationLineNumber);
            return false;
        }else{
            return true;
        }

    }

    /**
     * @description: checks if an expression is undefined, or if it isnt an Integer. // TODO: maybe move this function to ExpressionEvaluator
     *  If it is undefined or isnt an Integer, it logs an error to the ErrorPrinter and returns false;
     * @param expression: the expression to check.
     * @param expressionNumber for logging: 1 for the left boundary, 2 for the right boundary
     * @param dimensionNumber for logging: the dimension number. [dimension1][dimension2] etc.
     * @return
     */
    private boolean logErrorIfUndefinedOrNotInteger(Expression expression, int expressionNumber, int dimensionNumber){
        // check that the expression is an Integer expression
        Type expressionType = expression.getType();
        if(expressionType == null){
            ErrorPrinter.getInstance().logError(expression.toString()+" : Expression undefined", this.declarationLineNumber);
            return false;
        }else if(expressionType == Type.INTEGER){
            return true;
        }else{
            ErrorPrinter.getInstance().logError("The expression "+expressionNumber+" of the dimension "+dimensionNumber+" should be an Integer expression", this.declarationLineNumber);
            return false;
        }
    }

}
