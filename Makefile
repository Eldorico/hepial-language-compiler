PROGRAMFILENAME=HepialCompilateur
CUPFILENAME=syntax
JFLEXFILENAME=Lexical


# Program ...
bin/${PROGRAMFILENAME}.class: bin/$(JFLEXFILENAME).java bin/sym.class bin/${JFLEXFILENAME}.class 
	javac -d bin -cp lib/java-cup-11a.jar:bin src/$(PROGRAMFILENAME).java 


# Cup ...
bin/${CUPFILENAME}.cup : 

bin/sym.class : bin/${CUPFILENAME}.cup
	java  -jar lib/java-cup-11a.jar src/${CUPFILENAME}.cup
	mv sym.java bin/sym.java 
	mv parser.java bin/parser.java
	javac -cp lib/java-cup-11a.jar:bin bin/sym.java 
	javac -cp lib/java-cup-11a.jar:bin bin/parser.java 


# JFlex ...
bin/$(JFLEXFILENAME).class: bin/$(JFLEXFILENAME).java
	javac -cp lib/java-cup-11a.jar:bin bin/$(JFLEXFILENAME).java 

bin/$(JFLEXFILENAME).java: src/$(JFLEXFILENAME).flex
	mkdir -p bin
	jflex src/$(JFLEXFILENAME).flex -d bin

src/$(JFLEXFILENAME).flex:

# Clean
clean: 
	rm -f -r bin

# JFlex only
jflex: 
	mkdir -p bin
	jflex src/$(JFLEXFILENAME).flex -d bin
	javac -cp lib/java-cup-11a.jar:bin bin/$(JFLEXFILENAME).java 
