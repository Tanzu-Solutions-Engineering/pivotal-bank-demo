#!/bin/bash
set -e

pushd accounts-service/accounts-service
    pwd
    ls -ltr
    ./gradlew clean assemble
#
#    VERSION=`cat version-number`
popd
ls -ltr accounts-service
ls -ltr accounts-service/accounts-service
ls -ltr accounts-service/accounts-service/build
ls -ltr accounts-service/accounts-service/build/libs

#./accounts-service/accounts-service/gradlew clean assemble

mkdir build-output/libs && cp accounts-service/accounts-service/build/libs/$ARTIFACT_ID.jar build-output/libs/.
cp accounts-service/accounts-service/manifest.yml build-output/.
