#!/bin/sh

java -jar ../../lib/jasmin.jar Function1.j
java -jar ../../lib/jasmin.jar MainBlock.j
java -jar ../../lib/jasmin.jar ProgramName.j
java ProgramName
rm *.class
