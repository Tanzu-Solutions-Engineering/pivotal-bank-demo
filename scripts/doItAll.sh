#!/bin/sh
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

trap 'abort $LINENO' 0
SECONDS=0
SCRIPTNAME=`basename "$0"`

./deleteListedApps.sh &
wait
./deleteListedServices.sh &
wait
sh ./1_createServices.sh
sh ./2_build.sh
sh ./3_deploy.sh
sh ./4_addTarget.sh

echo "Executed $SCRIPTNAME in $SECONDS seconds."
exit 0
