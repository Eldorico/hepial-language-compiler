package abstractTree.expression;

public class CstString extends Expression {

	String value;

	public CstString(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
