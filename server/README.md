# Simple web server

## Problem Statement

Test task: Create a simple web server that can sum up numbers in a multithreaded environment.

## Requirements

	1. Create a java http server application that accepts up to 20 simultaneous requests. You can use jetty or tomcat web servers as the engine. Arriving
requests will be http post requests.
	2. If a request with a number arrives, keep the number around, don't respond yet.
	3. If a request with they keyword "end" (without the quotes) arrives then respond with the sum of all received numbers to all open requests (e.g. if
you requests with numbers 4 and 7 and end, all three requests should get response 11).
	4. Requests can arrive in parallel at the same time, the system must not lose any numbers or requests.
	5. Expected numbers are without decimal places, sum of them will not exceed 10 billion.
	6. After doing the "end" calculation forget all the numbers and be ready for the repeat cycle of operation (getting new requests with new numbers,
giving out response on next end).
	7. Provide the application to us as source with build and deploy instructions (preferably it should build into a war file that can be run with jetty or
tomcat).
	8. Provide tests as well
	
## How to Build?

	1. Navigate to the server folder of the project
	2. If you are on a linux machine, execute the following command in terminal to build the application.
		a. sh build-linux.sh 
		b. You can also double click the file if you are in Linux GUI.
		c. You should be able to see "BUILD SUCCESS" and a war file by name "server-1.0.war" will be created under target folder.
	2. If you are on a windows machine, double click "build-windows.bat" file.
		a. You should be able to see "BUILD SUCCESS" and a war file by name "server-1.0.war" will be created under target folder.

## How to run in embedded tomcat server?
	
	1. Navigate to the server folder of the project
	2. If you are on a linux machine, execute the following command in terminal to start the application.
		a. sh run-linux.sh
		b. You can also double click the file if you are in Linux GUI.
	3. If you are on a windows machine, double click run-windows.bat to start the application.
	4. Server will start working on http://localhost:1337/

## How to deploy in tomcat server?

	1. After building the application, goto /server/target
	2. Look for "server-1.0.war" WAR file and copy it.
	3. Navigate to %TOMCAT_PATH%/webapps and paste the WAR file.
	4. Now navigate to %TOMCAT_PATH%/bin and run the following command 
		a. catalina.bat run (on Windows)
		b. catalina.sh run (on Unix-based systems)
	5. Server will start working on http://localhost:%PORT%/server-1.0/

## Tech Stack

- Spring Boot
- H2 Database
- Tomcat
- Mockito
