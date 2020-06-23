# Bank Demo on Kubernetes

TLDR - Use the scripts in this directory to deploy the microservices to K8s used to demo the full Tanzu portfolio

## Install Data Services from TAC
 
```shell script
1_installl-data-services.sh
```
Right now it installs MySQL from TAC demo repo. 
## Build

```shell script
2_build-all.sh
```
This builds all the microservices and copies the manifest YAMLs to the `yamls/` directory

## Deploy to K8s

Verify your K8s context is set to the right cluster

```shell script
3_deploy-all.sh
```
Applies the yamls from `yaml` directory 