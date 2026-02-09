# Order Processing System – CashInvoice

## 📌 Overview

This project is a **Spring Boot–based Order Processing System** built as part of an assignment. It exposes REST APIs to **create orders**, **fetch orders**, and **list orders by customer**, with the following features:

* RESTful APIs using Spring Boot
* JWT-based authentication and authorization
* Global exception handling
* In-memory order storage
* Order persistence to local file system (JSON files)
* Apache Camel integration (file-based routing readiness)

---

## 🛠 Tech Stack

* **Java**: 8+
* **Spring Boot**: 3.5.x
* **Spring Security**: JWT Authentication
* **Apache Camel**: 4.17.0
* **Jackson**: JSON serialization
* **Maven**: Build & dependency management
* **Postman**: API testing

---

## 📂 Project Structure

```
cashinvoice/
├── src/main/java/com/example/cashinvoice
│   ├── controller      # REST Controllers
│   ├── service         # Business logic
│   ├── model           # Request/Response/Entity models
│   ├── security        # JWT & Spring Security config
│   ├── exception       # Custom exceptions & handlers
│   └── CashinvoiceApplication.java
│
├── input/orders        # Generated order JSON files
├── pom.xml
└── README.md
```

---

## 🔐 Security (JWT)

All order APIs are secured using **JWT-based authentication**.

### JWT Flow

1. Client sends credentials to auth endpoint
2. Server generates JWT
3. Client sends JWT in request headers
4. Server validates JWT using a filter

### Authorization Header Format

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🚀 API Endpoints

### 1️⃣ Create Order

**POST** `/api/orders`

**Headers**

```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

**Request Body**

```json
{
  "customerId": "CUST1001",
  "product": "Laptop",
  "amount": 75000
}
```

**Response (201 CREATED)**

```json
{
  "orderId": "<generated-uuid>",
  "status": "CREATED"
}
```

---

### 2️⃣ Get Order by ID

**GET** `/api/orders/{orderId}`

**Behavior**

* ✅ Returns order details if found
* ❌ Returns `404 NOT FOUND` if order does not exist

**Response (200 OK)**

```json
{
  "orderId": "uuid",
  "customerId": "CUST1001",
  "product": "Laptop",
  "amount": 75000,
  "createdAt": "2026-02-04T16:47:47.248"
}
```

---

### 3️⃣ List Orders by Customer

**GET** `/api/orders?customerId=CUST1001`

**Behavior**

* Returns list of orders for customer
* Returns empty list with `200 OK` if none found

**Response (200 OK)**

```json
[]
```

---

## 📁 File Storage

Every successfully created order is written as a JSON file to:

```
input/orders/order-<orderId>.json
```

This simulates file-based processing for integration with Apache Camel routes.

---

## ⚠️ Error Handling

Centralized exception handling using `@RestControllerAdvice`.

| Scenario             | HTTP Status | Response                 |
| -------------------- | ----------- | ------------------------ |
| Invalid request data | 400         | `{ "error": "message" }` |
| Order not found      | 404         | `{ "error": "message" }` |
| Unauthorized         | 401         | Unauthorized             |

---

## ▶️ Running the Application

### Prerequisites

* Java 8+
* Maven
* IntelliJ IDEA (recommended)

### Steps

```bash
mvn clean install
mvn spring-boot:run
```

Application starts on:

```
http://localhost:8080
```

---

## 🧪 Testing with Postman

1. Generate JWT token
2. Add token to Authorization header
3. Call secured APIs

Example:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## ✅ Assignment Highlights

* Clean REST API design
* Proper HTTP status codes
* JWT-based stateless security
* Exception handling best practices
* Ready for Apache Camel file routing

---

## 👤 Author

**Priyanka**

---

