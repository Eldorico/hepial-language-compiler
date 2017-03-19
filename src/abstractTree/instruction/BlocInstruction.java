package abstractTree.instruction;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @description:
 * This class represents a list of instructions.
 *
 */
public class BlocInstruction extends Instruction {

	LinkedList<Instruction> instructionList;

	public BlocInstruction(Instruction instruction) {
		// if the instruction is a BlocInstruction, print error. (it should not be constructed
		// with a BlocInstruction from the parser. Use the returnWithNewInstruction() instead.
		if(instruction.getClass().equals(BlocInstruction.class)){
			System.err.printf("BlocInstruction.constructor: instruction is allready a BlockInstruction. BlockInstruction should be constructed with a BlocInstruction from the parser. Use the returnWithNewInstruction() instead.");
		}

		this.instructionList = new LinkedList<Instruction>();
		this.instructionList.add(instruction);
	}

	/**
	 * @description: adds the instruction to the list
	 * @param instruction: the instruction to add to the BlocInstruction
	 */
	public void addInstruction(Instruction instruction){
		// if the instruction is a BlocInstruction, print error.
		if(instruction instanceof BlocInstruction){
			System.err.printf("BlocInstruction.returnWithNewInstruction(): instruction is allready a BlockInstruction. BlockInstruction should not be added to a BlockInstruction from the parser I think it is an error.");
		}
		instructionList.add(instruction);
	}

	@Override
	public String toString() {
		String stringToReturn = new String("");
		Iterator<Instruction> i = instructionList.iterator();
		while(i.hasNext()){
			stringToReturn += i.next().toString();
		}
		return stringToReturn;
	}

}
