#!/bin/bash

echo "Stopping Gaz Podelki PostgreSQL database..."
docker-compose -f src/main/docker/docker-compose.yml down

echo "Database stopped"