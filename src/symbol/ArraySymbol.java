package symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import abstractTree.expression.Expression;

public class ArraySymbol extends VariableSymbol {

	ArrayList<SimpleEntry< Expression, Expression>> dimensionsList;

	public ArraySymbol(int declarationLinesNumber, Type type, ArrayList< SimpleEntry< Expression, Expression>> dimensionsList) {
		super(declarationLinesNumber, type);
		this.dimensionsList = dimensionsList;
	}

	@Override
	public String toString(){
		String strToReturn = String.format("Array %s. No Ligne: %d. Dimensions: ", getType(), declarationLinesNumber);
		for(SimpleEntry<Expression, Expression> entry : dimensionsList){
			strToReturn += String.format("[%s..%s]", entry.getKey().toString(), entry.getValue().toString());
		}
		return strToReturn;
	}

}
