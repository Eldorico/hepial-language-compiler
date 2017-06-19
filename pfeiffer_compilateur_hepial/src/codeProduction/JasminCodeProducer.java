package codeProduction;

abstract class JasminCodeProducer {

    protected JasminCodeText jtext = new JasminCodeText();

    abstract String getJCodeAsString();

}
