package symbol;

/**
 * @description: its the parent of
 * IntBoolSymbol
 * ArraySymbol
 *
 */
public abstract class VariableSymbol extends Symbol {

	Type type;

	public VariableSymbol(int declarationLinesNumber, Type type, String blockName) {
		super(declarationLinesNumber, blockName);
		this.type = type;
	}

	public String getType(){
		switch(type){
			case BOOLEAN:
				return new String("boolean");
			case INTEGER:
				return new String("integer");
			default:
				return null;
		}
	}

	public Type type(){
		return type;
	}
}
