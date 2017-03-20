package abstractTree.expression;

/**
 * @description:
 * This class represents an identifier of a value of an array at a particular index.
 * It is represented as such:
 *
 * name[indexes] or name[indexes.get(0)][indexes.get(1) etc..
 *
 */
public class TabValueIndentifier extends Identifier {

	ExpressionList indexes;

	public TabValueIndentifier(String value, ExpressionList indexes) {
		super(value);
		this.indexes = indexes;
	}

	@Override
	public String toString(){
		return String.format("%s%s", this.name, this.indexes.toString());
	}

}
