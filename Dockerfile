FROM openjdk:11-jre-slim
LABEL maintainer="imvnrtk@gmail.com"

ARG JAR_FILE=./target/transactions-reconciliation-service-1.0.0.jar
COPY ${JAR_FILE} transactions-reconciliation-service.jar
ENTRYPOINT ["java","-jar","/transactions-reconciliation-service.jar"]