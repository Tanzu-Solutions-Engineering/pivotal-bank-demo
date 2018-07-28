# Zipkin Lab
Spring Sleuth can integrate with Zipkin to send trace/span data. Then use Zipkin server to search and view trace/span data.
In this lab, integration is done via HTTP and configured to send 100% of trace data.

# Zipkin Server
The Spring Cloud Zipkin server has been deprecated, so we will use the zipkin-server app from
zipkin source itself.  A fork of that repo was brought into this repo within the sub-directory
zipkin-server.  Minor updates were made so that the server would register with the Spring Cloud Discovery Server
and a cloud foundry manifest was added.

# Build Zipkin Server
Build the binary...
```
cd zipkin-server
mvn clean package -DskipTests
``` 
# Deploy to Cloud Foundry
```
cd zipkin-server
cf push
``` 

# Try it out

1. Log into the pivotal bank web and follow the [user acceptance test](lab_manual_test.md)
2. Access the zipkin app, and search for recent spans

# Deploy to Locally
```
cd zipkin-server
java -jar target/zipkin.jar
``` 

# Reference
- [zipkin-server on github](https://github.com/openzipkin/zipkin/tree/master/zipkin-server)
- [Spring Sleuth Integration with Zipkin](https://cloud.spring.io/spring-cloud-sleuth/single/spring-cloud-sleuth.html#_sleuth_with_zipkin_via_http)