package abstractTree.expression;

/**
 * @description
 * This class represents an Integer expression.
 *
 */
public class IntNumber extends Expression {

	int value;

	public IntNumber(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
