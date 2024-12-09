# GetchaPull

[![Apache-2.0 License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

[Visit the Website](https://getchapull.wtf)

GetchaPull is a simple social platform for music lovers to share their thoughts on albums and discover new music. You can think of it like a mix between a review site and a social feed where you follow people for music-related updates.

## What You Can Do

* **Create a Profile:** Sign up, log in, and set up your music review space.
* **Review Albums:** Write reviews, rate albums, and fetch album details from Spotify automatically.
* **Follow Users:** See what others are reviewing and follow profiles you like.
* **Personalized Feed:** Reviews from the people you follow show up on your main page.
* **Save Media:** Album covers, band photos, and profile pictures are stored using AWS S3.
  
## How It Works

### Backend - [Spring-Backend Repository](https://github.com/bariskbayram/getchapull-spring-backend)

Handles everything behind the scenes:
  * Connects to a PostgreSQL database to store users, reviews, bands and albums. 
  * Authenticates users with JWT. 
  * Saves images and other files to AWS S3.

### Frontend - [VueJS-Frontend Repository](https://github.com/bariskbayram/getchapull-vuejs-frontend)

This is what users see and interact with:
  * Built with Vue.js. 
  * Fetches and displays data from the backend. 
  * Responsive and easy-to-use design.

### Branches Overview
* **master:** Latest stable version using Spring Data JPA.
* **spring-jdbc-postgres:** Refactored to use Spring JDBC.
* **v1-without-refactoring:** Very first version, not cleaned up.

## How to Run It Locally
### Requirements

* **Java 17+:** Tested with OpenJDK 17.
* **Docker:** To run the app easily.
* **AWS Keys:** Needed for S3 integration.

1. Clone the Backend Repo:
  ```sh
    git clone https://github.com/bariskbayram/getchapull-spring-backend.git
    cd getchapull-spring-backend
   ```
2. Update the Dockerfile: Replace the ? values with your own:
  ```sh
    ENV BUCKET_NAME="?"
    ENV AWS_ACCESSKEY_ID="?"
    ENV AWS_SECRET_ACCESSKEY="?"
    ENV CORS_HOSTS="?"
    ENV DATABASE_PASSWORD="?"
    ENV DATABASE_USER="?"
    ENV DATABASE_HOST="?"
   ```
4. Build jar: 
  ```sh 
    ./mvnw package && java -jar target/getchapull-0.0.1-SNAPSHOT.jar 
  ```
5. Build images and run with Docker
  ```sh
    docker-compose up --build
  ```

* Frontend Setup: Follow instructions in [VueJS-Frontend Repository](https://github.com/bariskbayram/getchapull-vuejs-frontend).

### Build with and Third-party

* SpringBoot(SpringBootWeb, SpringBootJPA, SpringBootSecurity)
* JWT
* PostgreSQL
* AWS SDK
* Lombok
* Guava
* Flyway

## UML Diagram (GetchaPull-UML)

![UML](GetchaPull-UML.svg)

[linkedin-shield]: https://img.shields.io/static/v1?label=LINKEDIN&message=BKB&color=<COLOR>
[linkedin-url]: https://www.linkedin.com/in/bar%C4%B1%C5%9F-kaan-bayram-121850101
[license-shield]: https://img.shields.io/static/v1?label=LICENCE&message=Apache-2.0&color=<COLOR>
[license-url]: https://github.com/bariskbayram/BusCardSystem/blob/master/LICENSE
