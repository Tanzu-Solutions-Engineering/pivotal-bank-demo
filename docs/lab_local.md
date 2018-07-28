# Running Pivotal Bank Locally

You can run pivotal bank locally and get full functionality with some modified circuit breaker dashboard experience.
The **key** is ensuring that each of the dependencies are running.

## Common Dependencies

All services depend on a config server and discovery server.  Run the following commands in separate terminal windows.
The discovery server has configurations specific for running locally

1. Config Server (runs on localhost:8888)
You can test it out by accessing http://localhost:8888/quotes-service/local.  The configuration 
of this config server watches the local file system where cf-SpringBootTRADER-config is.  So to have your changes
take affect, you only have to edit the file.  No need to commit if you are just playing around.
```bash
cd configServer
./gradlew bootRun
```

2. Discovery Server (runs on localhost:8761)
You can test it out by accessing http://localhost:8761/
```bash
cd configServer
./gradlew bootRun

```

## Services
In order to run the services, you will need to launch the following in separate terminal windows
```
cd <service_director>
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```
Use the following for dependencies:
- quotes: http://localhost:8083 , no dependencies
- accounts: http://localhost:8082 , no dependencies
- user: http://localhost:8084 , no dependencies
- portfolio: http://localhost:8081 , depends on quotes and accounts
- web: http://localhost:8080 , depends on portfolio, users, quotes, and accounts