package abstractTree.instruction;

import abstractTree.AbstractTree;

/**
 * @description:
 * This class represents instructions such as:
 * asdf = asdf;
 * {asdf = asdf; asdf2 = asdf2; asdf3 = asdf3} // inscructions's list
 * fct(asdf, asdf2);
 * si asdf == asdf alors asdf = asdf1; sinon asdf = asdf2; finsi
 *
 * It is the parent of:
 * AffectationInstruction
 * ConditionInstruction
 * WhileInstruction
 * FctCall
 * BlocInstructions
 *
 */
public abstract class Instruction extends AbstractTree {

}
