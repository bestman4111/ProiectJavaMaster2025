# LibraryHub - Business Requirements & MVP

## 1. System Description
LibraryHub is a backend application developed in Java using Spring Boot.
It provides core functionalities for managing a library, including members, books, copies, loans, reservations and fines.

The application exposes a REST API documented using Swagger (OpenAPI) and persists data in an H2 database.

---

## 2. Business Requirements

1. The system must allow registering and managing library members.
2. The system must allow adding and managing books.
3. The system must allow associating books with authors and categories.
4. The system must allow adding multiple copies for a book.
5. The system must allow searching books bu title and category.
6. The system must allow loaning an available book copy to a member.
7. The system must limit the maximum number of active loans per member.
8. The system must allow returning a loan.
9. The system must calculate fines for overdue loans.
10. The system must allow creating reservations when no copies are available.


---

## 3. MVP - Minimum Viable Product

For the MVP phase, the following main features were implemented:

### Feature 1 - Member Management
- Create and list library members.
- View member details.

### Feature 2 - Book Catalog
- Create books with authors and categories.
- Manage book copies.

### Feature 3 - Search
- Search books by title.
- Filter search results by category.

### Feature 4 - Loans
- Create a loan for an available copy.
- Return a loan.
- View loan history for a member.

### Feature 5 - Reservations & Fines
- Create a reservation when no copies are available.
- Calculate fines for overdue loans.

---

## 4. MVP REST Endpoints

### Members
- `POST /api/members`
- `GET /api/members`
- `GET /api/members/{id}`

### Books
- `POST /api/books`
- `GET /api/books/{id}`
- `POST /api/books/{bookId}/copies`
- `GET /api/books/{bookId}/copies`

### Search
- `GET /api/books/search`

### Loans
- `POST /api/loans`
- `POST /api/loans/{loanId}/return`
- `GET /api/loans/{loanId}/fine`

### Reservations
- `POST /api/reservations`
