#!/bin/bash
set -e

pushd web
    ./gradlew clean assemble

    VERSION=`cat version-number`
popd

mkdir build-output/libs && cp web/build/libs/$ARTIFACT_ID-$VERSION.jar build-output/libs/.
cp web/build/manifest.yml build-output/.
