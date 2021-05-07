# railway-reservation-system

Steps to execute
Requirements
1) Java 8 installed
2) Maven installed
3) IDE (Intellij or Eclipse)
4) Mysql and mysql workbench
Open mysql workbench and run the createdatabasequery.sql.
Then open the project in one of the IDE and open application.properties file and change the myql username and password.

Then open the cmd at the same level as src folder and run  -> mvn clean install.
This will actually generate a jar in target folder.
Then open the cmd inside the target folder and run -> java -jar railwayreservation-0.0.1-SNAPSHOT.jar

You can see the webapp in locahost:8000/

Now you need to open mysql workbench again and run the trainquery.sql.

Now you can register a new user and login using that user and proceed
