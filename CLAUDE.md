# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Theme Service is a Spring Boot 3.5 / Java 21 microservice, part of the larger **ThemeVest** platform. It manages investment "themes" (e.g. a curated basket of stocks) and their constituent stock allocations. This is an early-stage service (see README roadmap: Docker, Kafka, Kubernetes, AWS, OpenTelemetry are not yet implemented).

## Common commands

```bash
# Build
./mvnw clean install

# Run the service locally (listens on port 8080)
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ThemeServiceApplicationTests
```

Swagger UI (springdoc-openapi) is available at `/swagger-ui.html` when the app is running. Actuator exposes `health` and `info` at `/actuator/*`.

### Database prerequisite

The app connects to PostgreSQL by default (`src/main/resources/application.yaml`), pointed at `jdbc:postgresql://localhost:5432/themevest`. A local Postgres instance with that database must exist before running the app or any test that boots the Spring context. `ddl-auto: update` is set, so schema is auto-migrated from JPA entities — there are no Flyway/Liquibase migration files. H2 is still on the classpath (commented-out config in `application.yaml`) as a leftover from before the Postgres migration.

## Architecture

Standard layered Spring MVC architecture, single module, single package root `com.sriram.themevest`:

- `controller/` — `@RestController` classes, thin; delegate directly to services. `ThemeController` maps `/themes` (GET all, GET by id, POST, PUT, DELETE).
- `service/` — business logic. `ThemeService` implements Theme CRUD over `ThemeRepository`, converting between `CreateThemeRequest` DTOs and `Theme` entities.
- `repository/` — Spring Data JPA repositories (`ThemeRepository`, `StockRepository`), no custom queries yet.
- `entity/` — JPA entities: `Theme` (has `RiskLevel` enum: LOW/MEDIUM/HIGH), `Stock` (keyed by ticker symbol), and `ThemeStock`, the join entity linking a `Theme` to a `Stock` with an `allocationPercentage` and `addedAt` timestamp (many-to-many via an explicit join entity, not `@ManyToMany`).
- `dto/` — request/response shapes decoupled from entities (`CreateThemeRequest`); `dto/error/ErrorResponse` is the standard error body shape.
- `exception/` + `GlobalExceptionHandler` (`@RestControllerAdvice`) — centralized exception-to-HTTP-response mapping. `ThemeNotFoundException` → 404, `MethodArgumentNotValidException` (bean validation failures) → 400. New domain exceptions should be added here rather than handled ad hoc in controllers.
- `config/ClientConfig` + `service/PostServiceClient` — an example/reference `RestClient` integration against a placeholder external API (jsonplaceholder.typicode.com), currently unused by real endpoints (wired into `ThemeController` but commented out). Treat this as a template for adding real outbound HTTP clients, not production logic.

Lombok (`@Getter`/`@Setter`/`@Builder`/`@Data`/`@RequiredArgsConstructor`) is used throughout instead of hand-written boilerplate — prefer the same style for new entities/DTOs/services (constructor injection via `@RequiredArgsConstructor`).

`resilience4j-spring-boot3` is a dependency but not yet wired into any endpoint (see the commented-out `@CircuitBreaker` in `ThemeService.invokeExternalService`) — intended for future outbound-call resilience.

Only a placeholder `contextLoads` test exists currently; there is no established test-writing pattern in this repo yet.
