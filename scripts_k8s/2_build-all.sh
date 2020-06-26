#!/bin/sh

#!/bin/sh

# This script:
#   1) Reads microServices.list
#   2) Applies yml from the yaml directory
#

source ./commons.sh

build()
{
  cd $BASE_DIR
  ./gradlew clean build bootBuildImage -x test
}


copy()
{
  cd $SCRIPT_DIR
  file="microServices.list"
  while IFS= read -r app
  do
    if [ ! "${app:0:1}" == "#" ]
    then
      copyYaml $app
    fi
  done < "$file"
  wait

}

build
copy

printf "\nExecuted $SCRIPTNAME in $SECONDS seconds.\n"
exit 0


