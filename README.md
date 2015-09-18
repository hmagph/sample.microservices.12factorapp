# Twelve-Factor Applications with Liberty

A Twelve-Factor application, as defined by [12factor.net](http://www.12factor.net), has characteristics that are ideal for developing individual microservices. To summarize briefly, a twelve-factor application:

1. is stored in a single codebase, tracked in a version control system: one codebase, many deployments. 
2. has explicitly declared and isolated external dependencies
3. has deployment-specific configuration stored in environment variables, and not in the code
4. treats backing services (e.g. data stores, message queues, etc.) as attached/replaceable resources
5. is built in distinct stages (build, release, run), with strict separation between them (no knock-on effects or cycles)
6. runs as one or more stateless processes that share nothing, and assume process memory is transient
7. is completely self-contained, and provides a service endpoint on well-defined (environment determined) host and port
8. is managed and scaled via process instances (horizontal scaling)
9. is disposable, with minimal startup, graceful shutdown, and toleration for abrupt process termination 
10. is designed for continuous development/deployment, with minimal difference between the app in development and the app in production
11. treats logs as event streams: the outer/hosting environment deals with processing and routing log files.
12. keeps one-off admin scripts with the application, to ensure the admin scripts run with the same environment as the application itself.


This sample contains an example for a Twelve-Factor Application that can be run on WAS Liberty and Bluemix. 


* Building and running this sample:
    * on the [command line](/docs/Using-cmd-line.md)
    * using [Eclipse and WebSphere Developer Tools](/docs/Using-WDT.md
* [Downloading WAS Liberty](/docs/Downloading-WAS-Liberty.md)
* [Creating a Cloudant database on bluemix](/docs/Creating-Cloudant-database.md)

Once the server has been started, go to http://localhost:9082/12-factor-application/ to view the sample locally or the route of your application to view the sample being hosted on Bluemix.

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
