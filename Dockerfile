FROM openjdk:17-jdk-alpine
ENV BUCKET_NAME="?"
ENV AWS_ACCESSKEY_ID="?"
ENV AWS_SECRET_ACCESSKEY="?"
ENV CORS_HOSTS="?"
ENV DATABASE_PASSWORD="?"
ENV DATABASE_USER="?"
ENV DATABASE_HOST="?"
COPY target/getchapull-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]