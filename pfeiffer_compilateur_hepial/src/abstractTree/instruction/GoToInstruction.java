package abstractTree.instruction;

public class GoToInstruction extends Instruction {

    String label;

    public GoToInstruction(int declarationLineNumber, String label) {
        super(declarationLineNumber);
        this.label = label;
    }

    @Override
    public String toString() {
        return "goto "+label;
    }

    public String getLabel(){
        return label;
    }

}
