# NBA Manager - Angular Frontend

This is an Angular application for managing NBA franchises and players. It communicates with the backend gateway application running on localhost:8080.

## Features

- **Franchises Management**
  - View list of all franchises
  - Add new franchise
  - Edit existing franchise
  - Delete franchise
  - View franchise details with players

- **Players Management**
  - View players within a franchise
  - Add new player to a franchise
  - Edit existing player
  - Delete player
  - View player details

## Prerequisites

- Node.js (v18 or later)
- npm (v9 or later)
- Angular CLI (v17 or later)
- Backend Gateway running on localhost:8080

## Installation

```bash
cd lab/nbaProject/angular-frontend
npm install
```

## Development Server

Make sure the backend gateway is running on localhost:8080 before starting the frontend.

```bash
ng serve
```

Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

The proxy configuration will forward all `/api` requests to the gateway at `http://localhost:8080`.

## Build

```bash
ng build
```

The build artifacts will be stored in the `dist/` directory.

## Project Structure

```
src/
├── app/
│   ├── models/
│   │   ├── franchise.model.ts
│   │   └── player.model.ts
│   ├── services/
│   │   ├── franchise.service.ts
│   │   └── player.service.ts
│   ├── components/
│   │   ├── franchise-list/
│   │   ├── franchise-add/
│   │   ├── franchise-edit/
│   │   ├── franchise-details/
│   │   ├── player-add/
│   │   ├── player-edit/
│   │   └── player-details/
│   ├── app.routes.ts
│   ├── app.config.ts
│   └── app.component.ts
├── proxy.conf.json
└── ...
```

## Routes

| Route | Component | Description |
|-------|-----------|-------------|
| `/franchises` | FranchiseListComponent | List all franchises |
| `/franchises/add` | FranchiseAddComponent | Add new franchise |
| `/franchises/edit/:id` | FranchiseEditComponent | Edit franchise |
| `/franchises/:id` | FranchiseDetailsComponent | Franchise details with players |
| `/franchises/:franchiseId/players/add` | PlayerAddComponent | Add new player |
| `/franchises/:franchiseId/players/edit/:playerId` | PlayerEditComponent | Edit player |
| `/franchises/:franchiseId/players/:playerId` | PlayerDetailsComponent | Player details |

## API Endpoints

The application communicates with the following API endpoints through the gateway:

- `GET /api/franchises` - List all franchises
- `GET /api/franchises/{id}` - Get franchise details
- `POST /api/franchises` - Create franchise
- `PUT /api/franchises/{id}` - Update franchise
- `DELETE /api/franchises/{id}` - Delete franchise
- `GET /api/players` - List all players
- `GET /api/players/{id}` - Get player details
- `GET /api/players/franchise/{id}/players` - Get players by franchise
- `POST /api/players/{franchiseId}` - Add player to franchise
- `PUT /api/players/{id}` - Update player
- `DELETE /api/players/{id}` - Delete player

