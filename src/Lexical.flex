/*
 * @Description: Provides an lexical evaluator simple expressions of the hepial langage.
 *               Each expression is returned as a SimpleEntry< String:value, Integer:line>
 *
 */

/* java imports */
import java_cup.runtime.*;
import java.util.AbstractMap.SimpleEntry;

%%
/* jflex options */
%class Lexical
%unicode
%line
%column
%cup

/* LEXEMES */
ident = [a-zA-Z][a-zA-Z0-9_]*                     /*   asdf9_   */
constanteEnt = [0-9]+                             /*   1234     */
constanteChaine = [\"]([^\"]|\"\")*[\"]           /* " as""df " */
comments = \/\/.*\n                               /* //comment  */
any_thing_else = .? | \n

%%
/* RULES */

// tags....
/*   asdf9_   */  {ident}   { return new Symbol(sym.ident, new SimpleEntry<String, Integer>(yytext(), yyline));  }
/*   1234     */  {constanteEnt}   { return new Symbol(sym.constanteEnt, new SimpleEntry<String, Integer>(yytext(), yyline));  }
/* " as""df " */  {constanteChaine} { return new Symbol(sym.constanteChaine, new SimpleEntry<String, Integer>(yytext(), yyline)); }
{comments} { ; }
{any_thing_else} {;}
