/* classe 'principale' */
import java.util.Vector;
import java.io.FileReader;
import utils.ErrorPrinter;
import symbol.SymbolTable;

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
				System.out.println("parse error");
			}

			// check for semantic errors and print errors if any
			boolean errorsDetected = false;
			if(SymbolTable.getInstance().semanticErrorsDetected()){
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
