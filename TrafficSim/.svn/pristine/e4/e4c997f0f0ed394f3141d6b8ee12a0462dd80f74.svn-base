#!/bin/bash

# Initial settings
PROJECT_DIR=/home/javi/Proyectos/Eclipse/TrafficSim
IRON_DIR=/home/javi/Proyectos/Eclipse/IRON
ECLIPSE_HOME=/opt/eclipse4.2
JAVA_EXECUTABLE=java
JAVA_FLAGS=-client
ECLIPSE_PLUGINS=$ECLIPSE_HOME/plugins/
JESS_LIB=$PROJECT_DIR/lib/jess7.1.2.jar

# Prepare CLASSPATH
CLASSPATH=.

TMP_LIBS=libs-todelete.txt
echo "" > $TMP_LIBS

# Add libraries to path
ECLIPSE_PLUGINS_ALL_JARS=`find $ECLIPSE_PLUGINS -type f -name '*.jar'`
for file in $ECLIPSE_PLUGINS_ALL_JARS
do
	echo "CLASSPATH=\$CLASSPATH:$file" >> $TMP_LIBS
done

# Add bin files to path
ECLIPSE_PLUGINS_ALL_BINS=`find $ECLIPSE_PLUGINS -type d -name '*.bin'`
for dire in $ECLIPSE_PLUGINS_ALL_BINS
do
	echo "CLASSPATH=\$CLASSPATH:$dile" >> $TMP_LIBS
done

# Add TrafficSim libs
echo "CLASSPATH=\$CLASSPATH:$JESS_LIB" >> $TMP_LIBS
PROJECT_LIBS=`find $PROJECT_DIR/lib -type f -name '*.jar'`
for file in $PROJECT_LIBS
do
	echo "CLASSPATH=\$CLASSPATH:$file" >> $TMP_LIBS
done

# Add IRON libs
IRON_LIBS=`find $IRON_DIR/lib -type f -name '*.jar'`
for file in $IRON_LIBS
do
	echo "CLASSPATH=\$CLASSPATH:$file" >> $TMP_LIBS
done

# Add source to path
echo "CLASSPATH=\$CLASSPATH:$IRON_DIR/bin" >> $TMP_LIBS
echo "CLASSPATH=\$CLASSPATH:$PROJECT_DIR/bin" >> $TMP_LIBS
echo "CLASSPATH=\$CLASSPATH:$PROJECT_DIR/bin-groovy" >> $TMP_LIBS

sort $TMP_LIBS > "$TMP_LIBS-sorted"

echo "export CLASSPATH=\$CLASSPATH" >> "$TMP_LIBS-sorted"

source "$TMP_LIBS-sorted"

export CLASSPATH=$CLASSPATH

rm $TMP_LIBS "$TMP_LIBS-sorted"

