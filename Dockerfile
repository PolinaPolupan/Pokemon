# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built .war file to the container
COPY build/libs/pkmn.war /app/pkmn.war

# Expose the Tomcat server port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/myapp.war"]
