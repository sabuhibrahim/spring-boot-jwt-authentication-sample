FROM maven:3.8.5-openjdk-17

RUN mkdir /code

COPY . /code

WORKDIR /code

RUN cp application.docker.yml src/main/resources/application.yml

RUN chmod 755 /code/start.sh

ENTRYPOINT [ "sh", "/code/start.sh"]
