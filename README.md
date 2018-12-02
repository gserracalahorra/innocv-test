# InnoCV Challenge

## Technologies used

* Ubuntu 18.04
* JDK 1.8
* Apache Maven 3.5.2
* Spring Boot 2.0.5.RELEASE
* Springfox Swagger 2.9.2
* Elasticsearch 6.5
* Nginx (always latest version)
* Docker Compose 1.23.1
* JUnit, Mockito, PowerMockito and RestAssured
* Lombock
* Travis CI

## Architecture documentation

This application implements a user REST api. The architecture has the following features:

* Docker Compose orchestration in order to startup at the same time all the services. All services are disposed along two virtual networks:

    * crm-cluster-network: to this network belong two Elasticsearch nodes and a Nginx reverse proxy. Reverse proxy is the only one with visibility to the ES nodes. Reverse proxy balances the load between the ES nodes. ES nodes does not expose their ports outside. The only one exposing ports outside is the Nginx reverse proxy in order to preserve security.

    * crm-network: to this network belong the container with the crm-user-api Java application and the container with the reverse proxy. This network is intended to isolate client containers from ES nodes, allowing visibility only through the reverse proxy.

* Use of Spring Boot in order to build the REST Api. Use of profiles. JVM argument injection.
* Exhaustive unit (with JUnit, Mockito and PowerMockito) and integration testing (with Rest Assured).
* Three layer architecture: controller, service and repository, speaking each of them their own language. Converters in order to translate messages between tiers.
* Rest API documentation with Swagger.
* Continuos Integration through Travis CI. To push to central repository fires the build of the maven project

## Installation steps

Prerrequisite: docker-compose installed.

Go to base folder in the project:

    mvn install

    cd docker-compose

    sudo docker-compose up

When it finishes, create the 'crm' index:

        curl -X PUT -H 'Content-Type: application/json' -i http://localhost/crm --data '{
        "settings" : { "index" : { "number_of_shards" : 5, "number_of_replicas" : 1 } }
        }'

Now, you have the system running on your local machine with an index called 'crm'.

## Usage

* CRUD operations:

    1. Create user

            curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/v1/crm/user --data '{
            "name": "Juan Ramon Martinez",
            "birthday": "1990-08-02"
            }'

    2. Update user (replace {id} in the URI with the ID retrieved by the POST operation)

            curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/v1/crm/user/{id} --data '{
            "name": "José Ramon Martinez",
            "birthday": "1990-08-02"
            }'

    3. Get user (replace {id} in the URI with the ID retrieved by the POST operation)

            curl -X GET -i http://localhost:8080/v1/crm/user/{id}

    4. Get all users

            curl -X GET -i http://localhost:8080/v1/crm/user/all

    5. Delete user

            curl -X DELETE -i http://localhost:8080/v1/crm/user/{id}

* Rest API doeumentation:

    1. Query the crm-user-api specifications...

            curl -X GET -i 'http://localhost:8080/v2/api-docs?group=crm-user-api'

    2. Or display the swagger-ui.html user interface:

            http://localhost:8080/swagger-ui.html