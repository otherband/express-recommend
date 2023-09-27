FROM openjdk:17-jdk-slim
WORKDIR app/
COPY target/express-recommend-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]