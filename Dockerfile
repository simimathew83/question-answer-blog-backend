# Use an official Maven + JDK image
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Use JDK to run the app
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/question-answer-blog-backend-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]