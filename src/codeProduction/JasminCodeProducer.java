package codeProduction;

abstract class JasminCodeProducer {
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

    abstract String getJCodeAsString();
}
