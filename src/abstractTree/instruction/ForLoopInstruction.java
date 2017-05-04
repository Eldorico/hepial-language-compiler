package abstractTree.instruction;

import symbol.Symbol;
import symbol.SymbolTable;
import symbol.Type;
import utils.ErrorPrinter;
import abstractTree.expression.Expression;
import abstractTree.expression.Identifier;

/**
 * @description:
 * This class represents a for loop.
  * for i from lowerBound to upperBound, do:
 * 	instructions
 * endfor
 *
 */
public class ForLoopInstruction extends Instruction {

	Identifier i;
	Expression lowerBound;
	Expression upperBound;
	BlocInstruction instructions;

	public ForLoopInstruction(Identifier i, Expression lowerBound, Expression upperBound, BlocInstruction instructions, int declarationLineNumber) {
	    super(declarationLineNumber);

		this.i = i;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return String.format("ForLoopInstruction: for (%s) from (%s) to (%s), do:\n%sEndForLoopInstruction\n", i.toString(), lowerBound.toString(), upperBound.toString(), instructions.toString());
	}

	/**
	 * @description: checks that the i, lowerBound and upperBound are defined, and of type Integer.
	 *  Checks that the i isn't a constant.
	 *  Calls also the BlocInstruction.semanticErrorsDetected()
	 * @return: true if some errors have been detected.
	 */
	@Override
	public boolean semanticErrorsDetected(){
	    boolean errorsDetected = false;

	    // get the type of i, lowerBound, upperBound
	    Type iType = i.getType();
	    Type lowerBoundType = lowerBound.getType();
	    Type upperBoundType = upperBound.getType();

	    // log errors if a type isnt defined and return here if an error is detected.
	    if(iType == null){
	        ErrorPrinter.getInstance().logError(i.toString()+" : identifier not defined", declarationLineNumber);
	        errorsDetected = true;
	    }
        if(lowerBoundType == null){
            ErrorPrinter.getInstance().logError(lowerBound.toString()+" : lower bound is not defined, or has mixed types content", declarationLineNumber);
            errorsDetected = true;
        }
        if(upperBoundType == null){
            ErrorPrinter.getInstance().logError(upperBound.toString()+" : upper bound is not defined, or has mixed types content", declarationLineNumber);
            errorsDetected = true;
        }
        if(errorsDetected){
            return errorsDetected;
        }

        // log error if the types are not correct
        if(iType != Type.INTEGER){
            ErrorPrinter.getInstance().logError(i.toString()+" : identifier has to be an integer.", declarationLineNumber);
            errorsDetected = true;
        }
        if(lowerBoundType != Type.INTEGER){
            ErrorPrinter.getInstance().logError(lowerBound.toString()+" : lower bound has to be an integer expression", declarationLineNumber);
            errorsDetected = true;
        }
        if(upperBoundType != Type.INTEGER){
            ErrorPrinter.getInstance().logError(upperBound.toString()+" : upper bound has to be an integer expression", declarationLineNumber);
            errorsDetected = true;
        }

        // log error if i is not an IntBoolSymbol
        Symbol iSymbol = SymbolTable.getInstance().getSymbol(i.getName());
        if(!iSymbol.getClass().getSimpleName().equals("IntBoolSymbol")){
            ErrorPrinter.getInstance().logError(i.toString()+" : identifier has to be an integer variable. (not an array nor a constant)", declarationLineNumber);
            errorsDetected = true;
        }

        // checks the bloc instruction
        if(instructions.semanticErrorsDetected()){
            errorsDetected = true;
        }

	    return errorsDetected;
	}

}
