package abstractTree.instruction;

import abstractTree.expression.Identifier;

public class ReadInstruction extends Instruction {

	Identifier dst;

	public ReadInstruction(Identifier dst) {
		this.dst = dst;
	}

	@Override
	public String toString() {
		return String.format("Read: destination = %s\n", dst.toString());
	}

}
