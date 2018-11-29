# innocv-test
Technical proof for InnoCV

* Startup application

chmod 755 launch-application.sh

./launch-application.sh



2 - Create 'crm' index











- Create user

curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/v1/crm/user --data '{
"name": "Juan Ramon Martinez",
"birthday": "1990-08-02"
}'

- Update user (replace {id} in the URI with the ID retrieved by the POST operation)

curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/v1/crm/user/{id} --data '{
"name": "José Ramon Martinez",
"birthday": "1990-08-02"
}'

- Get user (replace {id} in the URI with the ID retrieved by the POST operation)

curl -X GET -i http://localhost:8080/v1/crm/user/{id}

- Get all users

curl -X GET -i http://localhost:8080/v1/crm/user/all

-  Delete user

curl -X DELETE -i http://localhost:8080/v1/crm/user/{id}