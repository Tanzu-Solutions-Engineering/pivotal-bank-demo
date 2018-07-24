#!/bin/bash
set -e

pushd accounts-service/accounts-service
    ./gradlew clean assemble
popd

mkdir build-output/libs && cp accounts-service/accounts-service/build/libs/$ARTIFACT_ID.jar build-output/libs/.
cp accounts-service/accounts-service/manifest.yml build-output/.
