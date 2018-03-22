#!/bin/bash
set -e

pushd accounts-service
    ./gradlew clean assemble

    VERSION=`cat version-number`
popd

mkdir build-output/libs && cp accounts-service/build/libs/$ARTIFACT_ID-$VERSION.jar build-output/libs/.
cp accounts-service/build/manifest.yml build-output/.
