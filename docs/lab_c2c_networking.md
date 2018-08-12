# Container to Container Networking and SCS Discovery Server

## Background
By default, apps register themselves with the discovery server based upon their route URI and port.  
This means that any service to service communication is going to leave the foundation and go back in through
the go router.  In order to leverage container to container networking you must do two things.

1. Specify registration method for the app to be [direct](http://docs.pivotal.io/spring-cloud-services/1-5/common/service-registry/writing-client-applications.html#register-using-c2c),
instead of the default `route`.

```
spring.cloud.services.registrationMethod = direct
```

2. [Add a networking policy](http://docs.pivotal.io/spring-cloud-services/1-5/common/service-registry/writing-client-applications.html#consume-using-c2c)
 to allow access from the calling app to the registered app
 
```
cf add-network-policy gateway --destination-app quotes
```

## Prerequisites
This lab leverages the spring cloud gateway.  Setup the gateway first following the [gateway lab](lab_spring_cloud_gateway.md).
 
## Lab
1. Update the registrationMethod configuration for quotes service by editing quotes-services-cloud.yml and pushing your commit

2. Restart quotes service
```
cf restart quotes
```
3. View the discover service's management page and notice that the quote-service URL is an IP address, while the others are the route.

4. Access the quote service through the gateway
```
http  <GATEWAY_URL>/quotes/v1/quotes?q=PVTL
```
Notice that the request fails with 500 error.  If you looked at the gateway logs, it would say it could not make a connection

5. Apply the network policy
>Note: your user account must have network.admin or network.write scopes within UAA
```
cf add-network-policy gateway --destination-app quotes
```

6. Retest the quote service through the gateway
```
http  <GATEWAY_URL>/quotes/v1/quotes?q=PVTL
```
Now it is successful! 
