# Pushing all the services.

In this exercise, we will be deploying all the applications in the project to the cloud and create all required services.

### 1. Quote service
We have already deployed the quote service in the [previous lab](lab_pushquote.md), so nothing to do here.

### 2. Accounts service
The accounts service has a dependency on a RDBMS database.

**Cloud Foundry** provides a marketplace where administrators can enable certain services to be available to developers/operators. These services are called [*Managed services*](http://docs.pivotal.io/pivotalcf/devguide/services/#instances), in contrast to [*User-provided services*](http://docs.pivotal.io/pivotalcf/devguide/services/#user-provided-services).

There are a couple of ways to create a service in **Cloud Foundry**. For this service we will explore using the UI to create the service, but you can also create it using the CLI.

### Exercise

1. Log in to the Apps Manager through your browser. The URL will be: `https://console.<your_cloud_foundry_url>/`

Go the *Marketplace* and choose a MySQL service. In Pivotal Web Services we could use the "ClearDB MySQL Database" service. In Pivotal Cloud Foundry we can use the "MySQL for Pivotal Cloud Foundry" service. Depending on your cloud provider, you may have multiple plans to choose from. For this exercise, the smallest *free* plan will suffice.

When prompted for the name of the service, insert **"traderdb"** and bind it to the space you are using to deploy your applications.

> You can pick any name of the service, however, the service is already specified in the manifest files, so it is easier to re-use that name. If you do modify the name, ensure you modify it in the manifest files as well.

## 3. Portfolio service

The portfolio service has a dependency on 3 services:

- A RDBMS.
- The quote service.
- The Account service.

For the RDBMS, we will be re-using the service created for the *Accounts service*. This is for simplicity of these exercises, and it is possible and probably favorable to create a new DB service specific to the portfolio service.

The portfolio service also connects to the quote and account service. We are using a registry service to automatically and dynamically discover the other services. The other services are discovered automatically use the discovery service.

## 4. Web service
The Web service is the UI front-end and also acts as an API aggregator. As such, it uses all the other microservices in the project, i.e. The quote, account and portfolio services.

Similarly to above, we will be using the registry service to retrieve information about these microservices.

## 5. User service
The User service is the provides a user repository and authorization api

## 5. Push all the applications.

Now that we have all the required services created, let's push all the services.

### Exercises

1. Push each of the services to the platform.

> How could you push all the services in one go?
> The **Cloud Foundry** manifest file allows us to [define multiple applications in a single file](http://docs.pivotal.io/pivotalcf/devguide/deploy-apps/manifest.html#multi-apps)

2. (Optional if you are using cloud foundry with self-signed certificates) When the script has finished, set the `TRUST_CERTS` environment variable in each application to the API endpoint of your Pivotal Application Services instance (as in `api.example.com`), then restage the applications so that the changes will take effect.

```
$ cf set-env quotes TRUST_CERTS api.cloudfoundry.com
Setting env variable 'TRUST_CERTS' to 'api.cloudfoundry.com' for app accounts in org myorg / space outer as user...
OK
TIP: Use 'cf restage' to ensure your env variable changes take effect
$ cf restage accounts
```
> You only need to do this once per application.

Once completed, go to the URL of the Web service in your browser.

# Summary
Congratulations! You have now deployed a set of microservices to the cloud that interact with each other.

Feel free to familiarise yourself with the UI of the application. You can access the application in a browser on the URL provided at the end of the push command and see something similar to the image below.

![Spring Trader](/docs/springtrader.png)

Now you can go to [next lab](lab_scale.md)
