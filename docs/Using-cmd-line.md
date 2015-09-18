## Building and running the sample using the command line

### Clone Git Repo
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#clone-git-repo)

```bash

$ git clone https://github.com/WASdev/sample.microservices.12factorapp.git

```

### Building the sample
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#building-the-sample-in-eclipse)

This sample can be built using either [Gradle](#gradle-commands) or [Maven](#maven-commands).

###### [Gradle](http://gradle.org/) commands

The build will pull down a copy of Liberty, build the sample and produce a packaged liberty server that can be run locally or pushed up to Bluemix. Before building the sample you must add the License Code to the build.gradle file. You can obtain the license code by reading the [current license](http://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/wasdev/downloads/wlp/8.5.5.5/lafiles/runtime/en.html) and looking for the 'D/N: <license code>' line.

```bash
$ gradle build publishToMavenLocal
```

:pushpin: Note: To run functional tests and get a packaged server from the build you must [download WAS Liberty](/docs/Downloading-WAS-Liberty.md) and enter the location into the gradle.properties file.
:pushpin: Note: To get a packaged server without manually downloading Liberty enter the Liberty license code into the gradle.properties file. The license code can be found at the bottom of the [current license](http://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/wasdev/downloads/wlp/8.5.5.5/lafiles/runtime/en.html), look for the 'D/N: <license code>'. This option does not currently support running the functional tests.

###### [Apache Maven](http://maven.apache.org/) commands

By default, maven will build the sample and run unit tests.

```bash
$ mvn install
```

Running integration tests and producing a fully packaged liberty server that can be run locally or pushed to Bluemix requires a valid installation of liberty to work with.

* [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md) yourself, and specify where the installation is:

```bash
$ mvn install -Pwlp-install -Dwlp.install.dir=/path/to/installed/wlp
```

* Specify a licensed version of liberty that should be downloaded by the liberty-maven-plugin.

    The current developer license (as you would get from a direct wasdev.net download) can be found at the bottom of the [current license](http://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/wasdev/downloads/wlp/8.5.5.5/lafiles/runtime/en.html), look for the 'D/N: &lt;license code&gt;'.
```bash
$ mvn install -Pwlp-download -Dwlp.license=<license code>
```

Once a valid install has been located (e.g. when running locally), the install profile information can be omitted on the command line.

Liberty will be downloaded into the target directory by default, which means the profile parameters will be required for the first install after a clean.

In addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the 12-factor-wlpcfg directory:

```text
12-factor-wlpcfg
 +- servers
     +- 12FactorAppServer                      <-- specific server configuration
        +- server.xml                          <-- server configuration
        +- apps                                <-- directory for applications
           +- 12-factor-application.war        <-- sample application
        +- logs                                <-- created by running the server locally
        +- workarea                            <-- created by running the server locally
```



### Running the application locally
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#running-the-application-locally)

Pre-requisite: If the build hasn't installed Liberty already you need to [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md)
Pre-requisite: [Create and configure a Cloudant database](/docs/Creating-Cloudant-database.md). Make sure you have set the system environment variables 'dbUsername', 'dbPassword' and 'dbUrl' then you are ready to run your application.

Use the following to start the server and run the application:

```bash
$ export WLP_USER_DIR=/path/to/sample.microservices.12FactorApp/12-factor-wlpcfg
$ /path/to/wlp/bin/server run 12FactorAppServer
```

### Deploying to Bluemix using the command line
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#deploying-to-bluemix-using-eclipse)

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
