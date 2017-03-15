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
%standalone

/* LEXEMES */

ident = [a-zA-Z][a-zA-Z0-9_]*                     /*   asdf9_   */
constanteEnt = [0-9]+                             /*   1234     */
constanteChaine = [\"]([^\"]|\"\")*[\"]           /* " as""df " */

comments = \/\/.*\n                               /* //comment  */
any_thing_else = .? | \n

%%
/* RULES */

// mots clefs
debutprg    {System.out.println("Trouvé keyword_debutprg en ligne"+yyline);}
finprg      {System.out.println("Trouvé keyword_finprg en ligne"+yyline);}
programme   {System.out.println("Trouvé keyword_programme en ligne"+yyline);}
constante   {System.out.println("Trouvé keyword_constante en ligne"+yyline);}
debutfonc   {System.out.println("Trouvé keyword_debutfonc en ligne"+yyline);}
finfonc     {System.out.println("Trouvé keyword_finfonc en ligne"+yyline);}
entier      {System.out.println("Trouvé keyword_entier en ligne"+yyline);}
booleen     {System.out.println("Trouvé keyword_booleen en ligne"+yyline);}
lire        {System.out.println("Trouvé keyword_lire en ligne"+yyline);}
ecrire      {System.out.println("Trouvé keyword_ecrire en ligne"+yyline);}
retourne    {System.out.println("Trouvé keyword_retourne en ligne"+yyline);}
si          {System.out.println("Trouvé keyword_si en ligne"+yyline);}
alors       {System.out.println("Trouvé keyword_alors en ligne"+yyline);}
sinon       {System.out.println("Trouvé keyword_sinon en ligne"+yyline);}
finsi       {System.out.println("Trouvé keyword_finsi en ligne"+yyline);}
tantque     {System.out.println("Trouvé keyword_tantque en ligne"+yyline);}
faire       {System.out.println("Trouvé keyword_faire en ligne"+yyline);}
fintantque  {System.out.println("Trouvé keyword_fintantque en ligne"+yyline);}
pour        {System.out.println("Trouvé keyword_pour en ligne"+yyline);}
allantde    {System.out.println("Trouvé keyword_allantde en ligne"+yyline);}
a           {System.out.println("Trouvé keyword_a en ligne"+yyline);}
finpour     {System.out.println("Trouvé keyword_finpour en ligne"+yyline);}
vrai        {System.out.println("Trouvé keyword_vrai en ligne"+yyline);}
faux        {System.out.println("Trouvé keyword_faux en ligne"+yyline);}
et          {System.out.println("Trouvé keyword_et en ligne"+yyline);}
ou          {System.out.println("Trouvé keyword_ou en ligne"+yyline);}
non         {System.out.println("Trouvé keyword_non en ligne"+yyline);}

// caractères
\+    {System.out.println("Trouvé char_plus en ligne"+yyline);}
-     {System.out.println("Trouvé char_moins en ligne"+yyline);}
\*    {System.out.println("Trouvé char_fois en ligne"+yyline);}
\/    {System.out.println("Trouvé char_div en ligne"+yyline);}
\=    {System.out.println("Trouvé char_egual en ligne"+yyline);}
\=\=    {System.out.println("Trouvé char_egual_egual en ligne"+yyline);}
\<     {System.out.println("Trouvé char_ppetit en ligne"+yyline);}
\>     {System.out.println("Trouvé char_pgrand en ligne"+yyline);}
\<\>    {System.out.println("Trouvé char_ppetit_pgrand en ligne"+yyline);}
\<\=    {System.out.println("Trouvé char_petit_egal en ligne"+yyline);}
\>\=    {System.out.println("Trouvé char_grand_egal en ligne"+yyline);}
\~    {System.out.println("Trouvé char_non en ligne"+yyline);}
\(    {System.out.println("Trouvé char_par_ouvr en ligne"+yyline);}
\)    {System.out.println("Trouvé char_par_ferm en ligne"+yyline);}
\[    {System.out.println("Trouvé char_crochet_ouvr en ligne"+yyline);}
\]    {System.out.println("Trouvé char_crochet_ferm en ligne"+yyline);}
\,    {System.out.println("Trouvé char_virgule en ligne"+yyline);}
\;    {System.out.println("Trouvé char_point_virgule en ligne"+yyline);}
\.\.  {System.out.println("Trouvé char_point_point en ligne"+yyline);}

// autres
/*   asdf9_   */  {ident} { System.out.println("ident: "+yytext());}   /*{return new Symbol(sym.ident, new SimpleEntry<String, Integer>(yytext(), yyline));  }*/
/*   1234     */  {constanteEnt} { System.out.println("constanteEnt: "+yytext());}   /*{ return new Symbol(sym.constanteEnt, new SimpleEntry<String, Integer>(yytext(), yyline));  }*/
/* " as""df " */  {constanteChaine} { System.out.println("constanteChaine: "+yytext());} /*{ return new Symbol(sym.constanteChaine, new SimpleEntry<String, Integer>(yytext(), yyline)); }*/
{comments} {  System.out.println("comments: "+yytext()); }
{any_thing_else} {;}
