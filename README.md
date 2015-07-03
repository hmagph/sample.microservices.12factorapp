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

1. In the Git Repository view, expand the websocket repo to see the "Working Directory" folder
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

##### Add the User directory from Gradle project, and create a server
1. Right-click on the Runtime Environment created above in the 'Runtime Explorer' view, and select *Edit*
2. Click the `Advanced Options...` link
3. If the `12-factor-wlpcfg` directory is not listed as a User Directory, we need to add it:
    1. Click New
    2. Select the `12-factor-wlpcfg` project
    3. Select *Finish*, *OK*, *Finish*
4. Right-click on the `12-factor-wlpcfg` user directory listed under the target Runtime Environment in the Runtime Explorer view., and select *New Server*.
5. The resulting dialog should be pre-populated with the websocketSample Liberty profile server.
6. Click *Finish*

###### Note
If the '12-factor-wlpcfg' project does not appear in the dialog to add a project to the User Directory make sure there is a 'shared' folder in the 12-factor-wlpcfg project with an empty .gitkeep file inside it.

#### Running Liberty and the sample application from WDT
You're now ready to run your application.

1.  Select the `12-factor-application` project
2.  Right-click -> *Run As... -> Run On Server*
3.  Select the "WebShere Application Server under localhost" folder, and select *Finish*
4a.  Confirm web browser opens on "http://localhost:9082/12-factor-application/rest" with message 'Hello this is a response'
4b. Alternatively open a browser on "http://localhost:9082/12-factor-application/Test" to see a 'hello world' message from the servlet

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