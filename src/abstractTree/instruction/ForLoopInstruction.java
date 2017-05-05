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
	 * @description: checks for semantic errors into the i, lowerBound and upperBound
	 *  Checks that the i isn't a constant.
	 *  Checks that the lowerbound and upperbound are integers expressions
	 *  Calls also the instructions.semanticErrorsDetected()
	 * @return: true if some errors have been detected.
	 */
	@Override
	public boolean semanticErrorsDetected(){
	    boolean errorsDetected = false;

	    // check for semantic errors into i, lowerBound and upperBound
	    if(i.semanticErrorsDetected(declarationLineNumber)
	       || lowerBound.semanticErrorsDetected(declarationLineNumber)
	       || upperBound.semanticErrorsDetected(declarationLineNumber)){
	        errorsDetected = true;
	    }

        // log error if the types are not correct
        if(i.getType() != Type.INTEGER){
            ErrorPrinter.getInstance().logError(i.toString()+" : identifier has to be an integer.", declarationLineNumber);
            errorsDetected = true;
        }
        if(lowerBound.getType() != Type.INTEGER){
            ErrorPrinter.getInstance().logError(lowerBound.toString()+" : lower bound has to be an integer expression", declarationLineNumber);
            errorsDetected = true;
        }
        if(upperBound.getType() != Type.INTEGER){
            ErrorPrinter.getInstance().logError(upperBound.toString()+" : upper bound has to be an integer expression", declarationLineNumber);
            errorsDetected = true;
        }

        // if errors detected, return here because if we have some undefined expressions, the rest will crash due to reflexive operations. (invalid file like errors)
        if(errorsDetected){
            return errorsDetected;
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
