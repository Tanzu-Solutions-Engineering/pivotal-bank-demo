# Spring Cloud Gateway
[Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/) provides simple and effective ways to route to your api
and provide facility for cross cutting concerns.

In this lab, we will deploy an gateway in front of our various microservice apis.  This provides a consistent entry point
for the api.  Then we will run an automated end to end test that targets the gateway.

# Build and Deploy the Gateway
You can explore the code of the gateway-service.  You will notice that it is very light.  It includes dependencies for
actuator, zipkin, and spring cloud services just like our other microservices.  The primary dependency is the 
spring-cloud-starter-gateway which includes the key components of the gateway.  The code simply defines our application
and includes security configuration to expose the actuator endpoints.

The gateway configuration is stored in our configuration git repo identifying each route, it's downstream target, 
its predicate and filter.  Here we refer to the downstream target using the logical identifier of managed by the 
discovery server.  Spring Cloud Gateway is quite extensible and additional filters could be added.

```
cd gateway-service
./gradlew build
cf push
```

Copy the generated URL for the gateway service for use in the end to end tests.

# Run the end to end tests
An end 2 end test suite exists at /e2e-tests/.  This runs junit tests targeting the application gateway and exercise
each of the microservices behind the gateway.  

```
cd e2e-tests
APPLICATION_URL=<gateway_url> mvn verify

```
