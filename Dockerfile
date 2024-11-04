FROM openjdk:21
COPY target/spaceship-0.0.1-SNAPSHOT.jar spaceship-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/spaceship-0.0.1-SNAPSHOT.jar"]