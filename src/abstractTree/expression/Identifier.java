package abstractTree.expression;

/**
 * @description:
 * This class represents an identifier. For example, in an expression as 'a = b', 'a' and
 * 'b' are identifiers.
 *
 */
public class Identifier extends Expression {

	String name;

	public Identifier(String value) {
		this.name = value;
	}

	@Override
	public String toString() {
		return name;
	}

}
