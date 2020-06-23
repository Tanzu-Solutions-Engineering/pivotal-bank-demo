#!/bin/sh

# This script:
#   1) Reads microServices.list
#   2) Pushes each microservices to PCF
#       

source ./commons.sh



main()
{
  file="microServices.list"
  while IFS= read -r app
  do
    if [ ! "${app:0:1}" == "#" ]
    then
      delete $app
      sleep 2
    fi
  done < "$file"
  wait
  
#  summaryOfApps
#  summaryOfServices
}

main
kubectl delete -f config-map.yml
kubectl delete -f yamls/spring-cloud-k8s

printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0
