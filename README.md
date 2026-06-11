# Online Payment System

A full-stack payment management application built using Java, Spring Boot, Hibernate, MySQL, Thymeleaf, Docker, and Maven. The system allows users to register, log in, perform payment transactions, and view transaction history through a web-based interface.

---

## Project Overview

The Online Payment System is designed to simulate real-world digital payment workflows. It provides secure user authentication, transaction management, session handling, and database integration using modern Java backend technologies.

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate

### Frontend

* Thymeleaf
* HTML

### Database

* MySQL

### Build Tool

* Maven

### DevOps & Version Control

* Docker
* Git
* GitHub

---

## Features

### User Management

* User Registration
* User Login
* Session-based Authentication
* Logout Functionality

### Payment Management

* Create Payment Transactions
* Credit Transactions
* Debit Transactions
* Transaction Description Support

### Transaction History

* View Complete Transaction History
* Account Balance Calculation
* Total Credit Calculation
* Total Debit Calculation

### Database Integration

* User Entity Mapping
* Transaction Entity Mapping
* One-to-Many Relationship
* Hibernate ORM Integration

### Deployment

* Dockerized Application
* Container-based Execution

---

## Project Architecture

The project follows a layered architecture:

Controller Layer
↓
Service Layer
↓
Repository Layer
↓
Database Layer

This architecture improves maintainability, scalability, and separation of concerns.

---

## Database Design

### User Entity

* id
* name
* email
* password

### Transaction Entity

* id
* amount
* type
* description
* timestamp
* user

### Relationship

One User
↓
Many Transactions

Implemented using Hibernate JPA Mapping.

---

## Project Structure

src
├── controller
├── service
├── repository
├── entity
├── config
└── templates

---

## Docker Support

Build Docker Image:

mvn clean package

docker build -t online-payment-app .

Run Docker Container:

docker run -p 8082:8081 online-payment-app

Access Application:

http://localhost:8082

---

## How to Run Locally

### Clone Repository

git clone https://github.com/goswamirahu/secure-payment-api.git

### Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/onlinepaymentsystem
spring.datasource.username=root
spring.datasource.password=your_pasword

### Run Application

mvn spring-boot:run

### Access Application

http://localhost:8081

---

## Key Concepts Implemented

* Spring Boot MVC
* Hibernate ORM
* JPA Relationships
* Session Management
* Thymeleaf View Rendering
* Form Handling
* Dependency Injection
* Repository Pattern
* Docker Containerization
* Git Version Control

---

## Future Enhancements

* JWT Authentication
* Spring Security Role-Based Access
* REST API Version
* Payment Gateway Integration
* Email Notifications
* Swagger Documentation
* Railway Deployment
* CI/CD Pipeline

---

## Author

Rahul Giri

Java Backend Developer

GitHub:
https://github.com/goswamirahu

---

## Project Status

Completed

Features Implemented:

* Registration
* Login
* Payment Processing
* Transaction History
* MySQL Integration
* Docker Support
* GitHub Deployment
