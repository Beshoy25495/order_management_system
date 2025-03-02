
# Order Management System

## Overview
The Order Management System is a microservices-based architecture designed to handle the order processing cycle.

## Business Domain
This system facilitates the management of:
- **Order Management**: The order-service is responsible for handling the order cycle.

## Technical Architecture
The project structure follows Domain-Driven Design principles and is organized as follows:

```
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
│   └── presentation      # REST controllers and DTOs
└── shared
    ├── utils             # Shared utilities or helpers
    └── annotations       # Custom annotations (e.g., MeasureExecutionTime)
```

## Features
- **Order Processing**: Manage order creation, updates, and status tracking.
- **Asynchronous Communication**: RabbitMQ for message queuing.
- **Persistence**: MongoDB as the primary database for storing order data.
- **Performance Monitoring**: Custom annotations like `MeasureExecutionTime` for execution time tracking.

## Technologies Used
- **Java 17**
- **Spring Boot 3.1.2**
- **MongoDB**
- **RabbitMQ**
- **Lombok** for boilerplate code reduction
- **MapStruct** for DTO mappings
- **Maven** for build and dependency management

## Setup and Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Beshoy25495/order_management_system.git
   cd order-management-system
   ```

2. Build the application:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Custom Annotations
- **@MeasureExecutionTime**: Tracks and logs the execution time of methods. Implemented using Spring AOP.

## Directory Details
- **application**: Contains use case-specific services and global exception handling advice.
- **domain**: Holds core business logic and domain models (aggregates and entities).
- **infrastructure**: Contains AOP aspects, configuration files, and repository implementations.
- **shared**: Utilities and custom annotations that can be reused across modules.

## Contribution Guidelines
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature-name`.
5. Open a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---
Developed by [bwagih].
