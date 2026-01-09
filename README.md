# ProiectJavaMaster2025 - LibraryHub

## Overview
LibraryHub is a Spring Boot REST API that provides core functionality for managing a library system.
The application follows a layered architecture and uses DTOs to separate API models from persistence models.

---

## Technologies Used
- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI (Swagger)
- JUnit 5 & Mockito

---

## Running the Application

### Run tests
```bash
mvn clean test
```

### Run application
```bash
mvn spring-boot:run
```

---

## API Documentation (Swagger)
After starting the application, the API documentation is available at:
- Swagger UI:  
  http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON:  
  http://localhost:8080/v3/api-docs

---

## Database (H2)
- H2 Console:  
  http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:libraryhub`
- Username: `sa`
- Password: *(empty)*

---

## Demo Using Postman
The application functionality can be demonstrated using Postman.

## Import API into Postman
1. Open Postman
2. Click **Import**
3. Paste OpenAPI JSON link: `http://localhost:8080/v3/api-docs`

---

## Demo Flow

1. Create Member  
   `POST /api/members`
2. Create Book  
   `POST /api/books`
3. Add Book Copy  
   `POST /api/books/{bookId}/copies`
4. Create Loan  
   `POST /api/loans`
5. Create Reservation (when no copies are available)  
   `POST /api/reservations`
6. Check Fine  
   `GET /api/loans/{loanId}/fine`
7. Return Loan  
   `POST /api/loans/{loanId}/return`

---

## Architecture Overview
- **Controller layer** - exposes REST endpoints
- **Service layer** - contains business logic
- **Repository layer** - handles database access
- **DTO layer** - used for data transfer between client and server
