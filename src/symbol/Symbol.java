package symbol;

/**
 * @description:
 *
 */
public abstract class Symbol {

	int declarationLinesNumber;

	public Symbol(int declarationLinesNumber) {
		this.declarationLinesNumber = declarationLinesNumber+1;
	}

	public int getDeclarationLinesNumber(){
		return declarationLinesNumber;
	}
}
