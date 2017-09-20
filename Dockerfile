FROM openjdk:8u131-jre-alpine

COPY target/typer-service-api-*-standalone.jar /typer-service-api.jar

CMD java -jar /typer-service-api.jar