# README #

I tried to create a service as simple as possible in order to reduce external libraries and use Spring potential.

### How to run ###

class com.w2m.spaceship.SpaceshipApplication can be run from java:
```
java com.w2m.spaceship.SpaceshipApplication
```

Swagger front end can be accessed by http://localhost:8080/swagger-ui/index.html

#### Run with docker ####

The service is dockerized so:

- Run `mvn clean install` 
- Run `docker build --tag=spaceship:latest .` 
- Run `docker run -p8080:8080 spaceship:latest` 


## Architecture ##

The project is built with maven. Run to build and run test:
```
mvn clean install
```
The project is separated in layers: web -> domain -> database
- Web
    - Contains the @RestController responsible for MVC and open API.
    - Receive and validate the requests
    - Call to domain layer
    - Manage the error response when there are exceptions with ControllerAdvice.
- Domain
    - Contains the @Service classes.
    - It can't see the web layer.
    - Call to database and manage the data to response.
- Database
    - Contains the @Repository and @Entity to manage the database with JPA.
    - It can't see the domain layer.
    - Manage database connections and querys.

Each layer contains they own model to isolate the implementation and definition.
- Web model: it uses validation and swagger annotation to define the DTO model.
- Domain: is used to transform the data received from database.
- Database: it uses the persistence annotations to manage database data and DAO model.

#### Dependencies ####
- spring-boot-starter-web: a useful spring tool to generate a Restful application with MVC
- spring-boot-starter-validation: add bean validation
- spring-boot-starter-data-jpa: creates the connection to database and simplify the persistence.
- spring-boot-starter-aop: provides the tools to manage aspects.
- spring-boot-starter-actuator: is the spring module to monitorization.

#### External libraries ####

I have used external libraries with a solid background and community behind. They are developed thinking to be integrated in Spring:
- [lombok](https://projectlombok.org/): autogenerate code using annotations
- [mapstruct](https://mapstruct.org/): autogenerate the defined converter code in compilation time
- [micrometer](https://micrometer.io/): provides observability tools
- [OpenApi](springdoc.org): generate OpenApi documentation for Spring Web MVC application.

#### Traceability ####

Spring actuator and micrometer are activated to add traceId and spanId to the logs.

#### AOP ####

It's used to manage logging.
- I created a LogAspect and the annotation LogTime to log the execution time for every method with the annotation.
- I created a ControllerAspect to log the request, response and execution time for every endpoint in a controller.

#### Mapstruct ####

It's an external library to simplify mapping conversion between classes

#### TODO optional improvements ####

- I can use Maven modules to assure the isolation between layers.
  ```
  <modules>
    <module>w2m-service-database</module>
    <module>w2m-service-domain</module>
    <module>w2m-service-web</module>
  </modules>
  ```
- Add kafka consumer/producer
- Add authentication
- ControllerAspect: Request or response could have a big size. Do not have to be logged each time.
- If the service receives a request from another API, check the headers to copy spanId and traceId

### Sonar Issues ###

Vulnerabilities found in:

- spring-boot-starter-web
- spring-boot-starter-test
- spring-cloud-starter-sleuth

### TESTS ###

I created different tests for different targets
- WebMvcTest: to test controllers (web layer).
- DataJpaTest: to test database access.
- Test/ParameterizedTest: to test domain functionality.
- SpringBootTest: to create integration test.

Added Jacoco plugin to check coverage. Configuration, Aspect and main files are excluded.

### Commits ###

The idea is make small and simple commits to increase the value:

- feat: Initial commit

  Project has been created from https://start.spring.io/. Added README.md and .gitignore.


- feat: Create an endpoint with a generic response.


- feat: Add OpenAPi configuration and annotations.

  BREAKING CHANGE: These change will allow to frontEnd developers to see swagger definition.


- feat: Add controller validation and exception handler

  An error response model has been defined. Added validation to endpoint entries.

  BREAKING CHANGE: Entry params will be reviewed and requests will start to receive error response.


- feat: create H2 database

  Database has been configured to be load on startup. Entity and repository files are created and tested.


- feat: return database value to endpoint

  BREAKING CHANGE: After this change, service will return real values.


- feat: Add observability

  Aspects has been created to log request, response and execution time.

  Added trace and span id to the logs.


- feat: Add Jacoco as coverage tool

  Excluded configuration and aspect repository

  Excluded main class.


- feat: dockerize the project


