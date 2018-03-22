#!/bin/bash
set -e

pushd portfolio-service
    ./gradlew clean assemble

    VERSION=`cat version-number`
popd

mkdir build-output/libs && cp portfolio-service/build/libs/$ARTIFACT_ID-$VERSION.jar build-output/libs/.
cp portfolio-service/build/manifest.yml build-output/.
