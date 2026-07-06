# Event Booking API

Backend REST API for managing and booking event tickets (concerts, exhibitions, sports events).

This is a personal portfolio project built to demonstrate Java/Spring Boot backend development skills. **Work in progress — not a finished or production-ready application.**

## Stack

- Java 21, Spring Boot 3, Maven
- Spring Data JPA / Hibernate, PostgreSQL
- MapStruct, Bean Validation
- Spring Security + JWT (planned)
- MongoDB, Redis (planned)
- JUnit 5, Mockito, Testcontainers (planned)
- Docker (planned)

## Current state

Full CRUD REST API for `Category`, `Venue`, and `Event`: entities/repositories, DTOs with MapStruct mappers, service layer, controllers, centralized error handling, Bean Validation, pagination/sorting on list endpoints, and Swagger documentation.

Not yet implemented: `Booking` (reservations), authentication, and everything under the "advanced" milestones (concurrency, search, caching, async, CI/CD).

## Running locally

```bash
docker compose up -d      # starts PostgreSQL
./mvnw spring-boot:run
```
