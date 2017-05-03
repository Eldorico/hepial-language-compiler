package abstractTree.instruction;

import abstractTree.expression.Expression;

public class IfInstruction extends Instruction {

	Expression condition;
	BlocInstruction thenInstructions;
	BlocInstruction elseInstructions;

	public IfInstruction(Expression condition, BlocInstruction thenInstructions, BlocInstruction elseInstructions, int declarationLineNumber) {
	    super(declarationLineNumber);

		this.condition = condition;
		this.thenInstructions = thenInstructions;
		this.elseInstructions = elseInstructions;
	}

	@Override
	public String toString() {
		return String.format("IfInstruction: (%s) ?\nThen: \n%sElse:\n%sEndIfInstruction\n", condition.toString(), thenInstructions.toString(), elseInstructions.toString());
	}

}
