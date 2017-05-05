package abstractTree.expression;

import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import utils.ErrorPrinter;

/**
 * @description:
 * This class represents an identifier. For example, in an expression as 'a = b', 'a' and
 * 'b' are identifiers.
 *
 */
public class Identifier extends Expression {

	String name;

	public Identifier(String value) {
		this.name = value;
	}

	public String getName(){
	    return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Type getType(){
	    VariableSymbol identifiersSymbol = (VariableSymbol)SymbolTable.getInstance().getSymbol(name);
	    if(identifiersSymbol == null){
	        return null;
	    }else{
	        return  identifiersSymbol.type();
	    }
	}


	/**
	 * @description:
	 * @return
	 */
	@Override
	public boolean semanticErrorsDetected(int declarationLineNumber){
	       VariableSymbol identifiersSymbol = (VariableSymbol)SymbolTable.getInstance().getSymbol(name);
	        if(identifiersSymbol == null){
	            ErrorPrinter.getInstance().logError(name+" : expresssion undefined.", declarationLineNumber);
	            return true;
	        }else{
	            return  false;
	        }
	}

}
