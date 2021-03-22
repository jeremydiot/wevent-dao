#!/bin/bash
docker run --name wevent-postgres -p 5432:5432 \
-e POSTGRES_PASSWORD=root \
-e POSTGRES_USER=root \
-e POSTGRES_DB=wevent \
-e POSTGRES_INITDB_ARGS="--auth=password --encoding=UTF8" \
-d postgres

# CREATE DATABASE wevent ENCODING 'UTF8';
