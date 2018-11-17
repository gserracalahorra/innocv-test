# innocv-test
Technical proof for InnoCV

* Create index inserting a document

curl -X PUT -H 'Content-Type: application/json' -i http://localhost/crm/user/1 --data '{
    "name": "Guillem",
    "birthdate": "1990-08-02"
}'

* Get document

curl -XGET http://localhost/crm/user/1?pretty

* Update document

curl -X POST -H 'Content-Type: application/json' -i http://localhost/crm/user/1/_update --data '{
  "doc": { "name": "Jane Doe" }
}'

* Delete document

curl -X DELETE -i http://localhost/crm/user/1