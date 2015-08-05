This sample can be built using either [Gradle](#building-with-gradle) or [Maven](#building-with-maven).

In addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the 12-factor-wlpcfg directory:

```text
12-factor-wlpcfg
 +- servers
     +- websocketSample                        <-- specific server configuration
        +- server.xml                          <-- server configuration
        +- apps                                <- directory for applications
           +- 12-factor-application.war        <- sample application
        +- logs                                <- created by running the server locally
        +- workarea                            <- created by running the server locally
```

## Building with Gradle

This sample can be built using [Gradle](http://gradle.org/). The build will pull down a copy of Liberty, build the sample and produce a packaged liberty server that can be run locally or pushed up to Bluemix. Before building the sample you must add the License Code to the build.gradle file. (This doesn't work yet as we are waiting on an update from Maven Central, so see workaround)

Current workaround:

```bash
$ gradle build publishToMavenLocal
```
[Download WAS Liberty manually](/docs/Downloading-WAS-Liberty.md) then in a command line navigate to the wlp/bin directory of your Liberty Runtime. Set the environment variable 'WLP_USER_DIR' to point to your 12-factor-wlpcfg project then run:

```bash
$ server package 12FactorAppServer --include=usr
```

This will give you a 12FactorAppServer.zip which contains your packaged server.


## Building with maven

This sample can be built using [Apache Maven](http://maven.apache.org/). The build will pull down a copy of Liberty, build the sample and produce a packaged liberty server that can be run locally or pushed up to Bluemix. 

:pushpin: Note: Before building the sample you must add the License Code to the pom.xml file in the 12-factor-wlpcfg project. You can obtain the license code by reading the [current license](http://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/wasdev/downloads/wlp/8.5.5.5/lafiles/runtime/en.html) and looking for the 'D/N: <license code>' line.

```bash
$ mvn install
```

## Next step:

[Starting the server using WDT and Eclipse](/docs/Using-WDT.md)
[Creating a Cloudant database](/docs/Creating-Cloudant-database.md)
[Deploying the application to Bluemix](/docs/Deploying-application-to-Bluemix.md)
