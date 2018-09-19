# Start with a base image containing Java runtime
##FROM openjdk:8-jdk-alpine

# Add Maintainer Info
##MAINTAINER Partha Biswas

# Add a volume pointing to /tmp
##VOLUME /tmp

###ADD /dev/./urandom/tasktracker.mv.db /tmp/tasktracker.mv.db

# Make port 8080 available to the world outside this container
##EXPOSE 8082

# The application's jar file
##ARG JAR_FILE=target/TaskManagerService-0.0.1-SNAPSHOT.war

# Add the application's jar to the container
##ADD ${JAR_FILE} TaskManagerService.war

# Run the jar file 
##ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /TaskManagerService-0.0.1-SNAPSHOT.war"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE
COPY ${JAR_FILE} TaskManagerService.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/TaskManagerService.jar"]