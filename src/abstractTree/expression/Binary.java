package abstractTree.expression;

/**
 * @description:
 * This class is the parents of binary Expressions such as:
 * asdf = asdf (Relation type)
 * asdf + asdf (Arithmetic type).
 *
 * It is the parent of Arithemtic and Relation classes
 *
 */
public abstract class Binary extends Expression {

	Expression leftOperand;
	Expression rigthOperand;

	public Binary(Expression leftOperand, Expression rightOperand){
		this.leftOperand = leftOperand;
		this.rigthOperand = rightOperand;
	}

}
