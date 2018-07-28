# Creating the discovery service.

All our microservices will connect to a Service Registry. We will use the Discovery service provided by the [Spring Cloud Services for PCF](https://network.pivotal.io/products/p-spring-cloud-services).

Underneath the covers, this discovery service is implemented using the [Spring Cloud Netflix - Eureka](http://cloud.spring.io/spring-cloud-netflix/).

As such, all we have to do to implement a discovery service is to create a service instance.

### Exercise

1. Log in to the Apps Manager through your browser. The URL will be: `https://console.<your_cloud_foundry_url>/`

Go the *Marketplace* and choose a *Service Registry for Pivotal Cloud Foundry*.

When prompted for the name of the service, insert **"discovery-service"** and bind it to the space you are using to deploy your applications.

> You can pick any name of the service, however, the service is already specified in the manifest files, so it is easier to re-use that name. If you do modify the name, ensure you modify it in the manifest files as well.

# Summary

The registry server has a UI that can be accessed on the link provided in the console UI. Find the discovery service you created and click on **Manage**.

If you deployed your own Discovery service or are running it locally, you can also access the UI by going to the deployed application's URL.

You can now move on to [Creating the Circuit Breaker Dashboard](lab_circuitbreaker.md)
