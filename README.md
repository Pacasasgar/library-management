# Library Management API

This project is a RESTful API to manage a small library, allowing for the administration of members, books, and loans. It has been developed following clean architecture principles and modern development methodologies.

## Tech Stack

* **Language:** Java 21 (Eclipse Temurin)
* **Framework:** Spring Boot 3.x
* **Persistence:** Spring Data JPA / Hibernate
* **Database:** H2 (in-memory for development and tests)
* **Build Tool:** Maven
* **Testing:** JUnit 5, Mockito
* **Mapping:** MapStruct
* **Utilities:** Lombok

## How to Run the Project

1.  **Prerequisites:**
    * JDK 21 installed.
    * Maven installed.

2.  **Running the application:**
    * Clone the repository.
    * From the project root, run the following command in a terminal:
        ```bash
        mvn spring-boot:run
        ```
    * The API will be available at `http://localhost:8080`.

## API Endpoints

### Members (`/members`)
* `POST /members`: Creates a new member.
* `GET /members/{id}`: Finds a member by their ID.
* `PUT /members/{id}`: Updates a member's data.
* `DELETE /members/{id}`: Deletes a member.

### Books (`/books`)
* `POST /books`: Registers a new book.
* `GET /books/{id}`: Finds a book by its ID.

### Loans (`/loans`)
* `POST /loans`: Creates a new loan.
* `PUT /loans/{id}/return`: Marks a loan as returned.

## Key Concepts Demonstrated

This project serves as a practical demonstration of the following software architecture and development concepts:

* **Hexagonal Architecture (Ports & Adapters):** The core business logic (domain and application layers) is completely isolated from external technology (web framework, database).
* **Test-Driven Development (TDD):** Each feature has been developed starting with a failing test (Red), writing the code to make it pass (Green), and refactoring to improve quality.
* **Comprehensive Testing Strategy:**
    * **Unit Tests:** To test the business logic of the `UseCases` and the mapping logic of the `Adapters` in isolation (using Mockito).
    * **Integration Tests:** To test the full API flows, from the HTTP request to the database (using Spring Boot Test and MockMvc).
* **Domain-Driven Design (DDD) Principles:** Clear separation between `Entities`, `Use Cases`, and `Repositories` (through Ports).
* **Clean RESTful API Design:** Endpoint design following REST conventions, use of DTOs for requests and responses, and error handling with semantic HTTP status codes.
* **Refactoring and Clean Code:** Use of tools like MapStruct to eliminate boilerplate code and maintain high code quality.