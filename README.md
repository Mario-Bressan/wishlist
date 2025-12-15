# Wishlist API

Implementation of a **Wishlist** feature for an e-commerce platform, designed to run as an independent microservice in a cloud environment.

## Tech stack

- **Java 21**
- **Spring Boot 3.4.12**
- **MongoDB** (NoSQL)
- **Spock** (unit tests, Groovy)
- **Docker / Docker Compose**
- **springdoc-openapi** for Swagger UI

## Main features

- Add a product to a customer’s wishlist
- Remove a product from the wishlist
- List all products from a customer’s wishlist
- Check if a given product is present in the customer's wishlist
- Enforce a maximum of **20 items** per wishlist:
    - If the customer tries to add a 21st new product, a business exception is raised

## Architecture overview

The project follows a simple layered / clean structure:

- **api** – HTTP layer (controllers, DTOs, error handling)
- **domain**
    - `model.Wishlist` – domain entity with business rules (limit, add/remove)
    - `usecase.WishlistUseCase` – application service with use cases
    - `port.WishlistRepository` – output port for persistence
- **infra.persistence.mongo**
    - `WishlistDocument`, `SpringDataWishlistRepository`, `WishlistMongoRepository` – MongoDB adapter for the domain port

This keeps **business rules** isolated from **infrastructure** and makes testing easier.

## Running locally

### Prerequisites

- Java 21
- Docker + Docker Compose
- MongoDB (can be local or via Docker)

### Option 1 – Run with Docker Compose

This starts MongoDB and the application together.

```bash
# build and start
docker compose up --build

# stop
docker compose down
```

By default the API will be available at:

- `http://localhost:8080`

### Option 2 – Run locally with Gradle

1. Make sure MongoDB is running (for example on `mongodb://localhost:27017`)
2. Set the connection properties in `application.yaml` if needed
3. Run:

```bash
./gradlew clean bootRun
```

## API & Swagger

Once the application is running, the OpenAPI/Swagger UI is available at:

- `http://localhost:8080/swagger-ui.html`

From there you can explore and try all endpoints (add, remove, list, contains).

## Tests

Unit tests (domain + use cases) are written with **Spock**.

```bash
./gradlew test
```

They focus mainly on:

- Wishlist business rules (max limit, duplicates, removal)
- Wishlist use case behavior with mocked repository