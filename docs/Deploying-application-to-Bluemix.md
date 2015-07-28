## Deploying the application onto Bluemix

There are two ways to do this:
* [Using the Cloud Foundry command line interface](#deploying-the-application-using-the-command-line)
* [Using Eclipse, WebSphere Developer Tools (WDT) and the Bluemix Eclipse plugin](#deploying-the-application-using-eclipse)

#### Deploying the application using the command line

First you will need to download the [Cloud Foundry command line interface][cloudfoundry], this can be used to deploy and manage applications on Bluemix. Next we need to configure the environment variables that will specify the credentials of Cloudant. (If you haven't created a Cloudant database yet see ['Creating a Cloudant database'](/docs/Creating-Cloudant-database.md).) These can be added to the application after it is deployed or by providing a manifest.yml file. Create a manifest.yml file using the following template:

```text
---
env:
   dbUsername: <Cloudant 'username' credential>
   dbPassword: <Cloudant 'password' credential>
   dbUrl: <Cloudant 'url' credential>
```

Then we simply push the zip file containing our packaged server to Bluemix.

```bash
$ \path\to\12-factor-application\build\libs> cf push <appName> -p 12FactorApp.zip -f \path\to\manifest.yml
```

Bluemix will use the manifest.yml file we provided as long as it is in the same directory as the zip we are pushing and will add the environment variables to our application.
[cloudfoundry]: https://www.ng.bluemix.net/docs/starters/install_cli.html

#### Deploying the application using Eclipse

To push applications to Bluemix from Eclipse you need the [IBM Eclipse Tools for Bluemix][eclipse-bluemix]. In Eclipse go to *help -> Eclipse Marketplace* and search for Bluemix. Once you have downloaded the tools you should be able to create a Bluemix server by right clicking in the servers tab, selecting *new > server*, selecting the server type *IBM Bluemix* and entering your Bluemix credentials. To push the application to Bluemix simply drag and drop the 12FactorApp.zip file into the Bluemix server. WDT will prompt you to enter a name for the application, environment variables and services to bind. Pick a name for the application and under environment variables specify the following:

1. dbUsername: <Cloudant 'username' credential>
2. dbPassword: <Cloudant 'password' credential>
3. dbUrl: <Cloudant 'url' credential>

#### Running the sample application on Bluemix

Once the app has been deployed to Bluemix it should start automatically. You can start, stop and view that status of your app on the Bluemix dashboard. To check that your application is running successfully you just need to visit the route displayed at the top of the overview tab of your app. Send a HTTP GET request to <route>/12-factor-application/, you should see a list of the databases in your Cloudant instance and a list of the documents in the 'items' database.

#### Note

The Liberty on Bluemix currently still has the beta version of jaxrs-2.0 so the manifest.yml also includes the environent variable IBM_LIBERTY_BETA Value: true.
