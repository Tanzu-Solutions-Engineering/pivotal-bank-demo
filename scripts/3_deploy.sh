#!/bin/sh

# This script:
#   1) Reads microServices.list
#   2) Pushes each microservices to PCF
#       

source ./commons.sh

deploy()
{
  echo_msg "Deploying $1"
  cd $BASE_DIR/$1
  cf push -f manifest.yml  --random-route
  if [ $? -eq 0 ]
  then
    echo "Successfully deployed $1"
  else
    echo "Could not deploy $1" >&2
    exit 1
  fi
}

main()
{
  file="microServices.list"
  while IFS= read -r app
  do
    if [ ! "${app:0:1}" == "#" ]
    then
      deploy $app &
      sleep 8
    fi
  done < "$file"
  wait
  
  summaryOfApps
  summaryOfServices
}

main

printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0
