# Spring Boot Template

[![Java](https://img.shields.io/badge/Java-24-orange.svg)](https://openjdk.org/projects/jdk/24/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16.8-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-✓-blue.svg)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-✓-blue.svg)](https://kubernetes.io/)

A comprehensive template for building production-ready REST APIs using Java 24 and Spring Boot 3.5.0. This template provides a solid foundation with best practices for enterprise application development.

## 🚀 Features

- **Modern Java Stack**: Java 24 with Spring Boot 3.5.3
- **Database Integration**: PostgreSQL with JPA/Hibernate and Liquibase for schema management
- **Comprehensive Testing**: Unit, Integration, and E2E tests with TestContainers
- **Code Quality**: Spotless for code formatting, JaCoCo for coverage
- **Containerization**: Docker and Docker Compose support
- **Kubernetes Ready**: Helm charts for deployment
- **API Documentation**: RESTful API with proper HTTP status codes
- **Development Tools**: Lombok for reducing boilerplate code

## 📋 Prerequisites

- **Java 24** - [Download](https://adoptium.net/)
- **Maven 3.9+** - [Download](https://maven.apache.org/download.cgi)
- **Docker & Docker Compose** - [Download](https://www.docker.com/products/docker-desktop)
- **PostgreSQL 16.8** (optional, Docker will provide this)

## 🛠️ Quick Start

### Option 1: Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd spring-boot-template
   ```

2. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

   This will start:
   - PostgreSQL database on port 5432
   - Spring Boot application on port 8080

3. **Verify the application**
   ```bash
   curl http://localhost:8080/api/books
   ```

### Option 2: Local Development

1. **Start PostgreSQL database**
   ```bash
   docker-compose up db -d
   ```

2. **Build the application**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## 🏗️ Project Structure

```
spring-boot-template/
├── src/
│   ├── main/java/com/unir/template/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── model/          # Entity models
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic layer
│   │   └── TemplateApplication.java
│   ├── main/resources/
│   │   ├── application.yml # Application configuration
│   │   └── db/changelog/   # Liquibase database migrations
│   └── test/
│       ├── java/com/unir/template/
│       │   ├── controller/ # Controller tests
│       │   ├── e2e/        # End-to-end tests
│       │   ├── it/         # Integration tests
│       │   └── service/    # Service tests
│       └── resources/
│           └── application-test.yml
├── helm/                   # Kubernetes Helm charts
├── docker-compose.yml      # Local development environment
├── Dockerfile             # Application containerization
└── pom.xml               # Maven configuration
```

## 📚 API Documentation

This template includes comprehensive OpenAPI documentation for all REST endpoints. The API documentation is automatically generated and provides:

- **Interactive API Explorer**: Swagger UI for testing endpoints
- **Complete API Specification**: OpenAPI 3.1.0 compliant documentation
- **Request/Response Examples**: Detailed examples for all operations
- **Schema Definitions**: Complete data models and validation rules

### Accessing the API Documentation

Once the application is running, you can access the documentation at:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **OpenAPI YAML**: [docs/openapi.yaml](docs/openapi.yaml)

### API Endpoints Overview

The template includes a sample Books API with the following operations:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/books` | Get all books |
| `GET` | `/api/books/{id}` | Get book by ID |
| `POST` | `/api/books` | Create a new book |
| `PUT` | `/api/books/{id}` | Update an existing book |
| `DELETE` | `/api/books/{id}` | Delete a book |

### Example Usage

```bash
# Get all books
curl http://localhost:8080/api/books

# Create a new book
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot in Action",
    "description": "A comprehensive guide to Spring Boot development",
    "price": 29.99,
    "stock": 100
  }'

# Get a specific book
curl http://localhost:8080/api/books/{book-id}

# Update a book
curl -X PUT http://localhost:8080/api/books/{book-id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Title",
    "description": "Updated description",
    "price": 39.99,
    "stock": 50
  }'

# Delete a book
curl -X DELETE http://localhost:8080/api/books/{book-id}
```

For complete API documentation with examples, schemas, and interactive testing, visit the [Swagger UI](http://localhost:8080/swagger-ui.html) when the application is running.

## 🐳 Docker

### Build the Docker image
```bash
mvn clean package
docker build -t spring-boot-template .
```

### Run with Docker
```bash
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/template_db \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  spring-boot-template
```

## ☸️ Kubernetes Deployment

### Using Helm

1. **Update the image repository in `helm/values.yaml`**
   ```yaml
   image:
     repository: your-dockerhub-username/spring-boot-template
     tag: latest
   ```

2. **Deploy to Kubernetes**
   ```bash
   helm install spring-boot-template ./helm
   ```

3. **Access the application**
   ```bash
   kubectl port-forward svc/spring-boot-template 8080:80
   ```

### Helm Values

Key configuration options in `helm/values.yaml`:

- `replicaCount`: Number of application replicas
- `image.repository`: Docker image repository
- `image.tag`: Docker image tag
- `resources`: CPU and memory limits/requests
- `env`: Environment variables for database connection

## 🔧 Configuration

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_URL` | `jdbc:postgresql://localhost:5432/template_db` | Database connection URL |
| `DB_USERNAME` | `postgres` | Database username |
| `DB_PASSWORD` | `postgres` | Database password |
| `SERVER_PORT` | `8080` | Application port |

### Application Properties

The main configuration is in `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: spring-boot-template
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/template_db}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.yaml
server:
  port: 8080
```

## 🛠️ Development

### Code Formatting
```bash
mvn spotless:apply
```

### Running Tests
```bash
# All tests
mvn verify

# Unit tests only
mvn test

# Integration tests only
mvn failsafe:integration-test
```

### Database Migrations

Liquibase is configured for database schema management. Migration files are located in `src/main/resources/db/changelog/`.

## 📦 Dependencies

### Core Dependencies
- **Spring Boot 3.5.0** - Application framework
- **Spring Data JPA** - Data access layer
- **PostgreSQL** - Database
- **Liquibase** - Database migration tool
- **Lombok** - Reduces boilerplate code
- **SpringDoc OpenAPI** - API documentation generation

### Testing Dependencies
- **Spring Boot Test** - Testing framework
- **TestContainers** - Container-based testing
- **Rest Assured** - API testing
- **Instancio** - Test data generation
- **JaCoCo** - Code coverage

### Build Tools
- **Maven** - Build tool
- **Spotless** - Code formatting
- **Docker** - Containerization

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [Issues](../../issues) page
2. Create a new issue with detailed information
3. Include logs and error messages

---

**Happy Coding! 🚀**
