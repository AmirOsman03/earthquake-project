# Earthquake Monitoring & Management System

A robust Spring Boot application designed to monitor, fetch, and manage earthquake data from the USGS (United States Geological Survey). The system stores earthquake events in a PostgreSQL database and provides a set of RESTful APIs for data management and filtering.

## 🚀 Features

- **Automated Data Ingestion**: Fetches the latest earthquake data from the USGS API.
- **Data Persistence**: Uses PostgreSQL for reliable storage of earthquake events.
- **Smart Deduplication**: Prevents duplicate entries using unique USGS identifiers.
- **Filtering System**: Search earthquakes by minimum magnitude or timestamp.
- **Comprehensive API**: Full CRUD capabilities for managing earthquake data.
- **Containerized Database**: Ready-to-use Docker configuration for the database layer.
- **Unit Tested**: High test coverage across controllers, services, and API clients.

## 🛠️ Project Setup

### Prerequisites
- **Java 17** (or higher)
- **Maven 3.6+**
- **Docker & Docker Compose**

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd earthquake-project/backend
   ```
2. Start the database environment:
   ```bash
   docker-compose up -d
   ```

## 🏃 Running the Application

### Backend
Start the Spring Boot application using the Maven wrapper:
```bash
./mvnw spring-boot:run
```
The server will start on `http://localhost:8080`.

### Running Tests
To execute the unit test suite:
```bash
./mvnw test
```

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/earthquakes/fetch` | Fetch latest data from USGS and store new events |
| `GET`  | `/api/earthquakes` | Retrieve all stored earthquakes |
| `GET`  | `/api/earthquakes/filter/magnitude?minMagnitude={val}` | Filter by minimum magnitude |
| `GET`  | `/api/earthquakes/filter/time?timestamp={val}` | Filter events occurring after timestamp |
| `DELETE` | `/api/earthquakes/{id}` | Delete a specific earthquake record |

## 🗄️ Database Configuration
The system uses **PostgreSQL 17.4**.
- **Host**: `localhost`
- **Port**: `5435`
- **DB Name**: `earthquake_db`
- **Username/Password**: `demo` / `demo`

## 📝 Assumptions & Design Choices
- **Data Integrity**: We assume the USGS API is the single source of truth and use their `id` field for deduplication.
- **Minimum Threshold**: During ingestion, earthquakes with magnitude ≤ 2.0 are filtered out to focus on significant events.
- **Persistence**: PostgreSQL was chosen for its strong relational capabilities and reliability.

## ✨ Optional Improvements Implemented
- **Dockerized Environment**: Simplified database setup via Docker Compose.
- **Lombok**: Reduced boilerplate code for cleaner models and DTOs.
- **Service Layer Abstraction**: Used interfaces for the service layer to ensure better testability and maintainability.
- **Validation Logic**: Added error handling for missing records during deletion.
- **Comprehensive Testing**: Implemented 13+ unit tests using Mockito and JUnit 5.
