#!/bin/bash
set -e

pushd user-service
    ./gradlew clean assemble

    VERSION=`cat version-number`
popd

mkdir build-output/libs && cp user-service/build/libs/$ARTIFACT_ID-$VERSION.jar build-output/libs/.
cp user-service/build/manifest.yml build-output/.
