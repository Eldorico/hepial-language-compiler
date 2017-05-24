#!/bin/sh

java -jar ../../lib/jasmin.jar MainBlock.j
java -jar ../../lib/jasmin.jar LocalsScopeTesting.j
java LocalsScopeTesting
