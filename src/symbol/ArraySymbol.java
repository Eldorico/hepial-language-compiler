package symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import utils.ErrorPrinter;
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
     * @descrpition: check that every [Expression .. Expression] is a Number or an Arithmetic expression.
     *  if errors or incoherences are found, logs the errors and return true
     */
    @Override
    public boolean semanticErrorsDetected() {
        boolean errorsDetected = false;
        // for each boundaries
        for( int i=0; i<dimensionsList.size(); i++){
            // get the boundaries expression
            Expression lowerBound = dimensionsList.get(i).getKey();
            Expression upperBound = dimensionsList.get(i).getValue();

            // check for semantic errors into the boundaries (it will log their errors if any)
            if(lowerBound.semanticErrorsDetected(declarationLineNumber) || upperBound.semanticErrorsDetected(declarationLineNumber)){
                errorsDetected = true;
            }

            // check that the types are Integers
            Type lowerBoundType = lowerBound.getType();
            if(lowerBoundType != Type.INTEGER){
                ErrorPrinter.getInstance().logError(lowerBound.toString()+" : lowerbound of dimension "+i+" has to be an Integer type", declarationLineNumber);
                errorsDetected = true;
            }
            Type upperBoundType = upperBound.getType();
            if(upperBoundType != Type.INTEGER){
                ErrorPrinter.getInstance().logError(upperBound.toString()+" : upperbound of dimension "+i+" has to be an Integer type", declarationLineNumber);
                errorsDetected = true;
            }
        }
        return errorsDetected;
    }

    /**
     * @description: returns the number of dimensions of the array
     * @return the number of dimensions of the array
     */
    public int getNbDimensions(){
        return dimensionsList.size();
    }
}
