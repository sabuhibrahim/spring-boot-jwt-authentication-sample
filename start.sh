#!/bin/sh
cd /code
mvn install
java -jar target/spring-jwt-app.jar