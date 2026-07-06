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

- Core entities (`Event`, `Venue`, `Category`) and repositories
- DTOs and MapStruct mappers for `Event`, `Venue`, `Category`

Not yet implemented: REST controllers, service layer, authentication, and everything under the "advanced" milestones (concurrency, search, caching, async, CI/CD).

## Running locally

```bash
docker compose up -d      # starts PostgreSQL
./mvnw spring-boot:run
```

## Architecture diagrams

`docs/uml/` contains UML class/package diagrams generated directly from the source code (not hand-maintained, so they can't drift out of sync). Regenerate after any structural change:

```bash
./mvnw -P uml javadoc:javadoc
```
