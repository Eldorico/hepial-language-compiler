package codeProduction;

class JasminCodeText {
    protected String jInstructions = new String();

    protected String indent(){
        return CodeProducer.getTab();
    }

    protected void addIndentedLine(String txtToAdd){
        jInstructions = jInstructions + indent()+txtToAdd+"\n";
    }

    protected void addLine(String txtToAdd){
        jInstructions = jInstructions+txtToAdd+"\n";
    }

    protected void addText(String txtToAdd){
        jInstructions = jInstructions+txtToAdd;
    }

    String getJCodeAsString(){
        return jInstructions;
    }

    void insertBefore(String prefix){
        jInstructions = prefix + jInstructions;
    }

    boolean isEmpty(){
        return jInstructions.isEmpty();
    }
}
