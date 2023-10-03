# Sample jwt application with Spring Boot

This project includes authentication API's (login, register, logout, refresh, forgot-password, reset-password). It uses a PostgreSQL connection with Flyway migration.
Also there is sample dockerizing example.

## Installation

### With docker
- First [install docker](https://docs.docker.com/engine/install/)
- (Optional) Change Postgresql auth properties on docker-compose.yml and application.properties.docker
- Run docker-compose to build docker containers
```bash
docker-compose up -d --build
```
or
```bash
docker compose up -d --build
```
- It takes a few minutes application to get ready. If your containers up check logs to see backend is ready
```bash
docker logs backend -f 
```

### Without docker
- First you need Postgresql database
- Add your postgresql auth properties to src/main/recources/application.yml
- Use your IDE to run application or use maven build and run

```bash
mvn install
java -jar target/spring-jwt-app.jar
```

### If it is ready
- Test your API's on [http://127.0.0.1:8080](http://127.0.0.1:8080)
