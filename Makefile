PROGRAMFILENAME=HepialCompilateur
CUPFILENAME=syntax
JFLEXFILENAME=Lexical


# Program ...
bin/${PROGRAMFILENAME}.class: bin/$(JFLEXFILENAME).java bin/sym.class bin/${JFLEXFILENAME}.class
	javac -d bin -cp lib/java-cup-11a.jar:bin src/$(PROGRAMFILENAME).java


# Cup ...
bin/sym.class : src/${CUPFILENAME}.cup src/$(JFLEXFILENAME).flex
	# compile every abstractTree class
	find -path "*/src/abstractTree/*.java" > sources.txt
	find -path "*/src/symbol/*.java" >> sources.txt
	find -path "*/src/utils/*.java" >> sources.txt
	javac -d bin @sources.txt
	rm sources.txt

	#compile cup
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

# Clean
clean:
	rm -f -r bin sources.txt

#### DEBUG COMMANDS ######

#AbstractTree lib only
abstractTree:
	mkdir -p bin
	find -path "*/src/abstractTree/*.java" > sources.txt
	javac -d bin @sources.txt
	rm sources.txt

# JFlex only
jflex:
	mkdir -p bin
	jflex src/$(JFLEXFILENAME).flex -d bin
	javac -cp lib/java-cup-11a.jar:bin bin/$(JFLEXFILENAME).java
