# api
UserTaskAPI is a simple Spring boot application that manages users and user tasks. It is created with Spring Boot using REST endpoints without a UI. The API is able to create/list/update and delete users and user tasks via REST.The data is persisted to a database with an option of MySQL or H2 file-based database using jpa with hibernate implementation. 

The API structure consists of the following:
- a model (User and Task)
- a service (UserService and TaskService specifications and its implementations)
- RESTful Controller
- configuration files (pom.xml [specifies the API dependencies managed by Maven], 
persistence.xml - [specifying the jpapersistence unit thus connecting to MySQL database], 
application.properties - [specifies the connection to H2 file-based database].
Tech stack list [ Spring Boot, jpa with Hibernate implementation, Maven, Eclipse, Restful web services] 
