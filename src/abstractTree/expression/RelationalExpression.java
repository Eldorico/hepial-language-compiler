package abstractTree.expression;

import symbol.Type;

/**
 * @description:
 *	This class represents Relational expressions such as:
 *	asdf != asdf
 *  asdf == asdf
 *	asdf < asdf
 *	asdf <= asdf
 *
 *	It is the parent of
 * - LesserThanExpression
 * - LesserEqualExpression
 * - GreaterEqualExpression
 * - GreaterThanExpression
 * - EqualEqualExpression
 * - DifferentThanExpression
 *
 */
public abstract class RelationalExpression extends BinaryExpression {

	public RelationalExpression(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
    public Type getType(){
	    return Type.BOOLEAN;
	}
}
