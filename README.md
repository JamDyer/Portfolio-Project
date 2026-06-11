# Trading Portfolio API

A Java Spring Boot backend application for managing investment portfolios and trades.

This project provides a RESTful API that allows users to create portfolios, record buy and sell trades, and calculate current portfolio positions based on historical trading activity.

The application is designed to demonstrate backend engineering concepts including layered architecture, data persistence, domain modelling, validation and business logic implementation using modern Java and Spring Boot.

## Project Status

Active Development

Current functionality includes portfolio management, trade management, position aggregation, data validation and PostgreSQL persistence.

Planned enhancements include Docker containerisation, cloud deployment, portfolio valuation calculations, automated testing and event-driven processing.


##                                          ----------------------  Features  ----------------------


### Portfolio Management

* Create portfolios
* Retrieve portfolios
* Update portfolio details
* Delete portfolios

### Trade Management

* Create trades
* Retrieve trades
* Update trades
* Delete trades
* Associate trades with portfolios

### Position Aggregation

The API calculates current positions dynamically based on trade history.

Example:

Trades:

BUY 100 AAPL

BUY 50 AAPL

SELL 20 AAPL

Result:

AAPL = 130 shares

This is to demonstrate business logic beyond standard CRUD operations.

### Validation

Input validation is implemented using Jakarta Validation:

* Required fields enforced
* Positive quantity validation
* Positive price validation

### Persistence

Data is persisted using Spring Data JPA and PostgreSQL.

##                                       ----------------------  Technology Stack  ----------------------

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL

### Build Tool

* Maven

### Supporting Libraries

* Lombok
* Jakarta Validation

##                                        ----------------------  Architecture  ----------------------

The application intends to follow a layered architecture similar to what is used in enterprise backend systems.

### Controller Layer

Responsible for:

* Handling HTTP requests
* Validating incoming data
* Returning API responses

### Service Layer

Responsible for:

* Business logic
* Position calculations
* Data transformation
* Application workflows

### Repository Layer

Responsible for:

* Database access
* Query generation via Spring Data JPA
* Persistence operations

### Domain Layer

Contains:

* Portfolio entity
* Trade entity
* Position DTO

##                                        ----------------------  Domain Model  ----------------------

### Portfolio

Represents an investment portfolio.

Fields:

* id
* name
* userId
* createdOn

### Trade

Represents a buy or sell transaction.

Fields:

* id
* portfolio
* symbol
* quantity
* price
* side
* createdOn

### Position

Represents an aggregated holding calculated from trade history.

Fields:

* symbol
* quantity

Positions are derived dynamically and are not stored in the database.

##                                     ----------------------  Example API End Points  ----------------------

### Portfolios

GET

/api/portfolios

POST

/api/portfolios

GET

/api/portfolios/{id}

PUT

/api/portfolios/{id}

DELETE

/api/portfolios/{id}

### Trades

GET

/api/trades

POST

/api/trades

GET

/api/trades/{id}

PUT

/api/trades/{id}

DELETE

/api/trades/{id}

### Positions

GET

/api/portfolios/{id}/positions

Example response:

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

## Key Concepts Demonstrated

* Object-Oriented Programming
* REST API Development
* Spring Dependency Injection
* JPA Entity Relationships
* Repository Pattern
* Service Layer Design
* Data Validation
* Business Logic Aggregation
* PostgreSQL Integration
* Domain Modelling

## Future Enhancements

Planned improvements include:

* Docker containerisation
* Google Cloud deployment
* Portfolio valuation calculations
* Market price integration
* Automated testing with JUnit
* CI/CD pipelines
* Event-driven processing
* Redis caching
* Asynchronous trade processing

## Running the Application

- Clone the repository
- Navigate to the project
- Run the application
- The API will be available on

## Learning Objectives

This project is being developed as part of a transition into backend and distributed systems engineering, with a focus on Java, cloud-native development and scalable service design.
