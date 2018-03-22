#!/bin/sh

source ./commons.sh

delete()
{
  cf delete -f -r $1
}

summaryOfApps

file="./microServices.list"
while IFS= read -r app
do
  if [ ! "${app:0:1}" == "#" ]
  then
    app=`echo $app | cut -d "-" -f1`
    delete $app &
  fi
done < "$file"

#Annoying hack
delete webtrader &

wait

summaryOfApps
exit 0
