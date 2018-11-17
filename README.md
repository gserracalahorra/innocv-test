# innocv-test
Technical proof for InnoCV

1 - Startup containers

sudo docker-compose up

2 - Create 'crm' index

curl -X PUT -H 'Content-Type: application/json' -i http://localhost/crm --data '{
    "settings" : {
        "index" : {
            "number_of_shards" : 5,
            "number_of_replicas" : 1
        }
    }
}'

3 - Create 'user' type

curl -X PUT -H 'Content-Type: application/json' -i http://localhost/crm/_mapping/user --data '{
    "properties": {
        "name": {
            "type": "text"
        },
        "birthday": {
            "type": "text"
        }
    }
}'