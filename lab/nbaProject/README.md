# NBA Project - Docker Deployment Guide

This project provides a complete Docker containerization setup for the NBA microservices application.

## Architecture

The application consists of the following services:

- **Angular Frontend** - Angular 17 application served by NGINX
- **Gateway App** - Spring Cloud Gateway for routing API requests
- **Franchise App** - Spring Boot microservice managing franchise data (Categories)
- **Player App** - Spring Boot microservice managing player data (Elements)
- **PostgreSQL Databases** - Separate databases for Franchise and Player services

## Prerequisites

- Docker Engine 20.10 or higher
- Docker Compose 2.0 or higher

## Quick Start

1. Navigate to the project directory:
```bash
cd lab/nbaProject
```

2. Build and start all services:
```bash
docker-compose up --build
```

3. Access the application:
   - Frontend: http://localhost
   - Gateway API: http://localhost:8080
   - Franchise API: http://localhost:8083
   - Player API: http://localhost:8082

## Services Configuration

### Angular Frontend (Port 80)
- Built using Node.js 18
- Served by NGINX
- Proxies `/api` requests to the Gateway
- Environment variables:
  - `GATEWAY_URL`: Gateway application URL (default: http://gateway-app:8080)

### Gateway App (Port 8080)
- Spring Cloud Gateway
- Routes requests to appropriate microservices
- Environment variables:
  - `SERVER_PORT`: Server port (default: 8080)
  - `FRANCHISE_APP_URL`: Franchise service URL (default: http://franchise-app:8083)
  - `PLAYER_APP_URL`: Player service URL (default: http://player-app:8082)

### Franchise App (Port 8083)
- Spring Boot microservice with JPA
- PostgreSQL database backend
- Environment variables:
  - `SERVER_PORT`: Server port (default: 8083)
  - `DATABASE_URL`: PostgreSQL connection URL
  - `DATABASE_USERNAME`: Database username
  - `DATABASE_PASSWORD`: Database password
  - `DATABASE_DRIVER`: Database driver class
  - `JPA_DIALECT`: Hibernate dialect

### Player App (Port 8082)
- Spring Boot microservice with JPA
- PostgreSQL database backend
- Communicates with Franchise App for data synchronization
- Environment variables:
  - `SERVER_PORT`: Server port (default: 8082)
  - `DATABASE_URL`: PostgreSQL connection URL
  - `DATABASE_USERNAME`: Database username
  - `DATABASE_PASSWORD`: Database password
  - `DATABASE_DRIVER`: Database driver class
  - `JPA_DIALECT`: Hibernate dialect
  - `FRANCHISE_APP_URL`: Franchise service URL for data sync

### PostgreSQL Databases
- **franchise-db**: Database for Franchise App
  - Database: franchisedb
  - User: franchiseuser
  - Password: franchisepass
  
- **player-db**: Database for Player App
  - Database: playerdb
  - User: playeruser
  - Password: playerpass

## Docker Commands

### Start Services
```bash
# Start all services
docker-compose up

# Start in detached mode
docker-compose up -d

# Build and start
docker-compose up --build
```

### Stop Services
```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: deletes all data)
docker-compose down -v
```

### View Logs
```bash
# View all logs
docker-compose logs

# View logs for specific service
docker-compose logs franchise-app
docker-compose logs player-app
docker-compose logs gateway-app
docker-compose logs angular-frontend

# Follow logs
docker-compose logs -f
```

### Rebuild Specific Service
```bash
# Rebuild and restart a specific service
docker-compose up --build --no-deps -d franchise-app
```

## Development

### Local Development
For local development without Docker, use H2 in-memory databases by running Spring Boot applications directly:

```bash
# Franchise App
cd microservices/FranchiseApp
mvn spring-boot:run

# Player App
cd microservices/PlayerApp
mvn spring-boot:run

# Gateway App
cd microservices/GatewayApp
mvn spring-boot:run

# Angular Frontend
cd angular-frontend
npm install
npm start
```

### Environment Variables Override
You can override environment variables by creating a `.env` file in the same directory as `docker-compose.yml`:

```env
# Example .env file
FRANCHISE_DB_PASSWORD=mysecurepassword
PLAYER_DB_PASSWORD=anothersecurepassword
```

## Data Persistence

Database data is persisted in Docker volumes:
- `franchise-data`: Franchise database data
- `player-data`: Player database data

To backup data:
```bash
# Backup franchise database
docker-compose exec franchise-db pg_dump -U franchiseuser franchisedb > franchise_backup.sql

# Backup player database
docker-compose exec player-db pg_dump -U playeruser playerdb > player_backup.sql
```

To restore data:
```bash
# Restore franchise database
docker-compose exec -T franchise-db psql -U franchiseuser franchisedb < franchise_backup.sql

# Restore player database
docker-compose exec -T player-db psql -U playeruser playerdb < player_backup.sql
```

## Troubleshooting

### Services not starting
Check logs for specific service:
```bash
docker-compose logs <service-name>
```

### Database connection issues
Ensure databases are healthy before services start:
```bash
docker-compose ps
```

### Port conflicts
If ports are already in use, modify the port mappings in `docker-compose.yml`:
```yaml
ports:
  - "8080:8080"  # Change first port (host) to available port
```

### Clear all data and restart
```bash
docker-compose down -v
docker-compose up --build
```

## Architecture Diagram

```
┌─────────────────┐
│  Browser        │
└────────┬────────┘
         │ :80
         ▼
┌─────────────────┐
│ Angular         │
│ Frontend        │◄───── NGINX serves static files
│ (NGINX)         │       and proxies /api to Gateway
└────────┬────────┘
         │ /api
         ▼ :8080
┌─────────────────┐
│ Gateway App     │◄───── Routes requests to microservices
│ (Spring Cloud)  │
└────┬────────┬───┘
     │        │
     │        └──────────────┐
     │ :8083                 │ :8082
     ▼                       ▼
┌──────────────┐      ┌──────────────┐
│ Franchise    │      │ Player       │
│ App          │      │ App          │
│ (Spring)     │      │ (Spring)     │
└──────┬───────┘      └──────┬───────┘
       │                     │
       │ :5432               │ :5432
       ▼                     ▼
┌──────────────┐      ┌──────────────┐
│ PostgreSQL   │      │ PostgreSQL   │
│ franchise-db │      │ player-db    │
└──────────────┘      └──────────────┘
```

## License

This is a demo project for educational purposes.
