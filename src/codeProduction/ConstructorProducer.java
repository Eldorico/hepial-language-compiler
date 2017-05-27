package codeProduction;

class ConstructorProducer extends JasminCodeProducer {

    boolean dontProduceConstructor;
    FunctionInstructions blockInstructions;
    Fields blockFields;

    int stackSizeNeeded = 0;
    int localsSizeNeeded = 0;
    String parentNameWithCapital = null;
    String blockNameWithCapital = null;

    public ConstructorProducer(String blockName, String parentBlockName, FunctionInstructions blockInstructions, Fields blockFields)  {
        blockNameWithCapital = CodeProducer.capitaliseFirstChar(blockName);
        this.blockFields = blockFields;

        // write the constructor here

        // invoke super
        jtext.addIndentedLine("; invoke the super constructor ");
        jtext.addIndentedLine("aload 0");
        jtext.addIndentedLine("invokespecial java/lang/Object/<init>()V");

        // if there is a parent
        if(parentBlockName != null){
            // add the field for the parent. (to manage the variables scope)
            String parentNameWithCapital = CodeProducer.capitaliseFirstChar(parentBlockName);
            blockFields.jtext.addLine(".field "+parentBlockName+" L"+parentNameWithCapital+";");

            // add the parent to the field
            jtext.addIndentedLine(";load the parent block and put it on the field "+parentBlockName);
            jtext.addIndentedLine("aload 0");
            jtext.addIndentedLine("aload 1");
            jtext.addIndentedLine("putfield "+blockNameWithCapital+"/"+parentBlockName+" L"+parentNameWithCapital+";");
            localsSizeNeeded = 3;
            stackSizeNeeded = 2;
        }
    }

    @Override
    String getJCodeAsString() {
        // update the locals size needed with the field hook
        stackSizeNeeded = Math.max(stackSizeNeeded, blockFields.constructorHook.maxStackSizeNeeded);
        localsSizeNeeded = Math.max(localsSizeNeeded, blockFields.constructorHook.maxLocalsSizeNeeded);

        // open the constructor method
        String linesBefore = "\n; constructor declaration. This = "+blockNameWithCapital+", parent = "+parentNameWithCapital+"\n";
        String parentStr = (parentNameWithCapital == null) ? "" : "L"+parentNameWithCapital+";";
        linesBefore += ".method public <init>("+parentStr+")V\n";
        linesBefore += (stackSizeNeeded > 0) ? jtext.indent()+".limit stack "+stackSizeNeeded+"\n" : "";
        linesBefore += (localsSizeNeeded > 0) ? jtext.indent()+".limit locals "+localsSizeNeeded+"\n" : "";
        jtext.insertBefore(linesBefore);

        // add the field hook
        jtext.addText(blockFields.constructorHook.getJCodeAsString());

        // close the constructor method
        jtext.addLine("");
        jtext.addIndentedLine("return");
        jtext.addLine(".end method");
        return jtext.getJCodeAsString();
    }

}
