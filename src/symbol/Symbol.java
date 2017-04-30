package symbol;

/**
 * @description:
 *
 */
public abstract class Symbol {

	int declarationLineNumber;

	public Symbol(int declarationLinesNumber) {
		this.declarationLineNumber = declarationLinesNumber+1;
	}

	public int getDeclarationLinesNumber(){
		return declarationLineNumber;
	}

	abstract public boolean semanticErrorsDetected();
}
