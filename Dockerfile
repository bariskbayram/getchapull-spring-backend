FROM openjdk:8-jdk-alpine
ENV BUCKET_NAME="?"
ENV AWS_ACCESSKEY_ID="?"
ENV AWS_SECRET_ACCESSKEY="?"
ENV CORS_HOSTS="?"
COPY target/getchapull-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]