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
      *** STOPPING ***
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
  kubectl delete -f yamls/$1.yml
  if [ $? -eq 0 ]
  then
    echo "Successfully deleted $1"
  else
    echo "Could not delete $1" >&2
  fi
  set -e
}

copyYaml()
{

  echo_msg "Copying $1"
  cp $BASE_DIR/$1/build/classes/java/main/META-INF/dekorate/kubernetes.yml yamls/$1.yml
  if [ $? -eq 0 ]
  then
    echo "Successfully copied $1 yml"
  else
    echo "Could not copy $1 yml " >&2
    exit 1
  fi
}

deploy()
{

  echo_msg "Deploying $1"
  kubectl apply -f yamls/$1.yml
  kubectl wait --for=condition=available deployments/$1 --timeout=60s
  if [ $? -eq 0 ]
  then
    echo "Successfully deployed $1"
  else
    echo "Could not deploy $1" >&2
    exit 1
  fi
}


echo_msg()
{
  echo ""
  echo "************** ${1} **************"
}

trap 'abort $LINENO' 0
SECONDS=0
SCRIPTNAME=`basename "$0"`
