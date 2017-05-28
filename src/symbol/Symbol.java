package symbol;

/**
 * @description:
 *
 */
public abstract class Symbol {

	int declarationLineNumber;
	String blockName = null;

	public Symbol(int declarationLinesNumber) {
		this.declarationLineNumber = declarationLinesNumber+1;
	}

	public int getDeclarationLinesNumber(){
		return declarationLineNumber;
	}

	abstract public boolean semanticErrorsDetected();

	public String getBlockName(){
	    return blockName;
	}

	void setBlockName(String currentBlockName){
	    blockName = currentBlockName;
	}
}
