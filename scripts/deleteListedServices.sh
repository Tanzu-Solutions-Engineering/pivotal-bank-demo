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

summaryOfServices
exit 0
