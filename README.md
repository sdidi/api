# api
UserTaskAPI is a simple Spring boot application that manages users and user tasks. It is created with Spring Boot and uses REST endpoints without a UI. The Restful API is able to create/list/update and delete users and user tasks via http requests.The data is persisted to a database with an option to use either MySQL or H2 file-based database using jpa with hibernate implementation. 

The API structure consists of the following:
- a model (User and Task)
- a service (UserService and TaskService specifications and its implementations)
- RESTful Controller
- configuration files (pom.xml [specifies the API dependencies managed by Maven], 
persistence.xml - [specifying the jpapersistence unit thus connecting to MySQL database], 
application.properties - [specifies the connection to H2 file-based database],
pom.xml - [maven dependency management])

The following HTTP calls can be made to test the API using Postman chrome plugin (or any other HTTP client):

-> POST - Create the user or user tasks.

-> GET - List the users or user tasks.

-> PUT - Update the users or user tasks.

-> DELETE - Delete the user or user tasks.

Tech stack list [Spring Boot, jpa with Hibernate implementation, Maven, Eclipse, Restful web services, Liqui-Base database migration] 

How to Run the API:

You will need to clone the codebase: https://github.com/sdidi/api.git 

Using IDE:

Run the project as: Spring Boot API and then test the calls with Postman plugin or curl.

Using command-line:

compile, test, package

mvn clean package
run

mvn spring-boot:run

For accessing H2 database dashboard:

Go to http://localhost:8080/console

JDBC URL: jdbc:h2:file:~/h2/testdb
User Name: sa
Password: Leave it blank

You have an option of MySQL through the persistence.xml configuration.