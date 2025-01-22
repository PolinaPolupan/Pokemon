FROM openjdk:17-oracle
WORKDIR /app
RUN mkdir -p /app/staging
ARG INPUT_FILE=src/main/resources/students.csv
COPY ${INPUT_FILE} /app/resources/students.csv
COPY build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar", "input.file=/app/resources/students.csv"]