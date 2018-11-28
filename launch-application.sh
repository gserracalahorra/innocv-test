sudo sysctl -w vm.max_map_count=262144

cd docker-compose

sudo docker-compose up > ../application.log