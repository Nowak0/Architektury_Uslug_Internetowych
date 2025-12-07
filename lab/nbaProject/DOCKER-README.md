# NBA Project - Docker Setup Guide

This guide explains how to run the NBA Project microservices stack using Docker Compose.

## Architecture Overview

The stack consists of the following services:

```
┌─────────────────────┐
│  Angular Frontend   │  (Port 80)
│   (nginx)           │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Gateway App       │  (Port 8080)
│   (Spring Boot)     │
└──────┬──────────────┘
       │
       ├──────────────────┐
       │                  │
       ▼                  ▼
┌──────────────┐   ┌──────────────┐
│ Franchise App│   │  Player App  │
│ (Port 8083)  │   │ (Port 8082)  │
│              │◄──┤              │
└──────┬───────┘   └──────┬───────┘
       │                  │
       ▼                  ▼
┌──────────────┐   ┌──────────────┐
│Franchise DB  │   │  Player DB   │
│ (PostgreSQL) │   │ (PostgreSQL) │
└──────────────┘   └──────────────┘
```

### Service Dependencies

- **franchise-db**: PostgreSQL database for franchises
- **player-db**: PostgreSQL database for players
- **franchise-app**: Microservice managing NBA franchises (teams)
- **player-app**: Microservice managing NBA players (depends on franchise-app)
- **gateway-app**: API Gateway routing requests to microservices
- **angular-frontend**: Web UI for the application

## Quick Start

### First-Time Setup

1. **Start all services:**
   ```bash
   docker-compose up --build
   ```

2. **Wait for services to start** (this may take a few minutes)
   - Databases will be created
   - Schemas will be created automatically
   - Initial data will be loaded
   - Services will start in order based on dependencies

3. **Access the application:**
   - Frontend: http://localhost:80
   - Gateway API: http://localhost:8080
   - Franchise API: http://localhost:8083
   - Player API: http://localhost:8082

### Stopping the Stack

To stop the stack while **preserving data**:

```bash
docker-compose down
```

This stops and removes containers but keeps the database volumes intact.

### Restarting with Existing Data

To restart the stack with existing data:

```bash
docker-compose up
```

**What happens:**
- Database volumes are reused (data persists)
- Schemas are validated (not recreated)
- Data initialization is skipped (data already exists)
- All services start successfully

### Fresh Start (Reset Everything)

To completely reset and start fresh:

```bash
# Stop and remove everything including volumes
docker-compose down -v

# Start fresh
docker-compose up --build
```

**What happens:**
- All containers, networks, and volumes are deleted
- Databases are empty
- Behaves like first-time setup
- New data is initialized

## Environment Variables

### DDL_AUTO Strategy

The `DDL_AUTO` environment variable controls how Hibernate manages the database schema:

| Value | Description | Use Case |
|-------|-------------|----------|
| `create` | Creates schema, destroys previous data | **First run only** (default in docker-compose.yml) |
| `validate` | Only validates schema, doesn't change DB | **Production, restarts** (default in application.properties) |
| `update` | Tries to migrate schema | Risky, can cause issues (not recommended) |
| `create-drop` | Like create, but drops on shutdown | Testing only |

**Current Configuration:**
- In `docker-compose.yml`: `DDL_AUTO=create` for initial setup
- In `application.properties`: Default is `validate` for local development

### Changing DDL Strategy for Restarts

After the first successful run, you can change the DDL strategy to `validate` for safer restarts:

1. Edit `docker-compose.yml`
2. Change `DDL_AUTO: create` to `DDL_AUTO: validate` for both franchise-app and player-app
3. Restart: `docker-compose down && docker-compose up`

### SQL Logging

Control SQL logging with the `SQL_LOGGING` environment variable:

```yaml
environment:
  SQL_LOGGING: DEBUG  # Shows all SQL queries
  # or
  SQL_LOGGING: WARN   # Minimal logging (default)
```

## Data Initialization

### Idempotent Data Loading

Both microservices use idempotent data initialization:

- **FranchiseApp**: Checks if franchises exist before initialization
- **PlayerApp**: Checks if players exist before initialization

This prevents duplicate data errors on restart.

### Initial Data

**FranchiseApp** creates:
- Golden State Warriors
- Philadelphia 76ers

**PlayerApp** creates:
- Jimmy Butler (Forward)
- VJ Edgecombe (Center)
- Tyrese Maxey (Guard)

Players are randomly assigned to franchises.

## Troubleshooting

### Issue: Services fail to start on restart

**Symptoms:**
- Error messages about schema validation
- Duplicate key constraint violations

**Solution:**
1. Check if you're using the correct DDL strategy
2. For existing data, use `DDL_AUTO: validate`
3. If you want fresh data, run `docker-compose down -v` first

### Issue: "Data already exists, skipping initialization"

**This is normal!** It means:
- The database has existing data
- Data initialization was skipped (as intended)
- Your data persisted correctly

### Issue: PlayerApp cannot connect to FranchiseApp

**Symptoms:**
- PlayerApp fails during initialization
- Error message about FranchiseClient

**Solution:**
1. Ensure FranchiseApp is running: `docker-compose ps`
2. Check FranchiseApp logs: `docker-compose logs franchise-app`
3. PlayerApp has error handling and will retry on next restart

### Issue: Database connection errors

**Symptoms:**
- Services exit immediately
- "Connection refused" errors

**Solution:**
1. Wait for database health checks to pass
2. Check database logs: `docker-compose logs franchise-db player-db`
3. Ensure ports 5432 (inside containers) are not in use

### Issue: Port already in use

**Symptoms:**
- Error about port 8080, 8082, 8083, or 80 already in use

**Solution:**
1. Check what's using the port: `lsof -i :8080` (Mac/Linux) or `netstat -ano | findstr :8080` (Windows)
2. Stop the conflicting service
3. Or change the port mapping in `docker-compose.yml`:
   ```yaml
   ports:
     - "8081:8080"  # Maps host port 8081 to container port 8080
   ```

### Issue: Build fails

**Symptoms:**
- Maven build errors
- Docker build errors

**Solution:**
1. Ensure you have Docker and Docker Compose installed
2. Clear Docker build cache: `docker-compose build --no-cache`
3. Check Dockerfile in each microservice
4. Ensure Java 17+ is specified in Dockerfiles

## Viewing Logs

View logs for all services:
```bash
docker-compose logs -f
```

View logs for a specific service:
```bash
docker-compose logs -f franchise-app
docker-compose logs -f player-app
docker-compose logs -f franchise-db
```

## Accessing Databases Directly

### FranchiseDB
```bash
docker exec -it franchise-db psql -U franchiseuser -d franchisedb
```

### PlayerDB
```bash
docker exec -it player-db psql -U playeruser -d playerdb
```

Useful PostgreSQL commands:
- `\dt` - List tables
- `\d table_name` - Describe table
- `SELECT * FROM franchise;` - Query franchises
- `SELECT * FROM player;` - Query players
- `\q` - Quit

## Development Workflow

### Local Development (without Docker)

The applications can run locally with H2 in-memory database:

```bash
cd microservices/FranchiseApp
mvn spring-boot:run
```

The default configuration uses H2, so no external database is needed.

### Docker Development Workflow

1. Make code changes
2. Rebuild and restart:
   ```bash
   docker-compose up --build
   ```
3. Or rebuild specific service:
   ```bash
   docker-compose up --build franchise-app
   ```

## Best Practices

1. **First Run**: Use `DDL_AUTO=create` to create schema and initialize data
2. **Restarts**: Change to `DDL_AUTO=validate` after first successful run
3. **Production**: Always use `validate` to prevent accidental schema changes
4. **Testing**: Use `create-drop` for automated tests
5. **Data Preservation**: Use `docker-compose down` (without `-v`) to keep data
6. **Fresh Start**: Use `docker-compose down -v` to delete everything

## Volume Management

### List Volumes
```bash
docker volume ls
```

### Inspect Volume
```bash
docker volume inspect nbaproject_franchise-data
docker volume inspect nbaproject_player-data
```

### Delete Specific Volume
```bash
docker volume rm nbaproject_franchise-data
```

### Backup Volume (PostgreSQL)
```bash
# Backup
docker exec franchise-db pg_dump -U franchiseuser franchisedb > franchise_backup.sql

# Restore
docker exec -i franchise-db psql -U franchiseuser franchisedb < franchise_backup.sql
```

## Health Checks

Both database services have health checks configured:
- Check interval: 10 seconds
- Timeout: 5 seconds
- Retries: 5

Services wait for databases to be healthy before starting.

## Network Configuration

All services run on a custom bridge network: `nba-network`

Services can communicate using service names:
- `http://franchise-app:8083`
- `http://player-app:8082`
- `http://gateway-app:8080`

## Performance Tips

1. **Use BuildKit**: Export `DOCKER_BUILDKIT=1` for faster builds
2. **Layer Caching**: Don't change `pom.xml` frequently to leverage cache
3. **Multi-stage Builds**: Dockerfiles use multi-stage builds to minimize image size
4. **Resource Limits**: Add resource limits in docker-compose.yml if needed:
   ```yaml
   deploy:
     resources:
       limits:
         cpus: '0.5'
         memory: 512M
   ```

## Additional Resources

- [Spring Boot Docker Documentation](https://spring.io/guides/gs/spring-boot-docker/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [PostgreSQL Docker Hub](https://hub.docker.com/_/postgres)

## Support

For issues or questions:
1. Check this README
2. Review logs: `docker-compose logs`
3. Verify configuration in `docker-compose.yml`
4. Check individual `application.properties` files
