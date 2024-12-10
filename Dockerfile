FROM openjdk:17-jdk-alpine

ENV AWS_ACCESSKEY_ID="***"
ENV AWS_SECRET_ACCESSKEY="***"
ENV BUCKET_NAME="***"
ENV CORS_HOSTS="http://<IP_ADDRESS>:8082"
ENV DATABASE_HOST="jdbc:postgresql://<IP_ADDRESS>:5433/getchapull"
ENV DATABASE_USER="postgres"
ENV DATABASE_PASSWORD="postgres"

COPY target/getchapull-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]