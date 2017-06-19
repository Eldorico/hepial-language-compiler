package abstractTree.expression;

import symbol.Type;
import codeProduction.JEvaluator;

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

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }

    public boolean getValue(){
        return value;
    }
}
