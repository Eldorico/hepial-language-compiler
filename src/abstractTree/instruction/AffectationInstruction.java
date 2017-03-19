package abstractTree.instruction;

import abstractTree.expression.Expression;

/**
 * @descrition:
 * This class respresents an affectation such as:
 * asdf = asdf;
 * asdf = 1234;
 * asdf = ftc(asdf, asdf1);
 * asdf = tab[i];
 * asdf = 1234 + 324525;
 *
 * In short:
 * identifier | tab[i] = Expression.
 *
 */
public class AffectationInstruction extends Instruction {

	Expression src;
	Expression dst; // TODO: AffectationInstrcution.dst : dst should be an Identifier or a tab[i] or something like that..

	/**
	 * @description: Affectation is like: dst = src;
	 * @param dst
	 * @param src
	 */
	public AffectationInstruction(Expression dst, Expression src) {
		this.dst = dst;
		this.src = src;
	}

	@Override
	public String toString() {
		return String.format("Affectation: %s<-%s\n", dst.toString(), src.toString());
	}

}
