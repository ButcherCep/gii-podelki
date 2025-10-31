#!/bin/bash

echo "Starting Gaz Podelki PostgreSQL database..."
docker-compose -f src/main/docker/docker-compose.yml up -d

echo "Waiting for database to be ready..."
sleep 10

echo "Database is running on localhost:5432"
echo "PGAdmin is available on http://localhost:8081"
echo "Credentials: admin@gaz-podelki.ru / admin123"