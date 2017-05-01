package symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import utils.ErrorPrinter;
import utils.NumberExpressionEvaluator;
import abstractTree.expression.Expression;

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
     * @descrpition: check that every [Expression .. Expression] is a Number or an Arithmetic operation,
     *  It also calls semanticErrorsDetected of every Expression it has.
     */
    @Override
    public boolean semanticErrorsDetected() {
        boolean errorsDetected = false;
        for(int i=0; i<dimensionsList.size(); i++){
            if(errorsDetectedOnExpression(dimensionsList.get(i).getKey(), 1, i+1)){
                errorsDetected = true;
            }
            if(errorsDetectedOnExpression(dimensionsList.get(i).getValue(), 2, i+1)){
                errorsDetected = true;
            }
        }
        return errorsDetected;
    }

    /**
     * @description:
     * @param expression
     * @param expressionNumber
     * @param dimensionNumber
     * @return
     */
    private boolean errorsDetectedOnExpression(Expression expression, int expressionNumber, int dimensionNumber){
        boolean errorsDetected = false;

        // check that the expression is an Integer expression
        int evaluationResult = NumberExpressionEvaluator.isIntegerExpression(expression);
        switch(evaluationResult){
            case 0:
                ErrorPrinter.getInstance().logError("The expression "+expressionNumber+" of the dimension "+dimensionNumber+" should be an Integer expression", this.declarationLineNumber);
                errorsDetected = true;
                break;
            case -1:
                ErrorPrinter.getInstance().logError("Expression not defined: "+expression.toString(), this.declarationLineNumber);
                errorsDetected = true;
                break;
        }
        return errorsDetected;
    }

}
