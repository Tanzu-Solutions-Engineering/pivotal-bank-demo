#!/bin/sh

# This script:
#   1) Reads microservices.list
#   2) Performs a gradle build on each line in the file

source ./commons.sh

build()
{
  echo_msg "Building $1"
  cd $BASE_DIR/$1
  ./gradlew build ; sleep 4
}

main()
{
  file="microServices.list"
  while IFS= read -r app
  do
    if [ ! "${app:0:1}" == "#" ]
    then
      build $app &
      #sleep 4
    fi
  done < "$file"
  wait
}

main

printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0
