# Stage 1: Build application with Maven
FROM eclipse-temurin:25-jdk-alpine AS build

# Install Maven
RUN apk add --no-cache maven

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Run application
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8083
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
