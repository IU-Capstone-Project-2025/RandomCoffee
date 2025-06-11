# Build stage
FROM amazoncorretto:21-alpine-jdk AS builder
WORKDIR /build

# Copy only the necessary files for building
COPY pom.xml ./
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw ./

# Build the application
RUN chmod +x ./mvnw
RUN ./mvnw clean install

# Final stage
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /build/target/*.jar ./app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]
