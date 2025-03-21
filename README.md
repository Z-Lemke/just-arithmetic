# Adaptive Math Learning Application

This is a web application that offers a stream of math problems that adapt in difficulty based on user performance. The application starts with simple math problems and progressively increases in difficulty as users answer correctly.

## Features

- Adaptive difficulty based on user performance
- Problems from Grade 1 to Grade 8 levels
- Progress tracking and statistics
- Responsive design for all devices

## Tech Stack

- **Backend**: Spring Boot 3.x with Java 17
- **Frontend**: React.js 18.x
- **Database**: H2 in-memory database
- **Build Tools**: Maven (backend), npm (frontend)

## Project Structure

```
just-arithmetic/
├── backend/                  # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/        # Java source code
│   │   │   └── resources/   # Application properties
│   └── pom.xml              # Maven configuration
└── frontend/                # React.js frontend
    ├── public/              # Static files
    ├── src/                 # React source code
    │   ├── components/      # React components
    │   └── services/        # API services
    └── package.json         # npm configuration
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Node.js 14.x or higher
- npm 6.x or higher
- Maven 3.6.x or higher

### Running the Backend

1. Navigate to the backend directory:
   ```
   cd just-arithmetic/backend
   ```

2. Build and run the Spring Boot application:
   ```
   mvn spring-boot:run
   ```

3. The backend will start on http://localhost:8080

### Running the Frontend

1. Navigate to the frontend directory:
   ```
   cd just-arithmetic/frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Start the React development server:
   ```
   npm start
   ```

4. The frontend will start on http://localhost:3000

## How It Works

1. Users are presented with math problems appropriate for their current level
2. As they answer correctly, the difficulty increases
3. If they struggle, the difficulty decreases
4. Progress is tracked and displayed to the user

## License

This project is licensed under the MIT License - see the LICENSE file for details.
