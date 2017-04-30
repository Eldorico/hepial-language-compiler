package symbol;


public class IntBoolSymbol extends VariableSymbol {

	public IntBoolSymbol(int declarationLineNumber, Type type) {
		super(declarationLineNumber, type);
	}

	@Override
	public String toString(){
		return String.format("Variable %s. No Ligne: %d", getType(), declarationLineNumber);
	}

	@Override
	public boolean semanticErrorsDetected(){
	    //TODO IntBoolSymbol.semanticErrorsDetected(): je sais pas si faut la faire celle ci
	    return false;
	}

}
