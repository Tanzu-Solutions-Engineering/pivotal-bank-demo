#!/bin/bash
set -e

pushd accounts-service
    pwd
    ls -ltr
    ./gradlew clean assemble
#
#    VERSION=`cat version-number`
popd

mkdir build-output/libs && cp accounts-service/accounts-service/build/libs/$ARTIFACT_ID.jar build-output/libs/.
cp accounts-service/build/manifest.yml build-output/.
