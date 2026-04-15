# Earthquake Monitoring Project

A full-stack application for monitoring recent earthquake activity around the world using USGS data.

## Features
- **Fetch & Sync**: Sync latest earthquake data from the USGS API.
- **Filter**: Filter results by magnitude or date.
- **Delete**: Remove specific earthquake records from the local storage.
- **Responsive UI**: Built with React and Bootstrap for a modern feel.
- **Color Coded**: Magnitudes are color-coded (Gray to Red) based on intensity.

## Tech Stack
- **Frontend**: React, Vite, Axios, Bootstrap.
- **Backend**: Java, Spring Boot, Spring Data JPA, PostgreSQL.
- **Infrastructure**: Docker for database.

---

## Project Setup

### 1. Database Configuration
The project uses PostgreSQL. You can quickly set it up using the provided Docker Compose file in the backend directory.

1. Navigate to the backend directory:
   \`\`\`bash
   cd backend
   \`\`\`
2. Start the database container:
   \`\`\`bash
   docker-compose up -d
   \`\`\`
   - **Database Name**: \`earthquake_db\`
   - **Port**: \`5435\`
   - **User/Password**: \`demo\` / \`demo\`

### 2. Backend Setup
1. Ensure you have JDK 17+ and Maven installed.
2. From the \`backend\` directory, run:
   \`\`\`bash
   ./mvnw spring-boot:run
   \`\`\`
3. The API will be available at \`http://localhost:8080\`.

### 3. Frontend Setup
1. Ensure you have Node.js installed.
2. Navigate to the \`frontend\` directory:
   \`\`\`bash
   cd frontend
   \`\`\`
3. Install dependencies:
   \`\`\`bash
   npm install
   \`\`\`
4. Run the development server:
   \`\`\`bash
   npm run dev
   \`\`\`
5. Open your browser at \`http://localhost:3000\`.

---

## Assumptions Made
- The backend API is expected at \`http://localhost:8080/api\`.
- The frontend is configured to run on port \`3000\` to match the CORS policy in the backend.
- The USGS API data format is handled dynamically (supports both USGS Feature format and flattened database entities).

## Optional Improvements Implemented
- **Robust Error Handling**: Added UI safety checks for missing properties and API failures.
- **Enhanced UX**: Added a loading spinner to the Sync button for real-time feedback.
- **Visual Feedback**: Implemented a granular color gradient for earthquake magnitudes (Micro to Major).
- **Responsive Design**: Used Bootstrap 5 for a clean, mobile-friendly interface.
