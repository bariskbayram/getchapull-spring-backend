# GetchaPull - Backend - Spring and Postgresql

[![Apache-2.0 License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

GetchaPull website -> http://www.getchapull.wtf

<!-- ABOUT THE PROJECT -->
## About The Project

This version of GetchaPull was not intended to be to have that kind of functionality. So, it has a very bad implementation especially for Postgresql schemas and related things. :)

GetchaPull is a Twitter-like web page for sharing music reviews with others. In the begining, I was trying to make a web page just for myself on same aim. Then it became a more larger project than I thought. GetchaPull has 2 repo in Github, one of them is Spring-Backend and other one is VueJS-Frontend. 

Spring-Backend side of GetchaPull is a API that handles VueJS-Fronetend requests. In this awful implementation, 4 table is used for representing object in database and tables did not have any relation with each other(no FK or something). In GetchaPull web site, I am using AWS S3 for storing profile photos, album covers and band photos for now. When you wanna add a new review for one album, it's getting band and album informations from Spotify API, then stores informations in DB and images in AWS S3.

* You can add new reviews to your profile.
* You can follow or unfollow other users.
* You can see other reviews belongs to one user through its profile page.
* You can see the posts belonging to other users that are followed by you on the main page.

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
