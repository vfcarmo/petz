# Petz API

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Petz is a micro-service REST API responsible to manage customers and pets.

# New Features!

  - RESTful API
  
### Tech

Petz API was developed using the technologies bellow:

* [Java] - Java 11
* [Spring Boot] - Spring Boot 2
* [PostgreSQL] - PostgreSQL
* [Lombok] - Lombok
* [Mapstruct] - Mapstruct
* [Docker-Compose] - Docker Compose
* [Gradle] - Gradle
* [JUnit] - JUnit
* [Mockito] - Mockito
* [Cucumber] - Cucumber
* [Postman] - Postman


### Installation

Petz API need to be cloned on GitHub.

```sh
$ git clone https://github.com/vfcarmo/petz.git
$ cd petz
```

### Running the database
```sh
$ docker-compose up -d
```

### Running the application
```sh
$ ./gradlew bootRun
```

You can test the Petz API using [Postman] and importing the collection 
(Petz.postman_collection.json) located in the petz directory. 


### TODOs

 - Delivery in a Docker Orchestration environment

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [Java]: <https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html>
   [Spring Boot]: <https://spring.io/blog/2019/10/16/spring-boot-2-2-0>
   [PostgreSQL]: <https://www.postgresql.org/>
   [Lombok]: <https://projectlombok.org/>
   [Mapstruct]: <http://modelmapper.org/>
   [Docker-Compose]: <https://docs.docker.com/compose/>
   [Gradle]: <https://gradle.org/>
   [Postman]: <https://www.getpostman.com>
   [JUnit]: <https://junit.org/junit/>
   [Mockito]: <https://site.mockito.org/>
   [Cucumber]: <https://cucumber.io/>
