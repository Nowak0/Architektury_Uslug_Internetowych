# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw ./
COPY microservices/PlayerApp/pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY microservices/PlayerApp/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables with defaults
ENV SERVER_PORT=8082
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:nbaProject
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect

# Expose port
EXPOSE 8082

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8082/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
