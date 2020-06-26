#!/bin/sh


#Set your UNIQUE_APP_NAME . This will be used to identify this application for the following:
# 1. Docker Repo Name: repo name for all containers images for the microservices. For e.g for qoutes service,if you set it to
# acme-bank, then your container image will be acme-bank/quotes-service:version
# 2. Wavefront Application name. This will be used to uniquely identify your wavefront


# set some variables
SCRIPT_DIR=$(pwd)
BASE_DIR=$(dirname "$(pwd)")


