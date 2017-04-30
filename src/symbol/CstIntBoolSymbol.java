package symbol;

import utils.ErrorPrinter;
import abstractTree.expression.Expression;

public class CstIntBoolSymbol extends IntBoolSymbol {

	Expression value;

	public CstIntBoolSymbol(int declarationLinesNumber, Type type, Expression value) {
		super(declarationLinesNumber, type);
		this.value = value;
	}

	@Override
	public String toString(){
		return String.format("Const Variable %s. No Ligne: %d. Valeur: %s", getType(), declarationLineNumber, value.toString());
	}

	/**
	 * @description: checks if the CstIntBoolSymbol is really a Boolean or an Integer
	 * @return true if an error has been detected. Else, returns false
	 */
	@Override
    public boolean semanticErrorsDetected(){
	    // if the Symbol is a boolean symbol
	    if(type == type.BOOLEAN){
	        if(this.value.getClass().getSimpleName().equals("BooleanKeyword")){
	            return false;
	        }else{
	            ErrorPrinter.getInstance().logError("Value has to be a boolean value.", declarationLineNumber);
	            return true;
	        }
	    // else, it is an Integer symbol
	    }else{
            if(this.value.getClass().getSimpleName().equals("IntNumber")){
                return false;
            }else{
                ErrorPrinter.getInstance().logError("Value has to be an integer value.", declarationLineNumber);
                return true;
            }
	    }
	}

}
