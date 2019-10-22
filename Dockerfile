FROM openjdk:8-jdk-alpine

RUN mkdir -p /usr/local/backendfila

ADD ./target/fila-0.0.1-SNAPSHOT.jar /usr/local/backendfila/

EXPOSE 8080

CMD ["java", "-jar", "/usr/local/backendfila/fila-0.0.1-SNAPSHOT.jar"]
