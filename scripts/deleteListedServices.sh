#!/bin/sh
source ./commons.sh

IFS=' '

delete()
{
  cf delete-service -f $1
}

file="./PCFServices.list"
while read service plan si
do
  if [ ! "${service:0:1}" == "#" ]
  then
    delete $si &
  fi
done < "$file"
wait

# Wait until services are ready
while cf services | grep 'delete in progress'
do
 sleep 20
 echo "Waiting for services to delete..."
done

summaryOfServices
exit 0
