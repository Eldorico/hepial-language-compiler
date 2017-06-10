## Requirements:

- jflex: a lexical analyser generator for java. (equivalent to Lex for C) [(http://jflex.de/download.html)](http://jflex.de/download.html) 

- java 8 (jdk and jvm)  _(I think java 7 works but I'm not sure)_

  ​

## Librairies included

- CUP: a java version of Yacc, a syntaxic analyser.  JFlex is compatible with Cup.  [http://www2.cs.tum.edu/projects/cup/](http://www2.cs.tum.edu/projects/cup/)

- Jasmin:  an assembler for the JVM.  [http://jasmin.sourceforge.net/](http://jasmin.sourceforge.net/)

  ​


## Usage:

```bash
# compile the hepial compilator
make

# compile the hepial file
bash hepiaCompile <fileName>    # if no filename entered: will compile input.txt

# execute the hepial executable
bash <programName> 
```

