package abstractTree.expression;

/**
 * @description:
 *	This class represents Relation expressions such as:
 *	asdf = asdf
 *	asdf < asdf
 *	asdf <= asdf
 *
 *	It is the parent of //TODO
 *
 */
public abstract class Relation extends Binary {

	public Relation(Expression leftOperand, Expression rightOperand) {
		super(leftOperand, rightOperand);
	}

}
