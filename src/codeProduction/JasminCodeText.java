package codeProduction;

/**
 * @description: this class is used to keep jasmin code in a single String.
 * (This string will be printed "as is" into a jasmin file)
 * The attributes jInstructions contains the jasmin code.
 * The class provides simple API to addLines, addIndentedLine, etc...
 */
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

    protected void addIndentedText(String txtToAdd){
        jInstructions = jInstructions+indent()+txtToAdd;
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
