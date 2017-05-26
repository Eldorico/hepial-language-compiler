package codeProduction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.FunctionSymbol;
import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import abstractTree.instruction.AffectationInstruction;
import abstractTree.instruction.BlocInstruction;
import abstractTree.instruction.Instruction;
import abstractTree.instruction.WriteInstruction;

public class CodeProducer {

    private String outputFolderName;
    private static CodeProducer instance = new CodeProducer();
    private BlocInstruction mainInstructions;

    /**
     * @description:
     */
    public void produceProgram(String programName, String outputFolderName, BlocInstruction mainInstructions){
        this.mainInstructions = mainInstructions;

        // create outputFolder
        this.outputFolderName = outputFolderName;
        executeShellProcess("rm -f -r "+outputFolderName);
        executeShellProcess("mkdir "+outputFolderName);

        // create the jasmin file that will contain the static main method. (The file will have te name of the program)
        Block staticMainBlock = new Block(programName, null, null, outputFolderName, true, null);
        staticMainBlock.instructions.addFunctionCall(SymbolTable.getInstance().getMainBlockName(), null, null, Type.VOID);
        staticMainBlock.instructions.addReturnInstruction(null);
        staticMainBlock.produceJasminFile();

        // for each block (functions), produce a new block
        ArrayList<String> blockLists = SymbolTable.getInstance().getBlocNameList();
        String mainBlockName = SymbolTable.getInstance().getMainBlockName();
        for(String blocName : blockLists){
            String parentBlocName = (blocName.equals(mainBlockName)) ? null : mainBlockName;
            produceFunctionBlock(blocName, parentBlocName);
        }
    }

    public static String capitaliseFirstChar(String str){
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String getTab(){
        return "   ";
    }

    public String getFolderName(){
        return outputFolderName;
    }

    public static CodeProducer getInstance(){
        return instance;
    }

    /**
     * @description:
     * @param blockName
     */
    private void produceFunctionBlock(String blockName, String parentBlockName){
        // create the block
        Block block;
        if(blockName.equals(SymbolTable.getInstance().getMainBlockName())){
            block = new Block(blockName, null, new ArrayList<SimpleEntry<String, VariableSymbol>>(), outputFolderName, false, Type.VOID);
        }else{
            FunctionSymbol fSymbol = (FunctionSymbol) SymbolTable.getInstance().getSymbol(blockName);
            block = new Block(blockName, parentBlockName, fSymbol.getParameters(), outputFolderName, false, fSymbol.returnType());
        }

        // add fields
        SymbolTable.getInstance().enterBloc(blockName);
        ArrayList<SimpleEntry<String, VariableSymbol>> fieldsList = SymbolTable.getInstance().getNormalFieldsOf(blockName);
        for(SimpleEntry<String, VariableSymbol> entry : fieldsList){
            block.localFields.addField(entry.getKey(), entry.getValue());
        }
        SymbolTable.getInstance().exitCurrentBloc();

        // add instructions
        BlocInstruction instructionsList;
        if(blockName.equals(SymbolTable.getInstance().getMainBlockName())){
            instructionsList = mainInstructions;
        }else{
            FunctionSymbol fSymbol = (FunctionSymbol)SymbolTable.getInstance().getSymbol(blockName);
            instructionsList = fSymbol.getInstructions();
        }

        for(Instruction instruction: instructionsList){
            addInstructionToBlock(block, instruction);
        }

        // produce block
        block.produceJasminFile();
    }

    private void addInstructionToBlock(Block block, Instruction instruction){
        if(instruction instanceof AffectationInstruction){
            block.instructions.addAffectationInstruction((AffectationInstruction) instruction);
        }else if(instruction instanceof WriteInstruction){
            block.instructions.addWriteInstruction((WriteInstruction) instruction);
        }
    }

    /**
     * @description: executes a shell command.
     * @param command the command to execute
     * @return
     */
    private static int executeShellProcess(String command){
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(command);
            BufferedReader stdoutReader = new BufferedReader( new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = stdoutReader.readLine()) != null) {
               System.out.println(line);
            }

            BufferedReader stderrReader = new BufferedReader( new InputStreamReader(process.getErrorStream()));
            while ((line = stderrReader.readLine()) != null) {
               System.err.println(line);
            }

            process.waitFor();
        }catch(Exception e){
            System.err.print(e.getMessage());
        }
        return process.exitValue();
    }


}
