package abstractTree.expression;

import symbol.ArraySymbol;
import symbol.CstIntBoolSymbol;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import utils.ErrorPrinter;
import codeProduction.JEvaluator;

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
	 * @description: checks that the identifier is defined
	 * @return
	 */
	@Override
	public boolean semanticErrorsDetected(int declarationLineNumber){
	       VariableSymbol identifiersSymbol = (VariableSymbol)SymbolTable.getInstance().getSymbol(name);
	        if(identifiersSymbol == null){
	            ErrorPrinter.getInstance().logError(name+" : expresssion undefined.", declarationLineNumber);
	            return true;
	        }

	        boolean errorsDetected = false;

	        // if the symbol associated to dist is
	        Symbol symbol = SymbolTable.getInstance().getSymbol(name);
	        if(symbol instanceof ArraySymbol){
	            if( !(this instanceof TabValueIdentifier) ){
	                ErrorPrinter.getInstance().logError(this.name+" is an array and should be accessed like it. ", declarationLineNumber);
	                errorsDetected =  true;
	            }
	        }

	        return errorsDetected;

	}

	@Override
    public Integer evaluateIntValue(){
	    Symbol symbolAssociated = SymbolTable.getInstance().getSymbol(name);
	    if(symbolAssociated instanceof CstIntBoolSymbol){
	        CstIntBoolSymbol cstIntBoolSymbol = (CstIntBoolSymbol) symbolAssociated;
	        return cstIntBoolSymbol.getExpression().evaluateIntValue();
	    }else{
	        return null;
	    }
	}

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }

}
