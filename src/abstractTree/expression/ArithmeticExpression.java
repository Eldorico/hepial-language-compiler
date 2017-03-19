package abstractTree.expression;

/**
 * @description:
 *	This class represents Arithmetic expressions such as:
 *	asdf + asdf
 *  asdf - 1234
 *	asdf * asdf
 *	asdf / asdf
 *
 *	It is the parent of
 * - AdditionExpression
 * - SubstractionExpression
 * - MultiplyExpression
 * - DivideExpression
 *
 */
public abstract class ArithmeticExpression extends BinaryExpression {

	public ArithmeticExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}
}
