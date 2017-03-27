package abstractTree.instruction;

import abstractTree.expression.Expression;

/**
 * @description: This class represents a write Instruction. (Such a println() method).
 *
 */
public class WriteInstruction extends Instruction {

	Expression output;

	public WriteInstruction(Expression output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return String.format("Write: output = %s\n", output);
	}

}