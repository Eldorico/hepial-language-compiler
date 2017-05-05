package abstractTree.instruction;

import abstractTree.expression.Expression;

/**
 * @description: This class represents a write Instruction. (Such a println() method).
 *
 */
public class WriteInstruction extends Instruction {

	Expression output;

	public WriteInstruction(Expression output, int declarationLineNumber) {
	    super(declarationLineNumber);
		this.output = output;
	}

	@Override
	public String toString() {
		return String.format("Write: %s", output);
	}

    /**
     * @description: checks for semantic errors into the output
     */
    @Override
    public boolean semanticErrorsDetected(){
        return output.semanticErrorsDetected(declarationLineNumber);
    }

}
