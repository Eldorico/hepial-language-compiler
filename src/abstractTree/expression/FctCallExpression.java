package abstractTree.expression;


/**
 * This class represents a function call such as:
 * fctName();
 * fctName(parm, parm);
 *
 * It is the parent of the class AffectationFctCallInstruction
 *
 */
public class FctCallExpression extends Expression {

	Identifier fctName;
	ExpressionList parameters;

	public FctCallExpression(Identifier fctName, ExpressionList parameters) {
		this.fctName = fctName;
		this.parameters = parameters;
	}

	public FctCallExpression(Identifier fctName){
		this.fctName = fctName;
		this.parameters = new ExpressionList();
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", fctName.toString(), parameters.toString(false));
	}

}
