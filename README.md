# 🌍 Earthquake Monitoring & Management System

A full-stack application for monitoring, fetching, and managing earthquake data from the USGS (United States Geological Survey). The system includes a Spring Boot backend, a PostgreSQL database, and a React-based frontend dashboard.

## 🚀 Key Features

### Backend
- **Automated Data Ingestion**: Fetches the latest earthquake data from the USGS API.
- **Data Persistence**: Uses PostgreSQL for reliable storage of earthquake events.
- **Smart Deduplication**: Prevents duplicate entries using unique USGS identifiers.
- **Filtering System**: Search earthquakes by minimum magnitude or timestamp.
- **Comprehensive API**: Full CRUD capabilities for managing earthquake data.
- **Containerized Database**: Ready-to-use Docker configuration for the database layer.

### Frontend
- **Interactive Dashboard**: Real-time view of synced earthquake records.
- **Granular Filtering**: Easy-to-use magnitude range and date filters.
- **Sync Control**: Manual sync trigger with real-time loading feedback.
- **Intelligent Formatting**: Handles both raw USGS feature formats and flattened database entities.
- **Visual Indicators**: Color-coded badges (Gray to Red) representing seismic intensity levels.

---

## 🐳 Docker Setup (One-Click Launch)
The entire stack (Frontend, Backend, and Database) can be launched using Docker Compose from the project root.

1. **Start the whole application**:
   ```bash
   docker-compose up -d --build
   ```
2. **Access the services**:
   - **Frontend**: `http://localhost:3000`
   - **Backend API**: `http://localhost:8080`
   - **Database**: `localhost:5435`

---

## 🛠️ Project Setup & Installation

### Prerequisites
- **Java 17+**
- **Node.js 22+**
- **Maven 3.6+**
- **Docker & Docker Compose**

### 1. Database Configuration
The system uses **PostgreSQL 17.4**.
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Start the database environment:
   ```bash
   docker-compose up -d
   ```
   - **Host/Port**: `localhost:5435`
   - **DB Name**: `earthquake_db`
   - **Credentials**: `demo` / `demo`

### 2. Backend Execution
From the `backend` directory:
```bash
./mvnw spring-boot:run
```
The server will start on `http://localhost:8080`.

### 3. Frontend Execution
From the `frontend` directory:
```bash
npm install
npm run dev
```
The dashboard will be available at `http://localhost:3000`.

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/earthquakes/fetch` | Sync latest data from USGS to local database |
| `GET`  | `/api/earthquakes` | Retrieve all stored earthquake records |
| `GET`  | `/api/earthquakes/filter/magnitude?minMagnitude={val}` | Filter records by minimum magnitude |
| `GET`  | `/api/earthquakes/filter/time?timestamp={val}` | Filter by events occurring after timestamp |
| `DELETE` | `/api/earthquakes/{id}` | Permanently delete a specific earthquake record |

---

## 📝 Assumptions & Design Choices
- **Data Integrity**: USGS API is treated as the single source of truth; unique `id` fields are used for deduplication.
- **Inversion of Control**: The repository pattern was implemented in the frontend to decouple API logic from UI components.
- **CORS Policy**: Configured to specifically allow the frontend origin (`http://localhost:3000`) for secure cross-origin requests.
- **Robustness**: The UI implements safety checks for `undefined` properties and handles varied API response structures gracefully.

## ✨ Optional Improvements Implemented
- **Full-Stack Dockerization Support**: Database is containerized for "one-click" environment setup.
- **Granular Visual Feedback**: Implemented a 6-tier color gradient for magnitudes (Micro to Major).
- **Custom React Hooks**: Encapsulated state management and API orchestration into reusable `useEarthquakes` hook.
- **Comprehensive Testing**: Backend includes 13+ unit tests using Mockito and JUnit 5 for controller and service layers.
- **Lombok & Clean Code**: Utilized modern Java features and Lombok to reduce boilerplate.

---

## 📂 Project Structure
```text
earthquake-project/
├── docker-compose.yml   # Multi-container orchestration
├── backend/             # Spring Boot Application
│   ├── src/main/java/com/codeit/assignment/
│   │   ├── config/      # Application & Web security settings
│   │   ├── web/         # REST API Controllers
│   │   ├── service/     # Business logic & USGS API client
│   │   ├── model/       # JPA Entities (PostgreSQL)
│   │   ├── dto/         # Data Transfer Objects
│   │   └── repository/  # Data Access layer (Spring Data JPA)
│   ├── src/test/        # Mockito/JUnit 5 test suite
│   ├── Dockerfile
│   └── docker-compose.yml # Database-only container
└── frontend/            # React + Vite Application
    ├── src/
    │   ├── axios/       # API client configuration
    │   ├── hooks/       # Custom React hooks (useEarthquakes)
    │   ├── repository/  # API orchestration layer
    │   ├── App.jsx      # Main dashboard & UI components
    │   └── main.jsx     # Frontend entry point
    ├── vite.config.js
    └── Dockerfile
```
