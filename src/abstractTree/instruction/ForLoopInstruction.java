package abstractTree.instruction;

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

	@Override
	public boolean semanticErrorsDetected(){
	    // TODO: semanticErrorsDetected
	    return false;
	}

}
