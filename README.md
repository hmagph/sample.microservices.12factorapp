# Twelve-Factor Applications with Liberty

This sample contains an example for a Twelve-Factor Application that can be run on Liberty and Bluemix. A Twelve-Factor App is an application that follows the methodology described here: [12factor.net](12factor.net) and is also a great methodology to use when following a microservices architecture.

* Building this sample:
	* [Building with Maven](#building-with-maven)
	* [Building with Gradle](#building-with-gradle)
* [Downloading WAS Liberty](#downloading-was-liberty)
* [Creating a Cloudant database on bluemix](#creating-a-cloudant-database-on-bluemix)
* Running the application:
	* Starting the local server using [Eclipse and WebSphere Development Tools (WDT)](#eclipse--wdt)
	* Deploying the application onto [Bluemix](#deploying-the-application-onto-bluemix)
	

## Building with Maven

This sample can be build using [Apache Maven](http://maven.apache.org/).

```bash
$ mvn install
```
 In addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the 12-factor-wlpcfg directory:

```text
12-factor-wlpcfg
 +- servers
     +- 12FactorAppServer                      <-- specific server configuration
        +- server.xml                          <-- server configuration
        +- apps                                <- directory for applications
           +- 12-factor-application.war        <- sample application
        +- logs                                <- created by running the server locally
        +- workarea                            <- created by running the server locally
```

## Building with Gradle

This sample can be built using [Gradle](http://gradle.org/).

```bash
$ gradle build publishToMavenLocal
```

The build.gradle files are configured such that, in addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the 12-factor-wlpcfg directory:

```text
12-factor-wlpcfg
 +- servers
 	 +- 12FactorAppServer					<-- specific server configuration
 	 	+- server.xml						<-- server configuration
 	 	+- apps								<-- directory for applications
 	 		+- 12-factor-application.war	<-- sample application
 	 	+- logs								<-- created by running the server locally
 	 	+- workarea							<-- created by running the server locally
```

## Downloading WAS Liberty

For this sample you will need a version of Liberty that has support for Servlet 3.1 and Jaxrs 2.0.

To download just the WAS liberty runtime, go to [wasdev.net Downloads page][wasdev], and choose between the [latest version of the runtime][wasdev-latest] or the [latest beta][wasdev-beta]. You can also download Liberty via [Eclipse and WDT](#eclipse--wdt).

There are a few options to choose from (especially for the beta drivers): choose the one that is most appropriate.
* There are convenience archives for downloading pre-defined content groupings,
* You can add additional features from the repository using the [installUtility][installUtility] or the liberty-maven-plugin and [Gradle plugins](https://github.com/WASdev/ci.gradle).

[wasdev]: https://developer.ibm.com/wasdev/downloads/
[wasdev-latest]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-non-eclipse-environments/
[wasdev-beta]: https://developer.ibm.com/wasdev/downloads/liberty-profile-beta/
[installUtility]: http://www-01.ibm.com/support/knowledgecenter/#!/was_beta_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/rwlp_command_installutility.html

## Eclipse / WDT

The WebSphere Development Tools (WDT) for Eclipse can be used to control the server (start/stop/dump etc.). The tools also support incremental publishing with minimal restarts and works with a debugger to allow you to step through your applications plus many more features including:

* content-assist for server configuration (server configuration is minimal but the tools can help you find what you need)
* automatic incremental publish of applications so that changes can be written and tested locally without doing a build/publish cycle or restarting the server (the server does restart nice and quickly but it's still nice not being forced to do it!)

Installing WDT on Eclipse is a simple drag and drop process as explained on [wasdev.net][wasdev-wdt].

[wasdev-wdt]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-eclipse/

#### Clone Git Repo

You can use the git tools built into the WDT IDE to clone the git repository:

1.  Open the Git repositories view
    * *Window -> Show View -> Other*
    * Type "git" in the filter box, and select *Git Repositories*
2.  Copy Git repo url by finding the textbox under "HTTPS clone URL" at the top of this page, and select *Copy to clipboard*
3.  In the Git repositories view, select the hyperlink `Clone a Git repository`
4.  The git repo url should already be filled in.  Select *Next -> Next -> Finish*
5.  The "sample.microservices.12factorapp [master]" repo should appear in the view

#### Import Gradle projects into WDT

Once the git repository has been cloned you need to import the Gradle projects so they can be deployed to a server.

First make sure that you have the Gradle IDE tools installed into Eclipse (help > Eclipse Marketplace > search for gradle) then follow the steps below:

1. In the Git Repository view, expand the 12factorapp repo to see the "Working Directory" folder
2.  Right-click on this folder, and select *Copy path to Clipboard*
3.  Select menu *File -> Import -> Gradle -> Gradle Project*
4.  In the Root folder textbox, Paste in the repository directory.
5. Click *Build Model* 
6. Select all the projects (there should be three) and click *Finish*
7.  This will create 3 projects in Eclipse: sample.microservices.12factorapp, 12-factor-application, and 12-factor-wlpcfg

#### Create a Runtime Environment in Eclipse

Before creating a server you need a runtime environment for your server to be based on.

1. Open the 'Runtime Explorer' view:
    * *Window -> Show View -> Other*
    * type `runtime` in the filter box to find the view (it's under the Server heading).
2. Right-click in the view, and select *New -> Runtime Environment*
3. Give the Runtime environment a name, e.g. `wlp-2015.6.0.0` if you're using the June 2015 beta.
4. Either:
    * Select an existing installation (perhaps what you downloaded earlier, if you followed those instructions), or
    * select *Install from an archive or a repository* to download a new Liberty archive.
5. Follow the prompts (and possibly choose additional features to install) until you *Finish* creating the Runtime Environment

#### Add the User directory from Gradle project, and create a server
1. Right-click on the Runtime Environment created above in the 'Runtime Explorer' view, and select *Edit*
2. Click the `Advanced Options...` link
3. If the `12-factor-wlpcfg` directory is not listed as a User Directory, we need to add it:
    1. Click New
    2. Select the `12-factor-wlpcfg` project
    3. Select *Finish*, *OK*, *Finish*
4. Right-click on the `12-factor-wlpcfg` user directory listed under the target Runtime Environment in the Runtime Explorer view., and select *New Server*.
5. The resulting dialog should be pre-populated with the 12FactorAppServer Liberty profile server.
6. Click *Finish*

###### Note
If the '12-factor-wlpcfg' project does not appear in the dialog to add a project to the User Directory make sure there is a 'shared' folder in the 12-factor-wlpcfg project with an empty .gitkeep file inside it.

#### Running the sample application

Before running the application you need to create a [Cloudant database on Bluemix](creating-a-cloudant-databse-on-bluemix). Once this is done you are ready to run your application.

1.  Select the `12-factor-application` project
2.  Right-click -> *Run As... -> Run On Server*
3.  Select the "WebShere Application Server under localhost" folder, and select *Finish*
4. Confirm web browser opens on "http://localhost:9082/12-factor-application/" and displays a list of all the databases and the content of the "items" database.
5. Alternatively open a browser on "http://localhost:9082/12-factor-application/Test" to see a 'hello world' message from the servlet

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

Once your cloudant service has been created with a database called 'items' you can [run the application locally](#running-the-sample-application) or [deploy the application to Bluemix](deploying-the-application-onto-bluemix).

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

## Notice

© Copyright IBM Corporation 2015.

## License

```text
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````