package abstractTree.instruction;

import abstractTree.expression.Identifier;

public class ReadInstruction extends Instruction {

	Identifier dst;

	public ReadInstruction(Identifier dst, int declarationLineNumber) {
	    super(declarationLineNumber);
		this.dst = dst;
	}

	@Override
	public String toString() {
		return String.format("Read: destination = %s", dst.toString());
	}

}
