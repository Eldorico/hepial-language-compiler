package symbol;


public class IntBoolSymbol extends VariableSymbol {

	public IntBoolSymbol(int declarationLinesNumber, Type type) {
		super(declarationLinesNumber, type);
	}

	@Override
	public String toString(){
		return String.format("Variable %s. No Ligne: %d", getType(), declarationLinesNumber);
	}

}
