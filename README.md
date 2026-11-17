# Trading Portfolio API

A REST API for managing user-owned investment portfolios and recording trades. Built with Java 17 and Spring Boot, the application persists portfolio and trade data with JPA and PostgreSQL, and derives each portfolio's net positions from its trade history.

The project demonstrates a layered Spring application, authenticated resource access, relational persistence, request validation, and domain-level aggregation. It is an active development project; it does not currently provide market data or portfolio valuation.

## Features

- **Portfolio management:** Create, list, retrieve, update, and delete portfolios.
- **Trade management:** Record, list, retrieve, update, and delete BUY and SELL trades associated with a portfolio.
- **Position aggregation:** Calculate net quantity by symbol from the selected portfolio's trades. BUY quantities are added and SELL quantities are subtracted; positions are calculated on request rather than stored separately.
- **User accounts and access control:** Persist users, encode passwords with BCrypt, and protect portfolio and trade endpoints with Spring Security. Portfolio and trade queries are scoped to the authenticated user.
- **Request validation:** Trade creation rejects blank symbols and non-positive quantities or prices.
- **Persistence and local deployment:** Use PostgreSQL for application data. Docker Compose starts the API and database together, with a named volume for database data.

## Technology

| Area | Technologies |
| --- | --- |
| Language and framework | Java 17, Spring Boot 3.5.15 |
| Web and security | Spring Web MVC, Spring Security |
| Persistence | Spring Data JPA, Hibernate, PostgreSQL |
| Build and tests | Maven, JUnit 5, Spring Boot Test |
| Other dependencies | Jakarta Validation, Lombok, H2 (runtime), Spring Data Redis |

Spring Data Redis is currently a dependency only; the application does not yet use Redis-backed caching or session storage.

## API

All application endpoints require authentication under the current security configuration. Spring Security provides form login and HTTP Basic authentication. Users created through the user endpoint receive the `basic` authority; the admin sample endpoint requires the `admin` authority.

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/portfolios` | List portfolios owned by the signed-in user |
| `POST` | `/portfolios` | Create a portfolio |
| `GET` | `/portfolios/{id}` | Get an owned portfolio |
| `PUT` | `/portfolios/{id}` | Update an owned portfolio's name |
| `DELETE` | `/portfolios/{id}` | Delete an owned portfolio |
| `GET` | `/portfolios/{id}/positions` | Calculate net positions for an owned portfolio |
| `GET` | `/trades` | List trades belonging to the signed-in user's portfolios |
| `POST` | `/trades` | Create a trade in an owned portfolio |
| `GET` | `/trades/{id}` | Get a trade belonging to the signed-in user |
| `PUT` | `/trades/{id}` | Update an owned trade's symbol, quantity, price, or side |
| `DELETE` | `/trades/{id}` | Delete an owned trade |
| `POST` | `/users/create` | Create a user with the `basic` authority |
| `GET` | `/users/home` | Basic-authority example endpoint |
| `GET` | `/users/basic` | Basic-authority example endpoint |
| `GET` | `/users/admin` | Admin-authority example endpoint |

`/users/create` accepts `username` and `password` request parameters. It is not public registration: the current security configuration requires authentication for application endpoints other than `/login`. User provisioning for a fresh deployment is not automated.

### Example requests

Create a portfolio:

```http
POST /portfolios
Content-Type: application/json

{
  "name": "Long-term investments"
}
```

Create a trade by including the ID of an existing portfolio owned by the authenticated user:

```http
POST /trades
Content-Type: application/json

{
  "portfolio": { "id": 1 },
  "symbol": "AAPL",
  "quantity": 100,
  "price": 185.50,
  "side": "BUY"
}
```

Get positions with `GET /portfolios/1/positions`:

```json
[
  {
    "symbol": "AAPL",
    "quantity": 130.0
  },
  {
    "symbol": "TSLA",
    "quantity": 50.0
  }
]
```

For example, buying 100 AAPL shares, buying 50 more, then selling 20 produces a net position of 130 shares. The API reports quantities only; it does not calculate current market value or enforce that a sell is covered by prior buys.

## Architecture

The application separates HTTP handling, business operations, persistence, and domain data:

- **Controllers** expose portfolio, trade, and user endpoints.
- **Services** implement CRUD workflows, ownership checks, account creation, and position aggregation.
- **Repositories** use Spring Data JPA to access PostgreSQL-backed entities.
- **Domain model** includes `User`, `Portfolio`, and `Trade` entities, plus a `Position` response model derived from trades.

## Run Locally

### Docker Compose

With Docker and the Compose plugin installed, start PostgreSQL and the API from the repository root:

```powershell
docker compose up --build
```

The API listens on `http://localhost:8080`; PostgreSQL is exposed on port `5432`. Compose uses development credentials from `docker-compose.yml` and persists database files in the `postgres-data` volume. Do not reuse the sample credentials in a deployed environment.

Stop the containers with `Ctrl+C`, or run `docker compose down`. The named database volume is retained when stopping the stack.

### Run the application with a local PostgreSQL database

1. Install Java 17 and ensure PostgreSQL is running.
2. Configure the connection settings in `src/main/resources/application-local.properties` for your local database.
3. From the repository root, run:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

The API is available at `http://localhost:8080`.

## Build and Test

Run the test suite with the Maven wrapper:

```powershell
.\mvnw.cmd test
```

Or on macOS/Linux:

```bash
./mvnw test
```

The current automated test is a Spring application-context smoke test. Endpoint-level and business-logic test coverage remains an area for further development.

## Current Scope

Implemented: user accounts and authorization, portfolio and trade CRUD, ownership checks, trade validation, PostgreSQL persistence, and position aggregation.

Not currently implemented: market-price integration, portfolio valuation, public sign-up, Redis-backed features, cloud deployment, asynchronous/event-driven processing, and CI/CD automation.