package abstractTree;

public abstract class AbstractTree {

	@Override
	abstract public String toString();

	//abstract public boolean semanticErrorsDetected();
	public boolean semanticErrorsDetected(){return false;}; // TODO: remove this line!

}
