# cf-SpringBootTrader-extras
Extra tools to work with the [cf-SpringBootTrader](https://github.com/dpinto-pivotal/cf-SpringBootTrader) project.

This project provides a few extra tools for [cf-SpringBootTrader](https://github.com/dpinto-pivotal/cf-SpringBootTrader), such as:

1. [Discovery Server:](#1-discovery-server)

  This is a discovery server for microservices to register and find out about other services. Some platforms provide one of these, such as Pivotal Cloud Foundry, others don't. Also, if you want to test the [cf-SpringBootTrader](https://github.com/dpinto-pivotal/cf-SpringBootTrader) locally, you'll need to run one of these.
2. [Config Server:](#2-config-server)

  This is a config server for microservices to manage the configuration for all the services backed by a git repository.
3. Turbine server:

  This is a turbine and hystrix dashboard server to display circuit breaker information. It uses the discovery server above to find out where and which services to grab information from.
4. Spring Boot Admin - bootAdmin:
  This is an administration dashboard for Spring boot applications. It retrieves the list of spring boot applications from the Discovery service, and then uses the Spring boot actuator endpoints to retrieve information about each application.

##How to run

###1. Discovery Server:
####To run locally
  Run the project using `gradlew bootRun` or build the project with `gradlew build` and then run with `java -jar build/libs/registry-0.0.1.jar`

####To run in Pivotal Cloud Foundry
  Build the project using `gradlew build` and then *push* the `build/libs/registry-0.0.1.jar` to the cloud.

  You may want to create a manifest file to set things such as memory and routes.

###2. Config Server:
The config server is backed by a git repository, by default it points at: https://github.com/dpinto-pivotal/cf-SpringBootTrader-config

It is possible to change the repository by setting the `spring.cloud.config.server.git.uri` variable

####To run locally
  Run the project using `gradlew bootRun` or build the project with `gradlew build` and then run with `java -jar build/libs/configServer-0.0.1.jar`

####To run in Pivotal Cloud Foundry
  Build the project using `gradlew build` and then *push* the `build/libs/configServer-0.0.1.jar` to the cloud.

  You may want to create a manifest file to set things such as memory, routes and a different git repo.
