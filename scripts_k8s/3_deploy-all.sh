#!/bin/sh

# This script:
#   1) Reads microServices.list
#   2) Applies yml from the yaml directory
#       

source ./commons.sh

kubectl apply -f yamls/config-map.yml

main()
{
  file="microServices.list"
  while IFS= read -r app
  do
    if [ ! "${app:0:1}" == "#" ]
    then
      deploy $app
      sleep 2
    fi
  done < "$file"
  wait
}

main

printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0
