
# Order Management System

## Overview
The Port Management System is a microservices-based architecture designed to handle the order processing cycle.

## Business Domain
This system facilitates the management of:
- **Ship Management**: order-service that response for order cycle.

## Technical Architecture
src/main/java/com/bwagih/orderservice/
├── application
│   ├── advice            # Global exception handling (ControllerAdvice)
│   └── services          # Application services (use cases)
├── domain
│   ├── order             # Order aggregates and entities
├── infrastructure
│   ├── aspects           # AOP Aspects (e.g., MeasurementAspect)
│   ├── config            # Configuration classes (e.g., RabbitMQ, MongoDB)
│   └── repositories      # Repository implementations
└── shared
├── utils             # Shared utilities or helpers
└── annotations       # Custom annotations (e.g., MeasureExecutionTime)

