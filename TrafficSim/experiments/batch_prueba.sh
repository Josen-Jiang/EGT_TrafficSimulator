#!/bin/bash


# Initial settings
ECLIPSE_HOME=/Volumes/DATA/MASTER_IA/First-Semester/CI/Project/RepastSimphony-1.2.0/eclipse
JAVA_EXECUTABLE=java
JAVA_FLAGS=-client
ECLIPSE_PLUGINS=$ECLIPSE_HOME/plugins

WORKSPACE=/Volumes/DATA/MASTER_IA/First-Semester/CI/Project/workspace
NORMLAB_DIR=$WORKSPACE/TrafficSim
PROJECT_DIR=$NORMLAB_DIR

# Prepare CLASSPATH
CLASSPATH=.

TMP_LIBS="libs_todelete.txt"
echo "" > $TMP_LIBS

# Add libraries to path (Eclipse, Repast)
ECLIPSE_PLUGINS_ALL_JARS=`find $ECLIPSE_PLUGINS -type f -name '*.jar'`
for file in $ECLIPSE_PLUGINS_ALL_JARS
do
  echo "CLASSPATH=\$CLASSPATH:$file" >> $TMP_LIBS
done

# Add Virtual Communities Simulation libs
PROJECT_LIBS=`find $PROJECT_DIR/lib -type f -name '*.jar'`
for file in $PROJECT_LIBS
do
  echo "CLASSPATH=\$CLASSPATH:$file" >> $TMP_LIBS
done

# Add sources to path
echo "CLASSPATH=\$CLASSPATH:$PROJECT_DIR/bin" >> $TMP_LIBS

# Format Libraries (sort...)
sort $TMP_LIBS > "$TMP_LIBS-sorted"
echo "export CLASSPATH=\$CLASSPATH" >> "$TMP_LIBS-sorted"
source "$TMP_LIBS-sorted"
export CLASSPATH=$CLASSPATH

######################
# Launch experiments #
######################

FOLDER_NAME=prueba40-60
mkdir $FOLDER_NAME

java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher 40 60 500 5 5 $PROJECT_DIR'/batch/batch_params.xml'

CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M -Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml $PROJECT_DIR/trafficSim.rs > $NORMLAB_DIR/experiments/$FOLDER_NAME/experiment_log_$1.log

mv $PROJECT_DIR/experiments/logs/* $PROJECT_DIR/experiments/$FOLDER_NAME/Experiment_output_$1.txt
