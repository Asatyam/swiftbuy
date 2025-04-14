# Stage 1: Build

FROM --platform=linux/amd64 maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM --platform=linux/amd64 eclipse-temurin:21-jdk
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]