package abstractTree.instruction;

import symbol.CstIntBoolSymbol;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import utils.ErrorPrinter;
import utils.ExpressionEvaluator;
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

	public Identifier getDestination(){
	    return dst;
	}

	public Expression getSource(){
	    return src;
	}

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
     *  Checks that we dont affect a constant
     *  Logs the errors into the ErrorPrinter.
     * @return: true if an error has been detected. True if not.
	 */
	@Override
	public boolean semanticErrorsDetected(){
	    boolean errorsDetected = false;

	    // check the semantic of the src and dst. (it will log errors if found)
	    if(src.semanticErrorsDetected(declarationLineNumber) || dst.semanticErrorsDetected(declarationLineNumber)){
	        errorsDetected = true;
	    }

        // if errors detected, return here because if we have some undefined expressions, the rest will crash due to reflexive operations. (invalid file like errors)
        if(errorsDetected){
            return errorsDetected;
        }

        // check that the dst and src type are the same
        Type srcType = src.getType();
        Type dstType = dst.getType();
        if(srcType != dstType){
            ErrorPrinter.getInstance().logError("The affectation must be an "+Type.strType(dst.getType())+" expression", declarationLineNumber);
            errorsDetected = true;
        }

        // check that the dst is a VariableSymbol
        Class [] expectedSymbolClasses = {VariableSymbol.class};
        Class [] expectedExpressionClasses = {Identifier.class};
        if(!ExpressionEvaluator.expressionContainsOnly(expectedExpressionClasses, expectedSymbolClasses, dst)){
            ErrorPrinter.getInstance().logError("Left assignment has to be a variable identifier", declarationLineNumber);
            errorsDetected = true;
        }

        // check that we dont affect a constant
        Symbol symbolDst = SymbolTable.getInstance().getSymbol(dst.getName());
        if(symbolDst instanceof CstIntBoolSymbol){
            ErrorPrinter.getInstance().logError("Left assignment cannot be a constant", declarationLineNumber);
            errorsDetected = true;
        }

        return errorsDetected;

	}

}
