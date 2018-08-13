# Pivotal Bank Demo App

This demo app is a microservice version of the Spring Trader application.  It demonstrates
use of Spring Cloud Services in a reasonably complex set of microservices.  

>This repository was logically forked from the original [Pivotal-Bank](https://github.com/pivotal-bank) and collapsed
into a mono-repo for ease of rapid development by single demo-er.  Additional changes have been 
made to the repo that further strayed from the original to address demo needs.  In time, these changes
will be considered for inclusion in the origin pivotal-bank source.

![Spring Trader](/docs/springtrader2.png)

# Introduction

This repository holds a collection of micro services that work together to present a trading application surfaced though a web UI, but more interfaces can be created that re-utilise the microservices.

It was created to support workshops and demonstrations of building and using `microservices` architectures and running these in **Cloud Foundry** (although it is possible to run these on other runtimes).

The workshops follow a series of exercises, or labs, and you can find links to the guides for these exercises [below](#workshops).

## Table of Contents

1. [Architecture](#architecture)
2. [Deploying the application](#deployment)
3. [Workshops](#workshops)
4. [Demos](#demos)
5. [Roadmap](#roadmap)
6. [Contributing to the project](#contributing)


# Architecture
The system is composed of 5 microservices. The relationship between the microservices is illustrated below.

![architecture](/docs/base-architecture-diagram.png)

## 1. Quote Microservice
This service is a spring boot application responsible for providing up to date company and ticker/quote information. It does this by providing a REST api with 2 calls:
* ``/v1/quotes?q={symbol1,symbol2,etc}``
Returns as up to date quote for the given symbol(s).
* ``/v1/company/{search}``
Returns a list of companies that have the search parameter in their names or symbols.

This application has no dependencies apart from an external service - [markitondemand](http://dev.markitondemand.com/) - to retrieve the real time data.

## 2. Account Microservice
This service is a spring boot application responsible for creating and managing user accounts.

It stores the accounts in a RDBMS store and uses a spring JPA respository to accomplish this. It provides several REST api calls for other services to consume its services.

## 3. Portfolio Microservice
This service is a spring boot application responsible for managing portfolios - these are collections of holdings, which in turn are collection of orders on a particular share.

This service accepts orders (both BUY and SELL) and stores these in a RDBMS store - *it does not have to be the same RDBMS as the Account service, but it can be!* It provides REST api calls for other services to consume its services.

This service is dependent on the Account service above to ensure the logged in user has enough funds to buy stock as well as keeping the account funds up to date. It is also dependent on the Quote service to retrieve up to date quote information and calculate the current value of portfolios.

## 4. Web Microservice
This service is a spring boot application providing the web interface.

The web interface is built using bootstrap and Thymeleaf and uses a Spring controller to delegate calls to the relevant services:
* Account service
* Quote service
* Portfolio service

## 5. User Microservice
This service is a spring boot application providing the user repository and authorization services.

It stores the accounts in a RDBMS store and uses a spring JPA respository to accomplish this. It provides several REST api calls for other services to consume its services. 

# Deployment

To deploy the microservices manually please follow the guides of the [workshop below](#workshops)

Or if you want to quickly deploy all services to Pivotal Cloud Foundry with [Spring Cloud Services for PCF](https://network.pivotal.io/products/p-spring-cloud-services) follow the [scripted instructions](scripts/README.md)

Alternative, you can run most of the functionality locally, by following the [local instructions](docs/lab_local.md)

# Workshops:

The following guides describe how to setup the environment and deploy the microservices to **Cloud Foundry**.

At Pivotal we love education, not just educating ourselves, but also educating others. As such, these guides follow the *"teaching you how to fish"* principle - Rather than giving you line by line/command by command instructions, we provide guidelines and links to documentation where you can read and learn more.

1. [Setting up the environment](docs/lab_setup.md)
2. [Creating a discovery service](docs/lab_registryserver.md)
3. [Creating a circuit breaker dashboard](docs/lab_circuitbreaker.md)
4. [Creating the configuration service](docs/lab_configserver.md)
5. [Pushing the Quote service](docs/lab_pushquote.md)
6. [Pushing all the services](docs/lab_pushall.md)
7. [Scaling the services](docs/lab_scale.md)
8. [Blue/Green deployments](docs/lab_bluegreen.md)

# User Acceptance Test
In order to get familiar with Pivotal Bank, follow the [user acceptance test](docs/lab_manual_test.md) to go through basic functionality.

# Bonus Workshops

You can go further with the following bonus workshops.

1. [Zipkin Tracing](docs/lab_zipkin.md)
2. [Spring Cloud Gateway](docs/lab_spring_cloud_gateway.md)
3. [Container to Container Networking](docs/lab_c2c_networking.md)
4. [Add Elasticsearch and Spring Cloud Data Flow](docs/lab_pivotal_bank_analytics.md)

# Features

- **Discovery service:**
  All microservices register with the [Discovery Service](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html) and discover other microservices through it.
- **Correlation/Traceability:**
  Traceability of requests through all the microservices. This is done using [Spring-cloud-sleuth](http://cloud.spring.io/spring-cloud-sleuth/).
- **Config Server:**
  The microservices obtain the configuration from a [Configuration Service](http://cloud.spring.io/spring-cloud-config/) backed by a git repository. This means that configuration is now auditables and version controlled, as well as providing the ability to refresh configuration during runtime.

# Roadmap

The roadmap for this project is constantly evolving. Please feel free to reach out with ideas.
- **Better APIs:**
  Better APIs with documentation that conform to some standard and logic.
- **Security:**
  Secure microservices with OAUTH2.
- **Monitoring/Operations:**
  Show how to monitor a distributed system comprising of multiple microservices.  Zipkin or [PCF Metrics](http://docs.pivotal.io/pcf-metrics)

# Contributing
Everyone is encouraged to help improved this project.

The master branch has the latest release.

Here are some ways you can contribute:

- by reporting bugs
- by suggesting new features
- by writing or editing documentation
- by writing code (no patch is too small: fix typos, add comments, clean up inconsistent whitespace)
- by refactoring code
- by closing [issues](https://github.com/mid-atlantic-pa/pivotal-bank/issues)

## Submitting an Issue

We use the [GitHub issue tracker](https://github.com/mid-atlantic-pa/pivotal-bank/issues) to track bugs and features. Before submitting a bug report or feature request, check to make sure it hasn't already been submitted. When submitting a bug report, please include any relevant information. Ideally, a bug report should include a pull request with failing specs, and maybe even a fix!

## Submitting a Pull Request

1. Fork the project.
2. Create a topic branch.
3. Implement your feature or bug fix.
4. Commit and push your changes.
5. Submit a pull request.
