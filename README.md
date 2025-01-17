# Purchase Management System

This is a Spring Boot application for managing user registrations, purchases, and purchase verification. The system also includes an auto-reverse feature for unverified purchases after 30 minutes.

## Features
1. **User Registration**: Register a new user with an initial balance.
2. **Token Generation**: Generate a JWT token for authenticated API access.
3. **Purchase Registration**: Register a new purchase with a unique tracking code.
4. **Purchase Verification**: Verify a purchase using its tracking code.
5. **Auto-Reverse**: Automatically reverses unverified purchases after 30 minutes.

---

## Technologies Used
- **Spring Boot**: Backend framework for building the application.
- **MySQL**: Database for storing user and purchase data.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **Maven**: Build automation and dependency management.

---

## Prerequisites
- Java 17 or higher
- MySQL Server
- Maven

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone git@github.com:oruji/purchase-mng.git
cd purchase-management
```

### 2. Configure MySQL Database
Create a MySQL database named purchase_management.

Update the application.properties file with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/purchase_mng_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```
The application will start on http://localhost:8080.

## API Documentation
### 1. Register a User
- **Endpoint**: POST /api/users

- **Description**: Register a new user with an initial balance.

- **Request**:

```bash
curl -L 'localhost:8080/api/users' -H 'Content-Type: application/json' -d '{
    "username": "oruji2",
    "password": "123456",
    "initialBalance": 100000
}'
```
- **Response**:
```json
{
    "status": 200,
    "message": "OK",
    "data": {
        "username": "oruji2"
    },
    "errors": null
}
```
If you omit any mandatory fields in your request, the API will return a 400 Bad Request error with detailed information about the validation errors. Below is an example of the error response:
```json
{
    "status": 400,
    "message": "BAD_REQUEST",
    "data": null,
    "errors": [
        {
            "field": "username",
            "message": "must not be blank"
        }
    ]
}
```
### 2. Generate a Token
- **Endpoint**: POST /api/tokens

- **Description**: Generate a JWT token for authentication.

- **Request**:
```bash
curl -L 'localhost:8080/api/tokens' -H 'Content-Type: application/json' -d '{
    "username": "oruji2",
    "password": "123456"
}'
```
- **Response**:
```json
{
    "status": 200,
    "message": "OK",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvcnVqaTIiLCJpYXQiOjE3MzcxMDE2NjEsImV4cCI6MTczNzEwMzQ2MX0.QS4vYWyWysKC8SyDyZJu8VhkMHiyVpvSqNz4D128nybzLPMLlrc7DHWT7_sw2ES4YxJXr0tNCd_G9Bbf36sAvg"
    },
    "errors": null
}
```

### 3. Register a Purchase
- **Endpoint**: POST /api/purchases

- **Description**: Register a new purchase with a unique tracking code.

- **Request**:
```bash
curl -L 'localhost:8080/api/purchases' -H 'Content-Type: application/json' -H 'Authorization: Bearer <token>' -d '{
    "amount": "10000",
    "items": [
        {
            "name": "pencil",
            "count": 2
        }
    ]
}'
```
- Response:
```json
{
    "status": 200,
    "message": "OK",
    "data": {
        "trackingCode": "1adb3279-069c-4da9-8a14-38bf639087ec",
        "amount": 10000,
        "status": "INITIATED"
    },
    "errors": null
}
```

### 4. Verify a Purchase
- **Endpoint**: POST /api/purchases/verify/{trackingCode}

- **Description**: Verify a purchase using its tracking code.

- **Request**:
```bash
curl -L -X POST 'localhost:8080/api/purchases/verify/1adb3279-069c-4da9-8a14-38bf639087ec' -H 'Authorization: Bearer <token>'
```
- **Response**:
```json
{
    "status": 200,
    "message": "OK",
    "data": {
        "trackingCode": "1adb3279-069c-4da9-8a14-38bf639087ec",
        "amount": 10000.00,
        "status": "VERIFIED"
    },
    "errors": null
}
```

## Auto-Reverse Feature
If a purchase is not verified within 30 minutes, its status will automatically change to REVERSED.