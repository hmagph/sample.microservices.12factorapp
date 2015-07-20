There are lots of ways to get your hands on WAS Liberty. Note that you will need a version of Liberty that has support for Servlet 3.1 and Jaxrs 2.0 for this sample (Java EE 7 Web Profile will do).

To download just the WAS Liberty runtime, go to the [wasdev.net Downloads page][wasdev], and choose between the [latest version of the runtime][wasdev-latest] or the [latest beta][wasdev-beta]. You can also download Liberty via [Eclipse and WDT](/wiki/#eclipse--wdt)

There are a few options to choose from (especially for the beta drivers). Choose the one that is most appropriate.
* There are convenience archives for downloading pre-defined content groupings
* You can add additional features from the repository using the [installUtility][installUtility] or the [maven][maven-plugin]/[Gradle][gradle-plugin] plugins.

[wasdev]: https://developer.ibm.com/wasdev/downloads/
[wasdev-latest]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-non-eclipse-environments/
[wasdev-beta]: https://developer.ibm.com/wasdev/downloads/liberty-profile-beta/
[installUtility]: http://www-01.ibm.com/support/knowledgecenter/#!/was_beta_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/rwlp_command_installutility.html
[maven-plugin]: https://github.com/WASdev/ci.maven
[gradle-plugin]: https://github.com/WASdev/ci.gradle

## Tips

* If you use bash, consider trying the [command line tools](https://github.com/WASdev/util.bash.completion), which provide tab-completion for the server and other commands.