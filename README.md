# Library Management API

This project is a RESTful API to manage a small library, allowing for the administration of members, books, and loans. It has been developed following clean architecture principles and modern development methodologies, including event-driven patterns.

## Tech Stack

* **Language:** Java 21 (Eclipse Temurin)
* **Framework:** Spring Boot 3.x
* **Messaging:** **Spring Kafka**
* **Persistence:** Spring Data JPA / Hibernate
* **Database:** **PostgreSQL** (managed via Docker)
* **Build Tool:** Maven
* **Testing:** JUnit 5, Mockito, **Spring Kafka Test**
* **Mapping:** MapStruct
* **Containerization:** **Docker**, **Docker Compose**
* **Utilities:** Lombok

## How to Run the Project

1.  **Prerequisites:**
    * JDK 21 installed.
    * Maven installed.
    * **Docker** and **Docker Compose** installed and running.

2.  **Start Infrastructure:**
    * Open a terminal in the project root.
    * Run `docker-compose up -d` to start PostgreSQL, Zookeeper, and Kafka in the background.

3.  **Run the Application:**
    * In the same terminal, run:
        ```bash
        mvn spring-boot:run
        ```
    * The API will be available at `http://localhost:8080`.

4.  **Stopping Infrastructure:**
    * When finished, run `docker-compose down` to stop and remove the containers.

## API Endpoints

### Members (`/members`)
* `POST /members`: Creates a new member.
* `GET /members/{id}`: Finds a member by their ID.
* `PUT /members/{id}`: Updates a member's data.
* `DELETE /members/{id}`: Deletes a member.

### Books (`/books`)
* `POST /books`: Registers a new book.
* `GET /books/{id}`: Finds a book by its ID.
* `PUT /books/{id}`: Updates a book's data.
* `DELETE /books/{id}`: Deletes a book.

### Loans (`/loans`)
* `POST /loans`: Creates a new loan.
* `PUT /loans/{id}/return`: Marks a loan as returned. (Publishes a `BookReturnedLateEvent` to Kafka if the return is late).

## Kafka Topics

* `library.loans.late_returns`: Topic where `BookReturnedLateEvent` messages are published when a book is returned past its due date.

## Key Concepts Demonstrated

This project serves as a practical demonstration of the following software architecture and development concepts:

* **Hexagonal Architecture (Ports & Adapters):** The core business logic (domain and application layers) is completely isolated from external technology (web framework, database, **message broker**).
* **Test-Driven Development (TDD):** Each feature has been developed starting with a failing test (Red), writing the code to make it pass (Green), and refactoring to improve quality.
* **Comprehensive Testing Strategy:**
    * **Unit Tests:** To test the business logic of the `UseCases` and the logic of `Adapters` in isolation (using Mockito). Includes testing Kafka interactions with **`@EmbeddedKafka`**.
    * **Integration Tests:** To test the full API flows, from the HTTP request to the database (using Spring Boot Test, MockMvc, and **`@Transactional`** with a real database).
* **Domain-Driven Design (DDD) Principles:** Clear separation between `Entities`, `Use Cases`, `Repositories` (through Ports), and **Domain Events**.
* **Clean RESTful API Design:** Endpoint design following REST conventions, use of DTOs, and semantic HTTP status codes for responses and errors.
* **Refactoring and Clean Code:** Use of tools like MapStruct to eliminate boilerplate mapping code.
* **Containerization:** Using **Docker** and `docker-compose` to manage the development environment (PostgreSQL, Kafka, Zookeeper).
* **Event-Driven Architecture:** Using **Apache Kafka** for asynchronous communication between components (e.g., publishing a `BookReturnedLateEvent`).