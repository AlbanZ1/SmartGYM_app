# 🏋️ SmartGym Management System

SmartGym is a Spring Boot–based RESTful backend application for managing a gym.  
It provides APIs for handling members, trainers, workout classes, subscriptions, and revenue reporting, following clean architecture principles and best practices.

The project includes full API documentation, persistent storage, validation, exception handling, and automated testing.

---

## 🚀 Technologies Used

- **Java 21**
- **Spring Boot 4.0.2**
- Spring Web MVC
- Spring Data JPA
- H2 Database (file-based & in-memory)
- Swagger / OpenAPI
- Maven
- JUnit 5
- Mockito
- Lombok

---

## ▶️ How to Run the Application

### Prerequisites
- Java 21 installed
- Maven installed (or IntelliJ Maven support)

### Run with IntelliJ
1. Open the project in IntelliJ
2. Ensure Java SDK is set to **Java 21**
3. Run `SmartgymApplication`

### Run with Maven
```bash
./mvnw spring-boot:run
```
The application will start on:

`http://localhost:8080`

---

## 📖 Swagger API Documentation

Swagger UI is enabled and available at:

`http://localhost:8080/swagger-ui.html`

From Swagger you can:
- Create members
- Create trainers
- Schedule workout classes
- Create subscriptions
- View revenue reports
- Test validation and error handling

---

## 🗄️ Database Configuration
Application (Demo / Runtime Mode)

- Uses **file-based H2**
- Data persists between application restarts

H2 Console:
`http://localhost:8080/h2-console`

Default JDBC URL:
`jdbc:h2:file:./data/smartgym-db`

---

## Testing Mode

- Uses in-memory H2
- Clean database for every test run
- Fully isolated from application data

Test configuration file:

`src/test/resources/application-test.properties`

---

## 🧪 Testing

The project includes automated tests that verify business logic and API behavior.

### **Test Types**

- Service layer tests
- Controller (WebMvc) tests
- Exception handling tests (409 Conflict, validation errors)

### Run All Tests

`mvn test`

✅ All tests pass successfully:

`Tests run: 6, Failures: 0, Errors: 0, Skipped: 0`

---

## 📊 Revenue Reporting

The system includes a revenue reporting endpoint that calculates total revenue from subscriptions.

Example:

`GET /api/reports/revenue`

Returns total revenue based on stored subscription data.

---

## ⚠️ Error Handling

The application uses a **GlobalExceptionHandler** to handle:

- Validation errors
- Business rule conflicts
- Resource not found cases

HTTP status codes are returned correctly (e.g. 400, 404, 409).

---

## ✅ Features Implemented

- Member management
- Trainer management
- Workout class scheduling
- Subscription management
- Revenue reporting
- Input validation
- Global exception handling
- Swagger documentation
- Persistent storage
- Automated testing

---

## 🎓 Academic Notes

This project was developed for academic purposes and follows:

- Clean layered architecture
- Separation of concerns
- RESTful API principles
- Proper test isolation (in-memory DB for tests)

---

## 🏁 Conclusion

**SmartGym** demonstrates a complete backend application built with modern Spring Boot practices.
It is fully functional, tested, documented, and ready for demonstration or evaluation.

---

## Authors


### Alban Zulfija
### Nejazi Shabani