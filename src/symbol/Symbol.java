package symbol;

/**
 * @description:
 *
 */
public abstract class Symbol {

	int declarationLineNumber;
	String blockName;

	public Symbol(int declarationLinesNumber, String blockName) {
		this.declarationLineNumber = declarationLinesNumber+1;
		this.blockName = blockName;
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
