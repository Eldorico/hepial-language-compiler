/*
 * @Description: Provides an lexical evaluator simple expressions, keywords and
 *               operators of the hepial langage.
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

// keywords
debutprg    {return new Symbol(sym.keyword_debutprg, new SimpleEntry<String, Integer>(yytext(), yyline));  }
finprg      {return new Symbol(sym.keyword_finprg, new SimpleEntry<String, Integer>(yytext(), yyline));  }
programme   {return new Symbol(sym.keyword_programme, new SimpleEntry<String, Integer>(yytext(), yyline));  }
constante   {return new Symbol(sym.keyword_constante, new SimpleEntry<String, Integer>(yytext(), yyline));  }
debutfonc   {return new Symbol(sym.keyword_debutfonc, new SimpleEntry<String, Integer>(yytext(), yyline));  }
finfonc     {return new Symbol(sym.keyword_finfonc, new SimpleEntry<String, Integer>(yytext(), yyline));  }
entier      {return new Symbol(sym.keyword_entier, new SimpleEntry<String, Integer>(yytext(), yyline));  }
booleen     {return new Symbol(sym.keyword_booleen, new SimpleEntry<String, Integer>(yytext(), yyline));  }
lire        {return new Symbol(sym.keyword_lire, new SimpleEntry<String, Integer>(yytext(), yyline));  }
ecrire      {return new Symbol(sym.keyword_ecrire, new SimpleEntry<String, Integer>(yytext(), yyline));  }
retourne    {return new Symbol(sym.keyword_retourne, new SimpleEntry<String, Integer>(yytext(), yyline));  }
si          {return new Symbol(sym.keyword_si, new SimpleEntry<String, Integer>(yytext(), yyline));  }
alors       {return new Symbol(sym.keyword_alors, new SimpleEntry<String, Integer>(yytext(), yyline));  }
sinon       {return new Symbol(sym.keyword_sinon, new SimpleEntry<String, Integer>(yytext(), yyline));  }
finsi       {return new Symbol(sym.keyword_finsi, new SimpleEntry<String, Integer>(yytext(), yyline));  }
tantque     {return new Symbol(sym.keyword_tantque, new SimpleEntry<String, Integer>(yytext(), yyline));  }
faire       {return new Symbol(sym.keyword_faire, new SimpleEntry<String, Integer>(yytext(), yyline));  }
fintantque  {return new Symbol(sym.keyword_fintantque, new SimpleEntry<String, Integer>(yytext(), yyline));  }
pour        {return new Symbol(sym.keyword_pour, new SimpleEntry<String, Integer>(yytext(), yyline));  }
allantde    {return new Symbol(sym.keyword_allantde, new SimpleEntry<String, Integer>(yytext(), yyline));  }
a           {return new Symbol(sym.keyword_a, new SimpleEntry<String, Integer>(yytext(), yyline));  }
finpour     {return new Symbol(sym.keyword_finpour, new SimpleEntry<String, Integer>(yytext(), yyline));  }
vrai        {return new Symbol(sym.keyword_vrai, new SimpleEntry<String, Integer>(yytext(), yyline));  }
faux        {return new Symbol(sym.keyword_faux, new SimpleEntry<String, Integer>(yytext(), yyline));  }
et          {return new Symbol(sym.keyword_et, new SimpleEntry<String, Integer>(yytext(), yyline));  }
ou          {return new Symbol(sym.keyword_ou, new SimpleEntry<String, Integer>(yytext(), yyline));  }
non         {return new Symbol(sym.keyword_non, new SimpleEntry<String, Integer>(yytext(), yyline));  }

// operators
\+    {return new Symbol(sym.char_plus, new SimpleEntry<String, Integer>(yytext(), yyline));  }
-     {return new Symbol(sym.char_moins, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\*    {return new Symbol(sym.char_fois, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\/    {return new Symbol(sym.char_div, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\=    {return new Symbol(sym.char_egal, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\=\=  {return new Symbol(sym.char_egal_egal, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\!\=  {return new Symbol(sym.char_different, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\<    {return new Symbol(sym.char_ppetit, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\>    {return new Symbol(sym.char_pgrand, new SimpleEntry<String, Integer>(yytext(), yyline));  }
//\<\>  {return new Symbol(sym.char_ppetit_pgrand, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\<\=  {return new Symbol(sym.char_ppetit_egal, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\>\=  {return new Symbol(sym.char_pgrand_egal, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\!    {return new Symbol(sym.char_non, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\(    {return new Symbol(sym.char_par_ouvr, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\)    {return new Symbol(sym.char_par_ferm, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\[    {return new Symbol(sym.char_crochet_ouvr, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\]    {return new Symbol(sym.char_crochet_ferm, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\,    {return new Symbol(sym.char_virgule, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\;    {return new Symbol(sym.char_point_virgule, new SimpleEntry<String, Integer>(yytext(), yyline));  }
\.\.  {return new Symbol(sym.char_point_point, new SimpleEntry<String, Integer>(yytext(), yyline));  }

// other
/*   asdf9_   */  {ident} {return new Symbol(sym.ident, new SimpleEntry<String, Integer>(yytext(), yyline));  }
/*   1234     */  {constanteEnt} {return new Symbol(sym.constanteEnt, new SimpleEntry<String, Integer>(yytext(), yyline));  }
/* " as""df " */  {constanteChaine} {return new Symbol(sym.constanteChaine, new SimpleEntry<String, Integer>(yytext(), yyline));  }
{comments} {;}
{any_thing_else} {;}
