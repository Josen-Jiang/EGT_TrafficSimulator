#!/bin/bash

#IN_PARAMS=${1:-params.xml}
IN_PARAMS=$1

echo "IN_PARAMS($IN_PARAMS)"

source `dirname $0`/config.sh

BATCH_PARAMS="-params $IN_PARAMS"

pushd .
cd $PROJECT_DIR
echo "pwd= `pwd`"

CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xmx400M repast.simphony.batch.BatchMain $BATCH_PARAMS  $PROJECT_DIR/trafficSim.rs 

popd
