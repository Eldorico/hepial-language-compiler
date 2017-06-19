package codeProduction;

class ConstructorProducer extends JasminCodeProducer {

    boolean dontProduceConstructor;
    FunctionInstructions blockInstructions;
    Fields blockFields;

    int stackSizeNeeded = 0;
    int localsSizeNeeded = 0;
    String parentNameWithCapital = null;
    String parentNameWithLowerCase = null;
    String blockNameWithCapital = null;

    public ConstructorProducer(String blockName, String parentBlockName, FunctionInstructions blockInstructions, Fields blockFields)  {
        blockNameWithCapital = CodeProducer.capitaliseFirstChar(blockName);
        parentNameWithCapital = parentBlockName == null ? null : CodeProducer.capitaliseFirstChar(parentBlockName);
        parentNameWithLowerCase = parentBlockName == null ? null : CodeProducer.lowerCaseFirstChar(parentBlockName);
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
            blockFields.jtext.addLine(".field "+parentNameWithLowerCase+" L"+parentNameWithCapital+";");

            // add the parent to the field
            jtext.addIndentedLine(";load the parent block and put it on the field "+parentNameWithLowerCase);
            jtext.addIndentedLine("aload 0");
            jtext.addIndentedLine("aload 1");
            jtext.addIndentedLine("putfield "+blockNameWithCapital+"/"+parentNameWithLowerCase+" L"+parentNameWithCapital+";");
            localsSizeNeeded = 3;
            stackSizeNeeded = 2;
        }
    }

    @Override
    String getJCodeAsString() {
        // update the locals size needed with the field hook
        stackSizeNeeded = Math.max(stackSizeNeeded, blockFields.constructorConstantsHook.maxStackSizeNeeded);
        localsSizeNeeded = Math.max(localsSizeNeeded, blockFields.constructorConstantsHook.maxLocalsSizeNeeded);
        stackSizeNeeded = Math.max(stackSizeNeeded, blockFields.constructorArraysHook.maxStackSizeNeeded);
        localsSizeNeeded = Math.max(localsSizeNeeded, blockFields.constructorArraysHook.maxLocalsSizeNeeded);

        // open the constructor method
        String linesBefore = "\n; constructor declaration. This = "+blockNameWithCapital+", parent = "+parentNameWithCapital+"\n";
        String parentStr = (parentNameWithCapital == null) ? "" : "L"+parentNameWithCapital+";";
        linesBefore += ".method public <init>("+parentStr+")V\n";
        linesBefore += (stackSizeNeeded > 0) ? jtext.indent()+".limit stack "+stackSizeNeeded+"\n" : "";
        linesBefore += (localsSizeNeeded > 0) ? jtext.indent()+".limit locals "+localsSizeNeeded+"\n" : "";
        jtext.insertBefore(linesBefore);

        // add the field hook (the constants before, because arrays can be defined with constants expressions.
        jtext.addText(blockFields.constructorConstantsHook.getJCodeAsString());
        jtext.addText(blockFields.constructorConstantsHook.getJCodeAsString());  // we have to add it twice because it is possible to declare a constant using another constant declared after.
        jtext.addText(blockFields.constructorArraysHook.getJCodeAsString());                                        // constant y = x +2;
                                                                                                                    // constant x = 2;
        // close the constructor method                                                                             // if we only run once, y would be equal to 2, and not 4.
        jtext.addLine("");
        jtext.addIndentedLine("return");
        jtext.addLine(".end method");
        return jtext.getJCodeAsString();
    }

}
