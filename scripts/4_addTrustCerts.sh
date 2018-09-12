#!/bin/sh
# This script
#   1) Reads microservices.list
#   2) Adds the TRUST_CERTS env variable for unsigned cert environments (http://docs.pivotal.io/spring-cloud-services/1-5/common/service-registry/writing-client-applications.html)
#   3) restages the apps
source ./commons.sh

addTrustCerts()
{
  TRUST_CERTS=`cf target | grep "api endpoint" | cut -d" " -f5| xargs`
  cf set-env $1 TRUST_CERTS $TRUST_CERTS
  cf restage $1 &
}

echo "Attaching apps to Spring Cloud Services, watch progress in the Service Discovery Service"

file="./microServices.list"
while IFS= read -r app
do
  if [ ! "${app:0:1}" == "#" ]
  then
    app=`echo $app | cut -d "-" -f1`
        addTrustCerts $app
        sleep 4
  fi
done < "$file"

wait

summaryOfApps
summaryOfServices
printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0
