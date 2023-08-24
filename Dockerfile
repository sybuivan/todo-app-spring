#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/todo-app-0.0.1-SNAPSHOT.jar todo-app.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","demo.jar"]
