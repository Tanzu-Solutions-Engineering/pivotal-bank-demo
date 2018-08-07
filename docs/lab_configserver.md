# Creating the Configuration service.

All our microservices will retrieve their configuration from a Configuration service. We will use the Configuration service provided by 
the [Spring Cloud Services for PCF](https://network.pivotal.io/products/p-spring-cloud-services).

Underneath the covers, this discovery service is implemented using the [Spring Cloud Config](http://cloud.spring.io/spring-cloud-config/).

### Exercise

1. Log in to the Apps Manager through your browser. The URL will be: `https://console.<your_cloud_foundry_url>/`

Go the *Marketplace* and choose a *Config Server for Pivotal Cloud Foundry*.

When prompted for the name of the service, insert **"config-server"** and bind it to the space you are using to deploy your applications.

Add the following json for further configuration:

```json
{"git":{"uri":"https://github.com/Pivotal-Field-Engineering/pivotal-bank-demo.git","searchPaths":"cf-SpringBootTrader-config","label":"master"}}
```

> You can pick any name of the service, however, the service is already specified in the manifest files, so it is easier to re-use that name. If you do modify the name, ensure you modify it in the manifest files as well.

# Summary
The config server dashboard can be accessed on the link provided in the console UI. Find the config server service you created and click on Manage.

You can now move on to [pushing the quote service](lab_pushquote.md)
