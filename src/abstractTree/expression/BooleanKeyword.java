package abstractTree.expression;

import symbol.Type;

/**
 * @description:
 * This class represents a boolean keyword.
 */
public class BooleanKeyword extends Expression {

	boolean value;

	public BooleanKeyword(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public Type getType(){
	    return Type.BOOLEAN;
	}

}
