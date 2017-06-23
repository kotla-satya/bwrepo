Design
------
-Only one toaddress is supported.

A Rest Service (EmailServiceController.java handles all api calls) to support various endpoints
Different service classes for different emailservice providers, any new service provider should implement EmailService or extend BaseEmailService
Appropriate service class is called based on property 'email.service.preferred' - is invoked 
application.properties has all properties related to the application(mailgun, mandrill, logging properties)

Tech Stack
----------
Java 8
Spring Boot
Maven for build scripts

What can be done better
------------------------
The Mandrill service is not working as it requires some dedicated domain and other setup for it to work.
A database to store the transactions to track, debug, dashboard reporting etc
Autentication to the service endpoints - /email
More detailed TestCase classes
Return more appropriate and detailed HTTP status codes (rightnow returning 200, 400, 500 error codes)
Html tags removal - it covers only certain scenarios
Better logging and track some heuristics of the api calls


Setup/How to use 
------------------
To Build Project and Start Application, Go to project folder > ./mvnw spring-boot:run
To change properties of email service provider - modify 'email.service.preferred' application.properties

To restart after changes -  kill the process by getting process id > ps -ef|grep 'spring-boot'

Post request : http://localhost:8080/email
Request body :
{
	"to": "kotla.satya@gmail.com",
	"to_name": "sk1",
	"from": "kotla.satya+2@gmail.com",
	"from_name": "sk",
	"subject": "helloo test",
	"body": "hello test"	
}

Get - http://localhost:8080/serviceProviders

