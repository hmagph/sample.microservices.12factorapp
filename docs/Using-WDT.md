## Eclipse / WDT

The WebSphere Development Tools (WDT) for Eclipse can be used to control the server (start/stop/dump etc.). The tools also support incremental publishing with minimal restarts and works with a debugger to allow you to step through your applications plus many more features including:

* content-assist for server configuration (server configuration is minimal but the tools can help you find what you need)
* automatic incremental publish of applications so that changes can be written and tested locally without doing a build/publish cycle or restarting the server (the server does restart nice and quickly but it's still nice not being forced to do it!)

Installing WDT on Eclipse is a simple drag and drop process as explained on [wasdev.net][wasdev-wdt].

[wasdev-wdt]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-eclipse/

#### Clone Git Repo

If the sample git repository hasn't been cloned yet, WDT has git tools integrated into the IDE:

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
2. Right-click on this folder, and select *Copy path to Clipboard*
3. Select menu *File -> Import -> Gradle -> Gradle Project*
4. In the Root folder textbox, Paste in the repository directory
5. Click *Build Model* 
6. Select all the projects (there should be three) and click *Finish*
7. This will create 3 projects in Eclipse: sample.microservices.12factorapp, 12-factor-application, and 12-factor-wlpcfg

#### Import Maven projects into WDT

Alternatively if you built the sample with Maven:

1. In the Git Repository view, expand the 12factorapp repo to see the "Working Directory" folder
2. Right-click on this folder, and select *Copy path to Clipboard*
3. Select menu *File -> Import -> Maven -> Existing Maven Projects*
4. In the Root Directory textbox, Paste in the repository directory
5. Select *Browse..." button and select *Finish* (confirm it finds 3 pom.xml files)
6. This will create 3 projects in Eclipse: sample.microservices.12factorapp, 12-factor-application, and 12-factor-wlpcfg

:star: *Note:* If you did not use Eclipse/WDT to clone the git repository, follow from step 3, but navigate to the cloned repository directory rather than pasting its name.

#### Create a Runtime Environment in Eclipse and a Liberty Server

For the purposes of this sample, we will create the Liberty server (step 3 in the wasdev.net instructions) a little differently to create and customize a Runtime Environment that will allow the server to directly use the configuration in the `12-factor-wlpcfg` project.

##### Create a Runtime Environment in Eclipse
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

##### Add the User directory from Gradle or Maven project, and create a server
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

Before running the application you need to create a [Cloudant database on Bluemix](/docs/Creating-Cloudant-database.md). Make sure you have set the system environment variables 'dbUsername', 'dbPassword' and 'dbUrl' then you are ready to run your application.

1.  Select the `12-factor-application` project
2.  Right-click -> *Run As... -> Run On Server*
3.  Select the "WebShere Application Server under localhost" folder, and select *Finish*
4. Confirm web browser opens on "http://localhost:9082/12-factor-application/" and displays a list of all the databases and the content of the "items" database.
5. Alternatively open a browser on "http://localhost:9082/12-factor-application/Test" to see a 'hello world' message from the servlet

## Tips

* When importing the existing maven project into Eclipse, Eclipse will (by default) "helpfully" add this project to an (extraneous) ear. To turn this off, go to Preferences -> Java EE -> Project, and uncheck "Add project to an EAR" before you import the project. If you forgot to do this, just delete the ear project; no harm.

## Next step:

Try [deploying your application to Bluemix](/docs/Deploying-application-to-Bluemix.md)