docker run --name wevent-postgres -p 5432:5432 \
-e POSTGRES_PASSWORD=root \
-e POSTGRES_USER=root \
-e POSTGRES_DB=wevent \
-e POSTGRES_INITDB_ARGS=--auth=password \
-d postgres
