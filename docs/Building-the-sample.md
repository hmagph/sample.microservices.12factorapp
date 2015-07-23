This sample can be built using either [Gradle](#building-with-gradle) or [Maven](#building-with-maven).

In addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the 12-factor-wlpcfg directory:

```text
async-websocket-wlpcfg
 +- servers
     +- websocketSample                        <-- specific server configuration
        +- server.xml                          <-- server configuration
        +- apps                                <- directory for applications
           +- async-websocket-application.war  <- sample application
        +- logs                                <- created by running the server locally
        +- workarea                            <- created by running the server locally
```

## Building with Gradle

This sample can be build using [Gradle](http://gradle.org/). The build will pull down a copy of Liberty, build the sample and produce a packaged liberty server that can be run locally or pushed up to Bluemix. Before building the sample you must add the License Code to the build.gradle file. (This doesn't work yet as we are waiting on an update from Maven Central, currently using a local version of the plugin)  

```bash
$ gradle build publishToMavenLocal
```

## Building with maven

This sample can be build using [Apache Maven](http://maven.apache.org/). (Will soon be adding the functionality to do the same with Maven so it pulls down Liberty but waiting on an update to Maven Central.)

```bash
$ mvn install
```

## Next step:

[Downloading WAS Liberty](/docs/Downloading-WAS-Liberty.md)