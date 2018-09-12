# What am I??
A bunch of scripts that either deploy or clean out <a href="https://github.com/pivotal-bank/cf-SpringBootTrader">Spring Boot Trader</a>

**PLEASE NOTE**
* Before running these scripts, open ```setVars.sh``` and edit the line for BASE_DIR, point this at the location where you have Spring Boot Trader cloned. 
* Optionally edit the line GITHUB_URI to point it to your github containing configuration (or accept the default)

## Scripts to deploy everything
If you want ultimate simplicity just run:

``` ./doItAll.sh ```

This will delete previous app instances, previous service instances, re-create the services, build and push the apps (and activate certificates with Spring Cloud Services).  If you are using self-signed certs you will need to add the ```add-certs``` option:  

``` ./doItAll.sh add-certs ```

See the 4_addTrustCerts.sh section below for more details.
## Scripts to deploy in three steps
These scripts are numbered, just run them in the logical order of numbering from script 1 all the way to the last script  :)

### 1_createService.sh
This script creates PCF Services from the marketplace - specifically an instance of MySQL, ConfigServer, ServiceDiscovery and CircuitBreaker. It chooses which services and service plans to create by reading a file called ```PCFServices.list```
It won't fail or re-build these services if they already exist in the CF space with the same name. Run the corresponding delete script first if you want a clean start (see below).

To execute simply run:

``` ./1_createService.sh ```

After it creates the services, this script pauses 60 seconds to allow the Spring Cloud Services to initialise, if you want to skip this just hit Ctl+C.

### 2_build.sh
This script performs a parallel Gradle build of the microservices. The parallelism is staggered. It reads the Micoservices to build and deploy from a file called ```microServices.list```. Edit this file if you have more microservices or need to remove any.

To execute simply run:

``` ./2_build.sh ```

### 3_deploy.sh
This script performs a parallel cf push of all the Microservices (parallelism is staggered). It reads the Micoservices to build and deploy from a file called ```microServices.list```. Edit this file if you have more microservices or need to remove any.

To execute simply run:

``` ./3_deploy.sh ```

### 4_addTrustCerts.sh
This step is only necessary if you are running in a PCF environment which <a href="https://docs.pivotal.io/spring-cloud-services/service-registry/writing-client-applications.html" target="_blank">uses self-signed certificates</a>.  If you are not on such a PCF environment, don't run this step.

The script is necessary to make your Microservices register with Eureka in Service Discovery, if you are using self-signed certs and do not run it, then none of your Microservices will Register in Service Discovery even though they have bound to the service. It also uses the ```microServices.list``` file.

To execute simply run:

``` ./4_addTrustCerts.sh ```

## Scripts to delete the apps and services

Delete the apps first and then the services. There are two scripts, so simply run:

```
./deleteListedApps.sh
./deleteListedServices.sh
```

The scripts rely on the files ```PCFServices.list``` and ```microServices.list```
Both scripts forcibly remove objects, in addition the delete apps script removes the route as well.
