/* classe 'principale' */
import java.util.Vector;
import java.io.FileReader;

public class HepialCompilateur {

	public static void main (String[] arg)
	{
		try { 
			FileReader myFile = new FileReader(arg[0]);
			Lexical myLexical = new Lexical(myFile);
			parser myP = new parser(myLexical);
			try {myP.parse();}
			catch (Exception e) {
				System.out.println("parse error");
			}
		}
		catch (Exception e){
			System.out.println("invalid file");
		}
	}
}
