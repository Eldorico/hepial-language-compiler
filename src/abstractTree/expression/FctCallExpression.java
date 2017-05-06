package abstractTree.expression;

import symbol.FunctionSymbol;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.Type;
import utils.ErrorPrinter;


/**
 * This class represents a function call such as:
 * fctName();
 * fctName(parm, parm);
 *
 * It is the parent of the class AffectationFctCallInstruction
 *
 */
public class FctCallExpression extends Expression {

	Identifier fctName;
	ExpressionList parameters;

	public FctCallExpression(Identifier fctName, ExpressionList parameters) {
		this.fctName = fctName;
		this.parameters = parameters;
	}

	public FctCallExpression(Identifier fctName){
		this.fctName = fctName;
		this.parameters = new ExpressionList();
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", fctName.toString(), parameters.toString(false));
	}

    @Override
    public Type getType(){
        FunctionSymbol functionsSymbol = (FunctionSymbol)SymbolTable.getInstance().getSymbol(fctName.getName());
        if(functionsSymbol == null){
            return null;
        }else{
            return  functionsSymbol.returnType();
        }
    }

    public Identifier getFctName(){
        return fctName;
    }

    /**
     * @description: calls the semanticExpressions on all parameters
     * checks that the function is defined and it correspond to a FunctionSymbol
     * checks that the number of parameters corresponds to the function declaration
     * checks that the parameters are of the correct type
     */
    @Override
    public boolean semanticErrorsDetected(int declarationLineNumber){
        // calls the semanticExpressions on all parameters
        boolean errorsDetected = parameters.semanticErrorsDetected(declarationLineNumber);

        // check that the function is defined and it correspond to a FunctionSymbol
        Symbol symbol = SymbolTable.getInstance().getSymbol(fctName.name);
        if(symbol == null){
            ErrorPrinter.getInstance().logError(fctName.name+" : function undefined", declarationLineNumber);
            errorsDetected = true;
        }else{
            if(!(symbol instanceof FunctionSymbol)){
                ErrorPrinter.getInstance().logError(fctName.name+" : identifier must correspond to a function", declarationLineNumber);
                errorsDetected = true;
            }
        }

        // if errors are detected here, return here because we can crash the program for the rest of verifications
        if(errorsDetected){
            return errorsDetected;
        }

        // check that the number of parameters corresponds to the function declaration
        FunctionSymbol functionSymbol = (FunctionSymbol) symbol;
        int nbComparison = functionSymbol.getNbParameters() - parameters.getSize();
        if(nbComparison > 0){
            ErrorPrinter.getInstance().logError("Parameters missing", declarationLineNumber);
            return true;
        }else if(nbComparison < 0){
            ErrorPrinter.getInstance().logError("Too many parameters", declarationLineNumber);
            return true;
        }

        // checks that the parameters are of the correct type
        for(int i=0; i<parameters.getSize(); i++){
            if(parameters.get(i).getType() != functionSymbol.getTypeOfParameter(i)){
                ErrorPrinter.getInstance().logError("Parameter "+(i+1)+" has to be an "+Type.strType(functionSymbol.getTypeOfParameter(i))+" type", declarationLineNumber);
                errorsDetected = true;
            }
        }

        return errorsDetected;
    }

}
