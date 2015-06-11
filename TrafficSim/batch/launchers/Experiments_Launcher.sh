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
PROJECT_DIR=$NORMLAB_DIR
mkdir $NORMLAB_DIR/output/Experiment-10n1vsn290-density0.25
java -cp $PROJECT_DIR/bin batch.PopulationSwitcher $PROJECT_DIR'/experiments/files/populations/use/Population-n1vsn2.xml' $PROJECT_DIR'/experiments/files/populations/Population.xml' 
java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher 10 90 0.25 500 5 5 $PROJECT_DIR'/batch/batch_params.xml'
for i in `seq 100`; do
  cd $PROJECT_DIR
  CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M -Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml $PROJECT_DIR/trafficSim.rs > $NORMLAB_DIR/output/Experiment-10n1vsn290-density0.25/n1vsn2-Run$i-log.txt
  mv $PROJECT_DIR/experiments/logs/* $NORMLAB_DIR/output/Experiment-10n1vsn290-density0.25/Experiment_output_$i.txt
done
mkdir $NORMLAB_DIR/output/Experiment-30n1vsn270-density0.25
java -cp $PROJECT_DIR/bin batch.PopulationSwitcher $PROJECT_DIR'/experiments/files/populations/use/Population-n1vsn2.xml' $PROJECT_DIR'/experiments/files/populations/Population.xml' 
java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher 30 70 0.25 500 5 5 $PROJECT_DIR'/batch/batch_params.xml'
for i in `seq 100`; do
  cd $PROJECT_DIR
  CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M -Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml $PROJECT_DIR/trafficSim.rs > $NORMLAB_DIR/output/Experiment-30n1vsn270-density0.25/n1vsn2-Run$i-log.txt
  mv $PROJECT_DIR/experiments/logs/* $NORMLAB_DIR/output/Experiment-30n1vsn270-density0.25/Experiment_output_$i.txt
done
mkdir $NORMLAB_DIR/output/Experiment-50n1vsn250-density0.25
java -cp $PROJECT_DIR/bin batch.PopulationSwitcher $PROJECT_DIR'/experiments/files/populations/use/Population-n1vsn2.xml' $PROJECT_DIR'/experiments/files/populations/Population.xml' 
java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher 50 50 0.25 500 5 5 $PROJECT_DIR'/batch/batch_params.xml'
for i in `seq 100`; do
  cd $PROJECT_DIR
  CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M -Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml $PROJECT_DIR/trafficSim.rs > $NORMLAB_DIR/output/Experiment-50n1vsn250-density0.25/n1vsn2-Run$i-log.txt
  mv $PROJECT_DIR/experiments/logs/* $NORMLAB_DIR/output/Experiment-50n1vsn250-density0.25/Experiment_output_$i.txt
done
mkdir $NORMLAB_DIR/output/Experiment-70n1vsn230-density0.25
java -cp $PROJECT_DIR/bin batch.PopulationSwitcher $PROJECT_DIR'/experiments/files/populations/use/Population-n1vsn2.xml' $PROJECT_DIR'/experiments/files/populations/Population.xml' 
java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher 70 30 0.25 500 5 5 $PROJECT_DIR'/batch/batch_params.xml'
for i in `seq 100`; do
  cd $PROJECT_DIR
  CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M -Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml $PROJECT_DIR/trafficSim.rs > $NORMLAB_DIR/output/Experiment-70n1vsn230-density0.25/n1vsn2-Run$i-log.txt
  mv $PROJECT_DIR/experiments/logs/* $NORMLAB_DIR/output/Experiment-70n1vsn230-density0.25/Experiment_output_$i.txt
done
mkdir $NORMLAB_DIR/output/Experiment-90n1vsn210-density0.25
java -cp $PROJECT_DIR/bin batch.PopulationSwitcher $PROJECT_DIR'/experiments/files/populations/use/Population-n1vsn2.xml' $PROJECT_DIR'/experiments/files/populations/Population.xml' 
java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher 90 10 0.25 500 5 5 $PROJECT_DIR'/batch/batch_params.xml'
for i in `seq 100`; do
  cd $PROJECT_DIR
  CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M -Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml $PROJECT_DIR/trafficSim.rs > $NORMLAB_DIR/output/Experiment-90n1vsn210-density0.25/n1vsn2-Run$i-log.txt
  mv $PROJECT_DIR/experiments/logs/* $NORMLAB_DIR/output/Experiment-90n1vsn210-density0.25/Experiment_output_$i.txt
done
