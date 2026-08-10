# 🛒 RuralMart Backend

A secure and scalable **Spring Boot REST API** for a rural grocery marketplace. RuralMart enables shop owners to manage their shops and products while providing secure authentication using JWT.

---

## 📌 Features

### 🔐 Authentication & Security
- User Registration
- User Login
- JWT Authentication
- Spring Security
- BCrypt Password Encryption
- Protected REST APIs

### 🏪 Shop Management
- Create Shop
- One Shop per User
- Shop linked to authenticated owner

### 📦 Product Management
- Add Product
- Update Product
- Delete Product
- Get Product by ID
- Get All Products
- Get Products of Logged-in Shop
- Search Products
- Filter by Category
- Pagination

### 📊 Dashboard
- Total Products
- Active Products
- Inactive Products
- Out of Stock Products

---

# 🛠️ Tech Stack

| Technology | Used |
|------------|------|
| Java | 21 |
| Spring Boot | 4.x |
| Spring Security | JWT Authentication |
| Spring Data JPA | Hibernate |
| MySQL | Database |
| Maven | Build Tool |
| Git | Version Control |
| Postman | API Testing |

---

# 📁 Project Structure

```
src
│
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── response
├── security
├── service
│   ├── ProductService
│   ├── ShopService
│   └── UserService
│
├── service
│   └── impl
│
└── RuralmartApplication
```

---

# 🗄️ Database Design

```
User
 │
 │ One-to-One
 ▼
Shop
 │
 │ One-to-Many
 ▼
Product
```

---

# 🔐 Authentication Flow

```
Register
      │
      ▼
Login
      │
      ▼
Generate JWT Token
      │
      ▼
Client stores JWT
      │
      ▼
JWT sent in Authorization Header
      │
      ▼
Spring Security
      │
      ▼
Protected APIs
```

---

# 📡 REST APIs

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |

---

## Shop

| Method | Endpoint |
|---------|----------|
| POST | /api/shops |

---

## Products

| Method | Endpoint |
|---------|----------|
| POST | /api/products |
| GET | /api/products |
| GET | /api/products/{id} |
| PUT | /api/products/{id} |
| DELETE | /api/products/{id} |
| GET | /api/products/search |
| GET | /api/products/category/{category} |
| GET | /api/products/page |
| GET | /api/products/my-shop |

---

## Dashboard

| Method | Endpoint |
|---------|----------|
| GET | /api/products/dashboard |

---

# 🚀 Getting Started

## Clone the Repository

```bash
git clone https://github.com/Rakesh-456/ruralmart-backend.git
```

```
cd ruralmart
```

---

## Configure MySQL

Create a database named

```
ruralmart
```

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ruralmart
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

---

## Run the Project

```bash
mvn spring-boot:run
```

Server starts at

```
http://localhost:8080
```

---

# 🔑 Authorization

Protected APIs require a JWT token.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📷 Sample Dashboard Response

```json
{
    "totalProducts": 12,
    "activeProducts": 10,
    "inactiveProducts": 1,
    "outOfStockProducts": 1
}
```

---

# 💡 Future Enhancements

- Shopping Cart
- Order Management
- Payment Integration
- Product Images
- Customer Reviews
- Wishlist
- Email Notifications
- Admin Dashboard

---

# 👨‍💻 Author

**Rakesh Paragada**

- GitHub: https://github.com/Rakesh-456
- LinkedIn: https://www.linkedin.com/in/rakesh-paragada/

---

# ⭐ If you like this project

Give this repository a ⭐ on GitHub.
