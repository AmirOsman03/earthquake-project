# Earthquake Monitoring Project

This project is a Spring Boot application that fetches earthquake data from the USGS API, stores it in a PostgreSQL database, and provides endpoints to query and manage the data.

## Project Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose (for the database)

### Setup
1. Clone the repository.
2. Navigate to the `backend` directory.

## How to Run Backend and Frontend

### Backend
1. Start the database (see "Database Configuration Steps" below).
2. Run the application using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend will be available at `http://localhost:8080`.

### Frontend
*(Assuming a frontend exists or is planned as part of the full project. If it's a separate repository or folder, follow the instructions provided there.)*
Typically, you would:
1. Navigate to the frontend directory.
2. Run `npm install`.
3. Run `npm start`.

## Database Configuration Steps
The project uses PostgreSQL 17.4 managed via Docker Compose.

1. Ensure Docker is running.
2. Run the following command to start the database container:
   ```bash
   docker-compose up -d
   ```
3. The database will be accessible at:
   - **Host:** `localhost`
   - **Port:** `5435`
   - **Database:** `earthquake_db`
   - **Username:** `demo`
   - **Password:** `demo`

## Any Assumptions Made
- **USGS API Availability:** The application assumes the USGS API (`https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson`) is publicly accessible and returns data in the expected format.
- **Persistence:** All data is stored in a relational database (PostgreSQL) for persistence across application restarts.
- **Deduplication:** Earthquakes are identified by their unique USGS ID (`externalId`) to avoid duplicate entries when fetching data multiple times.

## Any Optional Improvements Implemented
- **Docker Integration:** Included a `docker-compose.yml` for easy setup of the PostgreSQL database environment.
- **Filtering Capabilities:** Implemented endpoints for filtering earthquake data by minimum magnitude and by time (timestamp).
- **Data Protection:** Added logic to prevent storing duplicate earthquake events.
- **Lombok Integration:** Used Lombok to reduce boilerplate code for models and DTOs.
