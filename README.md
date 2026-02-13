# Online Payment System (Backend)

A secure and scalable backend application built using Java and Spring Boot that handles user authentication, wallet transactions, and payment processing using RESTful APIs.

## Tech Stack
- Java
- Spring Boot
- Spring Security (JWT Authentication)
- Hibernate (JPA)
- MySQL
- Maven
- 
## Features

- User Registration & Login
- JWT-based Authentication & Authorization
- Secure Payment Transactions
- Transaction History with Database Mapping
- Layered Architecture (Controller, Service, Repository)
- Input Validation

## Architecture

The project follows a clean layered architecture:

Controller → Service → Repository → Database

This structure ensures separation of concerns, maintainability, and scalability.

##  Database Design

- User Entity
- Transaction Entity
- Proper JPA Relationships
- Secure password storage
- Relational mapping using Hibernate

---

##  How to Run the Project

1. Clone the repository:
   https://github.com/goswamirahu/secure-payment-api.git

2. Configure MySQL database in `application.properties`.

3. Run the application:
   mvn spring-boot:run

4. Access APIs using Postman.

---

##  Future Improvements

- Redis caching for performance optimization
- Fraud detection integration
- Docker containerization
- API rate limiting

---

##  Author

Rahul Giri
