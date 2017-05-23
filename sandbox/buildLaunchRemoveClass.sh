#!/bin/sh

if [ $# -eq 0 ]; then
	echo "usage: "
	echo "./buildLaunchRemoveClass.sh <jasminFileToBuild/Launch>"
	exit 1
fi

java -jar ../lib/jasmin.jar $1

echo 
fileName=$1
fileName=${fileName%".j"}
java $fileName

rm $fileName.class
