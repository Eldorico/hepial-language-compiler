/* classe 'principale' */
import java.io.FileReader;

import symbol.SymbolTable;
import utils.ErrorPrinter;

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
				//System.out.println("parse error");
				System.exit(-1);
			}

			// the abstractTreeStack generated from the parser has to have a size of 1
			if(myP.abstractTreeStack.size() != 1){
			    System.err.println("The abstractTreeStack has not a size of 1. It has a size of "+myP.abstractTreeStack.size()+". Compilation will exit");
			    System.exit(-2);
			}

			// check for semantic errors and print errors if any
			boolean errorsDetected = false;
			if(SymbolTable.getInstance().semanticErrorsDetected()){
				errorsDetected = true;
			}
			if(myP.abstractTreeStack.peek().semanticErrorsDetected()){
			    errorsDetected = true;
			}

			if(errorsDetected){
				ErrorPrinter.getInstance().printErrors();
			}


		}
		catch (Exception e){
			System.out.println("invalid file");
		}
	}
}
