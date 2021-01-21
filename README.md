# GetchaPull - Backend - Spring and Postgresql

[![Apache-2.0 License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

GetchaPull website -> http://www.getchapull.wtf

<!-- ABOUT THE PROJECT -->
## About The Project

This version of GetchaPull is refactored version of [this](https://github.com/bariskbayram/getchapull-spring-backend/tree/v1-without_refactoring). It became more flexible and useful. PostgreSQL tables are designed from the beginning and implemented. In this version, I was using Spring-JDBC for database operations. The next version is planned to be with Spring-Data-JDBC or Spring-Data-JPA.

GetchaPull is a Twitter-like web page for sharing music reviews with others. In the beginning, I was trying to make a web page just for myself with the same aim. Then it became a bigger project than I thought. GetchaPull has 2 repo in Github, one of them is [Spring-Backend](https://github.com/bariskbayram/getchapull-spring-backend) and another one is [VueJS-Frontend](https://github.com/bariskbayram/getchapull-vuejs-frontend). 

The Spring-Backend side of GetchaPull is an API that handles VueJS-Frontend requests. In this implementation, 6 tables are used for representing the object in the database. On the GetchaPull website, I am using AWS S3 for storing profile photos, album covers, and band photos for now. When you wanna add a new review for one album, it's getting the band's and album's information from Spotify API, then stores information in DB and images in AWS S3.

* You can add new reviews to your profile.
* You can follow or unfollow other users.
* You can see other reviews belong to one user through its profile page.
* You can see the posts belonging to other users that are followed by you on the main page.

### Build and Run on your localhost

  * It requires AWS AccessKey ID and Secret AccessKey, so firstly you need to get these.  

1. Download and unzip the source repo, or clone it using Git:
  ```sh
    git clone https://github.com/bariskbayram/getchapull-spring-backend.git
   ```
2. cd into 
  ```sh 
    cd getchapull-spring-backend-refactor-postgres-spring-jdbc
  ```
3. Replace environment variables in Dockerfile through your AWS information instead of "?"
  ```sh
    ENV BUCKET_NAME="?"
    ENV AWS_ACCESSKEY_ID="?"
    ENV AWS_SECRET_ACCESSKEY="?"
   ```
4. Build jar: 
  ```sh 
    mvnw package && java -jar target/getchapull-spring-backend-0.1.0.jar 
  ```
5. Build images and run with Docker
  ```sh
    docker-compose up --build
  ```

* On this side of GetchaPull, it requires authentication so, if you wanna use the whole GetchaPull implementation, run the frontend side too.

### Build with and Third-party

* SpringBoot ( SpringBootWeb, SpringBootJdbc, SpringBootSecurity )
* PostgreSQL
* Guava
* Flyway
* JWT
* AWS-Java-SDK

[linkedin-shield]: https://img.shields.io/static/v1?label=LINKEDIN&message=BKB&color=<COLOR>
[linkedin-url]: https://www.linkedin.com/in/bar%C4%B1%C5%9F-kaan-bayram-121850101
[license-shield]: https://img.shields.io/static/v1?label=LICENCE&message=Apache-2.0&color=<COLOR>
[license-url]: https://github.com/bariskbayram/BusCardSystem/blob/master/LICENSE
