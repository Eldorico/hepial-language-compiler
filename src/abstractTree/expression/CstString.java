package abstractTree.expression;

import symbol.Type;

public class CstString extends Expression {

	String value;

	public CstString(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public Type getType(){
	    return Type.CST_STRING;
	}

}
