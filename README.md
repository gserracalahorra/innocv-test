# InnoCV Challenge

## Technologies used

* Ubuntu 18.04
* JDK 1.8
* Spring Boot 2.0.5.RELEASE
* Spring Web Flux
* Embbeded MongoDB
* JUnit, Mockito and RestAssured
* Lombock

## Architecture documentation

This application is a proof of concept of a Rest API using Spring Web Flux. It implements a Rest API with CRUD operations over the 'user' resource. An embedded MongoDB is used when the Spring Context loads.


## Installation steps

Go to base folder in the project:

    mvn install

    java -jar target/crm-user-api.jar

## Usage

1. Create user

        curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/v1/crm/user --data '{
        "name": "Guillem Serra Calahorra",
        "birthday": "1990-08-02"
        }'

2. Update user (replace {id} in the URI with the ID retrieved by the POST operation)

        curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/v1/crm/user/{id} --data '{
        "name": "Octavi Serra Calahorra",
        "birthday": "1989-04-18"
        }'

3. Get user (replace {id} in the URI with the ID retrieved by the POST operation)

        curl -X GET -i http://localhost:8080/v1/crm/user/{id}

4. Get all users

        curl -X GET -i http://localhost:8080/v1/crm/user/all

5. Delete user

        curl -X DELETE -i http://localhost:8080/v1/crm/user/{id}
