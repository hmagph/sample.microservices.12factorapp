# Twelve-Factor Applications with Liberty

This will be a sample for creating a twelve-factor app using WAS Liberty. A twelve-factor app is an application that follows the methodology described here: [12factor.net](12factor.net). It is also a great methodology to use when creating microservices, so watch this space!

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