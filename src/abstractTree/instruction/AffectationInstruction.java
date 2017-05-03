package abstractTree.instruction;

import abstractTree.expression.Expression;
import abstractTree.expression.Identifier;

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
	Identifier dst;

	/**
	 * @description: Affectation is like: dst = src;
	 * @param dst
	 * @param src
	 */
	public AffectationInstruction(Identifier dst, Expression src, int declarationLineNumber) {
	    super(declarationLineNumber);
		this.dst = dst;
		this.src = src;
	}

	@Override
	public String toString() {
		return String.format("Affectation: %s<-%s\n", dst.toString(), src.toString());
	}

	/**
     * @description: checks if the dst is associated to a VariableSymbol. (not a function symbol)
     *  Checks if the dst type and the src type are the same.
     *  Checks if dst and the src can be defined.
     *  Logs the errors into the ErrorPrinter.
     * @return: true if an error has been detected. True if not.
	 */
//	@Override
//	public boolean semanticErrorsDetected(){
//	    boolean errorsDetected = false;
//
//	    // get the src and dst type
//	    Type srcType = src.getType();
//	    Type dstType = dst.getType();
//
//	    // check if dst and src are undefined and return if one is undefined.
//	    if(srcType == null){
//	        ErrorPrinter.getInstance().logError(srcType.toString()+" : Expression undefined", declarationLineNumber);
//	    }
//	}

}
