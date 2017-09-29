FROM openjdk:8u131-jre-alpine

COPY target/typer-service-api-*-standalone.jar /typer-service-api.jar

EXPOSE 80

CMD java -jar /typer-service-api.jar