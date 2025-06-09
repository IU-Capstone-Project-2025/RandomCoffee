FROM amazoncorretto:21.0.7-al2023-headless
WORKDIR /app
COPY /target/*.jar ./app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]
