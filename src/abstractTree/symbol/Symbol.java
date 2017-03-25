package abstractTree.symbol;

/**
 * @description:
 *
 */
public abstract class Symbol {

	int declarationLinesNumber;

	public Symbol(int declarationLinesNumber) {
		this.declarationLinesNumber = declarationLinesNumber;
	}

	public int getDeclarationLinesNumber(){
		return declarationLinesNumber;
	}
}
