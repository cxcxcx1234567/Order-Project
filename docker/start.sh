#!/bin/bash

docker-compose up -d order-mysql
until docker-compose exec order-mysql mysqladmin ping -h "localhost" --silent; do
    echo "Waiting for MySQL"
    sleep 2
done

docker-compose up -d orders-project

echo "Running"