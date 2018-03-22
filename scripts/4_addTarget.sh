#!/bin/sh
# This script
#   1) Reads microservices.list
#   2) Adds the CF target env variable
#   3) restages the apps
source ./commons.sh

addTarget()
{
  CF_TARGET=`cf target | grep "API" | cut -d" " -f5| xargs`
  cf set-env $1 CF_TARGET $CF_TARGET
  cf restage $1 &
}

echo "Attaching apps to Spring Cloud Services, watch progress in the Service Discovery Service"

file="./microServices.list"
while IFS= read -r app
do
  if [ ! "${app:0:1}" == "#" ]
  then
    app=`echo $app | cut -d "-" -f1`
    addTarget $app
    sleep 4
  fi
done < "$file"

#Annoying hack
addTarget webtrader
wait

summaryOfApps
summaryOfServices
printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0
