## Creating a Cloudant database on Bluemix

This sample connects to a Cloudant database that will be hosted on Bluemix. You sign up for a free 30-day trial of [Bluemix][bluemix].
Once you are logged into Bluemix go to your dashboard to create a cloudant service:

1. Select *USE SERVICES OR APIS*
2. Navigate to the Data Management section and select the *Cloudant NoSQL DB* service
3. Give the service a name, leave the App section as 'Leave unbound' and the plan as 'shared' then click *CREATE*

Now that we have a service we need to find out the service credentials so that we can add these as system variables to our local environment. Currently on Bluemix to find out the credentials of a service we have to bind the service to an app but this should change soon. If you already have an app in Bluemix jump to step 5 if not follow the instructions to create an app and bind the service:

1. In the Bluemix dashboard select *CREATE APP*
2. Choose *WEB*
3. Select the *Liberty for Java* starter and click continue
4. Give the app a name and click Finish
5. Navigate to the *Overview* section of your application and select *BIND A SERVICE OR API*
6. Select your cloudant service and click *ADD* (Bluemix will prompt you to restage your app, you can click cancel to this)
7. Select the arrow next to *Show Credentials* on your service, these are the credentials you will need for your system environment variables

Once you have created your service and found out the credentials you need to create system environment variables as follows:

1. Save the "username" as "dbUsername"
2. "password" as "dbPassword
3. "url" as "dbUrl"

You can now unbind your service by selecting the settings cog on the service but make sure not to delete the service completely.

[bluemix]: https://console.ng.bluemix.net/

#### Adding content to your database

Open your cloudant service in Bluemix and select *Launch*, this will open the Api for cloudant. In the databases tab create a new database called 'items'.


## Next step:

Once your cloudant service has been created with a database called 'items' you can:

* [Run the application locally](/docs/Using-WDT.md#running-the-sample-application), or
* [Deploy the application to Bluemix](/docs/Deploying-application-to-Bluemix.md).
