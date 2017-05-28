/* classe 'principale' */
import java.io.FileReader;

import symbol.SymbolTable;
import utils.ErrorPrinter;
import abstractTree.instruction.BlocInstruction;
import codeProduction.CodeProducer;
//import java_cup.parser;

public class HepialCompilateur {

	public static void main (String[] arg)
	{
		try {
			FileReader myFile = new FileReader(arg[0]);
			Lexical myLexical = new Lexical(myFile);
			parser myP = new parser(myLexical);

			// parse the code and check for syntax errors
			try {myP.parse();}
			catch (Exception e) {
				System.exit(-1);
			}

			// the abstractTreeStack generated from the parser has to have a size of 1
			if(myP.abstractTreeStack.size() != 1){
			    System.err.println("The abstractTreeStack has not a size of 1. It has a size of "+myP.abstractTreeStack.size()+". Compilation will exit");
			    System.exit(-4);
			}
			// the abstractTreeStack generated from the parser has to be an instance of Instruction
			BlocInstruction abstractTreeElement = null;
			if(!(myP.abstractTreeStack.peek() instanceof BlocInstruction)){
                System.err.println("The element in the abstractTreeStack is not an instance of BlocInstruction. Compilation will exit");
                System.exit(-4);
			}else{
			    abstractTreeElement = (BlocInstruction) myP.abstractTreeStack.peek();
			}

			// debug
			System.out.println("\n\ndetecting semantic errors...");

			// check for semantic errors and print errors if any
			boolean errorsDetected = false;
			if(SymbolTable.getInstance().semanticErrorsDetected()){
				errorsDetected = true;
			}
			if(abstractTreeElement.semanticErrorsDetected()){
			    errorsDetected = true;
			}

			if(errorsDetected){
				ErrorPrinter.getInstance().printErrors();
				System.exit(1);
			}

			// produce the code
			System.out.println("Starting code production of "+SymbolTable.getInstance().getProgramName()+"...");
			CodeProducer.getInstance().produceProgram(SymbolTable.getInstance().getProgramName(), "compiledBin", abstractTreeElement);



		}
		catch (Exception e){
			System.out.println("Compilation error: "+e.getMessage());
		}
	}
}
