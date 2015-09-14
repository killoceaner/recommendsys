#!/bin/bash

SITE='recommendsys'

#find ./target/classes -name "*.xml"|xargs rm -f


tmp='./bin/resources'
tmp='./target/classes':$tmp
tmp='./target/recommendsys-1.0-SNAPSHOT-jar-with-dependencies-without-resources/*':$tmp

CLASSPATH=$tmp:$CLASSPATH

JAVA_OPTS="-Xms256m -Xmx256m -Xmn128m"  

echo $CLASSPATH

java $JAVA_OPTS -DlogFilePath=${SITE} -classpath $CLASSPATH com.ossean.main.SimilityCount >>log/${SITE}.log 2>&1 &