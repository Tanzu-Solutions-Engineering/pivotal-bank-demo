#!/bin/sh

set -e

# set some variables
. ./setVars.sh

abort()
{
    if [ "$?" = "0" ]
    then
        return
    else
      echo >&2 '
      ***************
      *** ABORTED ***
      ***************
      '
      echo "An error occurred on line $1. Exiting..." >&2
      exit 1
    fi
}

delete()
{
  set +e
  echo_msg "Deleting $1"
  cd $BASE_DIR/$1
  kubectl delete -f build/classes/java/main/META-INF/dekorate/kubernetes.yml
  if [ $? -eq 0 ]
  then
    echo "Successfully deleted $1"
  else
    echo "Could not delete $1" >&2
  fi
  set -e
}

deploy()
{

  echo_msg "Deploying $1"
  cd $BASE_DIR/$1
  kubectl apply -f build/classes/java/main/META-INF/dekorate/kubernetes.yml
  kubectl wait --for=condition=available deployments/$1 --timeout=60s
  if [ $? -eq 0 ]
  then
    echo "Successfully deployed $1"
  else
    echo "Could not deploy $1" >&2
    exit 1
  fi
}

#summaryOfServices()
#{
#  echo_msg "Current Services in CF_SPACE"
#  cf services | tail -n +4
#}
#
#summaryOfApps()
#{
#  echo_msg "Current Apps in CF_SPACE"
#  cf apps | tail -n +4
#}

echo_msg()
{
  echo ""
  echo "************** ${1} **************"
}

trap 'abort $LINENO' 0
SECONDS=0
SCRIPTNAME=`basename "$0"`
