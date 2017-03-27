package abstractTree.symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import abstractTree.instruction.BlocInstruction;

public class FunctionSymbol extends Symbol {

	Type returnType;
	ArrayList<SimpleEntry<String, VariableSymbol>> parameters;
	BlocInstruction instructions;

	public FunctionSymbol(int declarationLinesNumber, Type returnType, ArrayList<SimpleEntry<String, VariableSymbol>> parameters, BlocInstruction instructions) {
		super(declarationLinesNumber);
		this.returnType = returnType;
		this.parameters = parameters;
		this.instructions = instructions;
	}

	@Override
	public String toString(){
		String strToReturn =  String.format("Function. Return type: %s. No Ligne: %d. Parametres:\n", getReturnType(), declarationLinesNumber);
		for(SimpleEntry<String, VariableSymbol> parameter: parameters){
			strToReturn += String.format("%s: (%s)\n", parameter.getKey(), parameter.toString());
		}
		strToReturn += String.format("Instructions:\n%s",instructions.toString());
		return strToReturn;
	}

	public String getReturnType(){
		switch(returnType){
			case BOOLEAN:
				return new String("boolean");
			case INTEGER:
				return new String("integer");
			default:
				return null;
		}
	}

	Type returnType(){
		return this.returnType;
	}
}
