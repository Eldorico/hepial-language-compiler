package abstractTree.expression;

import symbol.ArraySymbol;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.Type;
import utils.ErrorPrinter;


/**
 * @description:
 * This class represents an identifier of a value of an array at a particular index.
 * It is represented as such:
 *
 * name[indexes] or name[indexes.get(0)][indexes.get(1) etc..
 *
 */
public class TabValueIdentifier extends Identifier {

	ExpressionList indexes;

	public TabValueIdentifier(String value, ExpressionList indexes) {
		super(value);
		this.indexes = indexes;
	}

	@Override
	public String toString(){
		return String.format("%s%s", this.name, this.indexes.toString(true));
	}

	/**
	 * @description: checks that the identifier is defined and return if not
	 * checks that the identifier corresponds to an ArraySymbol
	 * checks that the number of indexes correspond to the dimension of the ArraySymbol
	 * checks that every expression on the expressionList is an Integer expression
	 * checks for semantic errors on every expression on the expressionList is an Integer expression
	 * @return true if any error has been found. Else return false
	 */
	@Override
	public boolean semanticErrorsDetected(int declarationLineNumber){
	    // checks that the identifier is defined and return if not
	    boolean errorsDetected = super.semanticErrorsDetected(declarationLineNumber);
	    if(errorsDetected){
	        return errorsDetected;
	    }

	    // checks that the identifier corresponds to a ArraySymbol
	    Symbol symbol = SymbolTable.getInstance().getSymbol(name);
	    if( !(symbol instanceof ArraySymbol) ){
	        ErrorPrinter.getInstance().logError(this.toString()+" Identifier ist defined as an Array", declarationLineNumber);
	        errorsDetected = true;
	    // if it is an array, checks that the number of indexes correspond to the dimension of the ArraySymbol
	    }else{
	        ArraySymbol arraySymbol = (ArraySymbol) symbol;
	        if(arraySymbol.getNbDimensions() != this.indexes.getSize()){
	            ErrorPrinter.getInstance().logError(this.toString()+" Nb dimensions not matching with the nb dimensions of the declared array on line "+arraySymbol.getDeclarationLinesNumber(), declarationLineNumber);
	            errorsDetected = true;
	        }
	    }
        if(errorsDetected){
            return errorsDetected;
        }

        // checks for semantic errors on every expression on the expressionList is an Integer expression
        if(indexes.semanticErrorsDetected(declarationLineNumber)){
            errorsDetected = true;
        }

        // checks that every expression on the expressionList is an Integer expression
	    for(int i=0; i<indexes.size(); i++){
	        Expression indexExpression = indexes.get(i);
	        if(indexExpression.getType() != Type.INTEGER){
	            ErrorPrinter.getInstance().logError("Index "+(i+1)+" of the "+this.name+" array has to be an integer expression", declarationLineNumber);
	            errorsDetected = true;
	        }
	    }

	    return errorsDetected;
	}

	public int getNbIndexes(){
	    return indexes.getSize();
	}

	public Expression getIndex(int i){
	    return indexes.get(i);
	}
}
