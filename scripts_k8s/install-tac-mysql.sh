#!/bin/sh

# Need to have helm3 installed for this to work
helm repo add tacDemo https://charts.trials.tac.bitnami.com/demo
heml repo update
helm install acme-mysql tacDemo/mysql --version 6.14.0 --set useHelm3=true