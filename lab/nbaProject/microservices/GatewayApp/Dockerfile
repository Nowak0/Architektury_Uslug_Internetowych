# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw ./
COPY microservices/GatewayApp/pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY microservices/GatewayApp/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables with defaults
ENV SERVER_PORT=8080
ENV FRANCHISE_APP_URL=http://franchise-app:8083
ENV PLAYER_APP_URL=http://player-app:8082

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
