package abstractTree.instruction;

public class GoToInstruction extends Instruction {

    String label;

    public GoToInstruction(int declarationLineNumber, String label) {
        super(declarationLineNumber);
        this.label = label;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLabel(){
        return label;
    }

}
