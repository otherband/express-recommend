FROM maven:3.8.5-openjdk-17-slim as builder

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src/ src/

RUN mvn package spring-boot:repackage


FROM openjdk:17-jdk-slim

WORKDIR app/

COPY --from=builder /build/target/express-recommend-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]