# NBA Project - Docker Configuration

This document provides instructions for running the NBA Project using Docker and Docker Compose.

## Architecture

The application consists of the following services:

- **Frontend** (Angular) - Port 4200
- **Gateway** (Spring Cloud Gateway) - Port 8080
- **FranchiseApp** (Spring Boot) - Port 8083
- **PlayerApp** (Spring Boot) - Port 8082
- **franchise-db** (PostgreSQL 16) - Internal only
- **player-db** (PostgreSQL 16) - Internal only

## Prerequisites

- Docker Engine 20.10 or higher
- Docker Compose 2.0 or higher
- At least 4GB of available RAM
- Ports 4200, 8080, 8082, and 8083 must be available

## Quick Start

1. Navigate to the project directory:
```bash
cd lab/nbaProject
```

2. Build and start all services:
```bash
docker-compose up --build
```

3. Wait for all services to be healthy (this may take 2-3 minutes)

4. Access the application:
   - **Frontend UI**: http://localhost:4200
   - **Gateway API**: http://localhost:8080/api
   - **FranchiseApp API**: http://localhost:8083/api/franchises
   - **PlayerApp API**: http://localhost:8082/api/players

## Service Startup Order

The services start in the following order (enforced by health checks):

1. **Database services** (franchise-db, player-db)
2. **Microservices** (franchise-app, player-app) - wait for databases
3. **Gateway** - waits for both microservices
4. **Frontend** - waits for gateway

This ensures proper initialization and prevents "cannot create franchise" issues.

## Commands

### Start services in detached mode:
```bash
docker-compose up -d
```

### View logs:
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f frontend
docker-compose logs -f gateway
docker-compose logs -f franchise-app
docker-compose logs -f player-app
```

### Stop services:
```bash
docker-compose down
```

### Stop services and remove volumes (delete all data):
```bash
docker-compose down -v
```

### Rebuild a specific service:
```bash
docker-compose up --build franchise-app
```

### Check service health:
```bash
docker-compose ps
```

## Health Check Endpoints

Each service exposes a health check endpoint:

- FranchiseApp: http://localhost:8083/actuator/health
- PlayerApp: http://localhost:8082/actuator/health
- Gateway: http://localhost:8080/actuator/health

## Database Configuration

### FranchiseApp Database
- **Host**: franchise-db (internal)
- **Database**: franchisedb
- **User**: franchise_user
- **Password**: franchise_pass
- **Port**: 5432 (internal only)

### PlayerApp Database
- **Host**: player-db (internal)
- **Database**: playerdb
- **User**: player_user
- **Password**: player_pass
- **Port**: 5432 (internal only)

Data is persisted in Docker volumes:
- `franchise-data`
- `player-data`

## Environment Variables

### FranchiseApp
- `SERVER_PORT`: 8083
- `SPRING_DATASOURCE_URL`: jdbc:postgresql://franchise-db:5432/franchisedb
- `SPRING_DATASOURCE_USERNAME`: franchise_user
- `SPRING_DATASOURCE_PASSWORD`: franchise_pass
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`: org.postgresql.Driver
- `SPRING_JPA_DATABASE_PLATFORM`: org.hibernate.dialect.PostgreSQLDialect

### PlayerApp
- `SERVER_PORT`: 8082
- `SPRING_DATASOURCE_URL`: jdbc:postgresql://player-db:5432/playerdb
- `SPRING_DATASOURCE_USERNAME`: player_user
- `SPRING_DATASOURCE_PASSWORD`: player_pass
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`: org.postgresql.Driver
- `SPRING_JPA_DATABASE_PLATFORM`: org.hibernate.dialect.PostgreSQLDialect

### Gateway
- `SERVER_PORT`: 8080
- `FRANCHISE_APP_URL`: http://franchise-app:8083
- `PLAYER_APP_URL`: http://player-app:8082

### Frontend
- `GATEWAY_URL`: http://gateway:8080

## Troubleshooting

### Service won't start
Check the logs for the specific service:
```bash
docker-compose logs <service-name>
```

### Port already in use
Make sure ports 4200, 8080, 8082, and 8083 are not being used by other applications.

### Database connection errors
Ensure the database containers are healthy before the application containers start:
```bash
docker-compose ps
```

All services should show "healthy" status.

### CORS errors
CORS is configured in each microservice and in the NGINX frontend. If you encounter CORS issues:
1. Check that you're accessing via http://localhost:4200
2. Verify the Gateway URL is correctly set in the frontend
3. Check browser console for specific CORS errors

### Changes not reflected
If you make code changes, rebuild the affected service:
```bash
docker-compose up --build <service-name>
```

### Reset everything
To completely reset the application (removes all data):
```bash
docker-compose down -v
docker-compose up --build
```

## Network Configuration

All services run on a bridge network called `nba-network`. Services communicate using container names as hostnames:

- `franchise-app` can access `franchise-db:5432`
- `player-app` can access `player-db:5432`
- `gateway` can access `franchise-app:8083` and `player-app:8082`
- `frontend` proxies API requests to `gateway:8080`

## Development vs Production

This configuration is optimized for development. For production:

1. Use secrets management for database passwords
2. Configure proper resource limits
3. Use production-grade PostgreSQL configuration
4. Enable HTTPS/TLS
5. Implement proper logging and monitoring
6. Review and restrict CORS policies

## Success Criteria

✅ All containers start in the correct order  
✅ Health checks pass for all services  
✅ Frontend communicates with Gateway successfully  
✅ Gateway routes requests to FranchiseApp and PlayerApp  
✅ CRUD operations work immediately (no refresh needed)  
✅ Data persists in PostgreSQL databases  
✅ Each microservice has independent database  
✅ All environment variables are properly configured  

## Support

For issues or questions, check the logs and ensure all prerequisites are met.
