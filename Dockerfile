FROM openjdk:8u131-jre-alpine

COPY target/typer-service-api-*-standalone.jar /typer-service-api.jar

EXPOSE 8080

USER 1001

CMD java -jar /typer-service-api.jar