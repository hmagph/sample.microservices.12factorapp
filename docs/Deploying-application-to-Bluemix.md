## Deploying the application onto Bluemix
The application can also be deployed onto Bluemix to run. First you will need to download the [Cloud Foundry command line interface][cloudfoundry], this can be used to deploy and manage applications on Bluemix. To tell the Liberty buildpack which features we need there is a manifest.yml file in the 12-factor-wlpcfg project that defines the JPB_CONFIG_LIBERTY environment variable. When we push the application we need to tell Bluemix where to find this manifest.yml file.

```bash
$ \path\to\servers\12FactorAppServer\apps> cf push -p 12-factor-application.war -f ..\manifest.yml
```

[cloudfoundry]: https://www.ng.bluemix.net/docs/starters/install_cli.html

#### Running the sample application on Bluemix

Once the app has been deployed to Bluemix it should start automatically. You can start, stop and view that status of your app on the Bluemix dashboard. To check that your application is running successfully you just need to visit the route displayed at the top of the overview tab of your app. You should see a list of the databases in your Cloudant instance and a list of the documents in the 'items' database.

#### Note

The Liberty on Bluemix currently still has the beta version of jaxrs-2.0 so the manifest.yml also includes the environent variable IBM_LIBERTY_BETA Value: true.
