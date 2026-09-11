# 🏋️ PulseFit - Gym Management System

A full-stack **Gym Management System** developed using **Java, Spring Boot, Spring Security, JWT, Spring Mail, MySQL, HTML, CSS, and JavaScript**.

The application provides secure role-based access for **Admin** and **Members** and manages the complete member and membership lifecycle, including membership plans, expiry tracking, dashboard statistics, and automated membership receipt emails.

---

## 🚀 Features

### 👨‍💼 Admin

- Secure Admin Login
- Add, View, Update and Delete Members
- Manage Membership Plans
- View Active and Expired Memberships
- Membership Expiry Tracking
- Admin Dashboard with Statistics
- View Membership Revenue
- Automated Membership Receipt Email

### 👤 Member

- Secure Member Login
- View Personal Profile
- View Membership Details
- Check Registration and Expiry Date
- View Membership Status

---

## 📸 Application Screenshots

### 🔐 Login Page


<img width="1627" height="786" alt="Screenshot 2026-09-11 162714" src="https://github.com/user-attachments/assets/1aaf6e38-0e37-4c25-81c2-cd16a38ed1bc" />




---

## 🔐 Security

The application uses **Spring Security and JWT** to provide secure authentication and authorization.

- Spring Security
- JWT-Based Authentication
- Stateless Authentication
- Role-Based Authorization (`ADMIN` / `MEMBER`)
- BCrypt Password Hashing
- Protected REST APIs
- JWT Secret stored using Environment Variables

---

## 🛠️ Tech Stack

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- Spring Mail
- Spring Scheduler
- REST APIs
- Maven

### Database

- MySQL

### Frontend

- HTML
- CSS
- JavaScript

### Development & Testing

- Spring Tool Suite (STS)
- Postman
- MySQL Workbench
- Git
- GitHub
- VS Code

---

## 🏗️ Architecture

The project follows a layered architecture:

```text
Frontend
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Spring Data JPA / Hibernate
   ↓
MySQL Database
```

Additional layers and components include:

```text
DTO
Mapper
Entity
Exception Handling
Security
Configuration
Scheduler
Email Service
```

---

## 🔑 Authentication Flow

```text
User enters Email + Password
          ↓
       Login API
          ↓
Spring Security Authentication
          ↓
     JWT Generated
          ↓
Frontend stores JWT
          ↓
JWT sent with protected requests
          ↓
Role checked (ADMIN / MEMBER)
          ↓
      Access Granted
```

The application uses **stateless JWT authentication**, so protected API requests require a valid JWT token.

---

## 💳 Membership Management

The system supports multiple membership plans:

- 1 Month Membership
- 3 Months Membership
- 6 Months Membership
- 1 Year Membership
- Automatic Registration Date
- Automatic Expiry Date Calculation
- ACTIVE / EXPIRED Membership Status
- Membership Payment Details

A scheduled task using **Spring Scheduler** automatically tracks membership expiry and updates expired memberships.

---

## 📧 Automated Membership Receipt Email

When an Admin assigns a membership to a member, the system automatically sends a membership receipt to the member's registered email address using **Spring Mail**.

The email receipt includes:

- Member Details
- Roll Number
- Membership Plan
- Amount Paid
- Payment Mode
- Registration Date
- Expiry Date
- Membership Status

Email sending is handled asynchronously using Spring's **`@Async`** functionality.

This allows the membership operation to complete without waiting for the email sending process.

---

## 🗄️ Database Relationship

The system uses a **One-to-One relationship** between Member and Membership.

```text
Member
  │
  │ One-to-One
  ↓
Membership
```

Each membership is associated with one registered member.

---

## ⚠️ Exception Handling & Validation

The application includes:

- Global Exception Handling
- Resource Not Found Handling
- Duplicate Resource Handling
- Request Validation
- Email Validation
- Phone Number Validation
- Password Validation
- Membership Validation

---

## 🗑️ Soft Delete

Members are soft deleted instead of immediately removing their records from the database.

```text
deleted = false → Active Member

deleted = true  → Deleted / Inactive Member
```

This helps preserve member records for administrative history while preventing deleted members from accessing the system.

---

## ⏰ Automatic Membership Expiry

The application uses **Spring Scheduler** to automatically check membership expiry dates.

```text
Current Date
     ↓
Check Membership Expiry Date
     ↓
Is Membership Expired?
     ↓
    Yes
     ↓
Status = EXPIRED
```

This reduces the need for manual membership status updates.

---

## 📊 Admin Dashboard

The Admin Dashboard provides useful statistics for gym management, including:

- Total Members
- Active Memberships
- Expired Memberships
- Membership Revenue
- Member Management
- Membership Management

---

## 🔒 Environment Variables

Sensitive credentials and secrets are not hardcoded in the source code.

The application uses environment variables such as:

```text
DB_USERNAME
DB_PASSWORD

MAIL_USERNAME
MAIL_PASSWORD

ADMIN_NAME
ADMIN_EMAIL
ADMIN_PASSWORD

JWT_SECRET
```

The Spring Boot configuration accesses these values using environment variables.

Example:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}

jwt.secret=${JWT_SECRET}
```

> ⚠️ Never commit real passwords, JWT secrets, database credentials, Admin credentials, or mail credentials to the repository.

---

## ▶️ Running the Project

### 1. Clone the Repository

```bash
git clone https://github.com/bhaveshpatil24122003-cell/gym-management-system.git
```

### 2. Open the Project

Import the project into **Spring Tool Suite (STS)** or another compatible Java IDE.

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE gym_management;
```

### 4. Configure Environment Variables

Configure the required environment variables:

```text
DB_USERNAME
DB_PASSWORD
MAIL_USERNAME
MAIL_PASSWORD
ADMIN_NAME
ADMIN_EMAIL
ADMIN_PASSWORD
JWT_SECRET
```

### 5. Run the Application

Run the Spring Boot application from STS.

The application runs on:

```text
http://localhost:8080
```

### 6. Open the Login Page

Open the application in a browser and login using an authorized **Admin** or **Member** account.

---

## 📌 API Modules

The backend provides REST APIs for:

- Authentication
- Member Management
- Membership Management
- Admin Dashboard
- Member Dashboard
- Membership Expiry Tracking

---

## 📂 Project Structure

```text
src/main/java/com/gym
│
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
├── service
│   └── impl
└── util
```

Frontend resources are available under:

```text
src/main/resources/static
│
├── HTML
├── CSS
└── JAVASCRIPT
```

---

## 🌟 Key Highlights

- Full-Stack Web Application
- Admin and Member Modules
- JWT-Based Stateless Authentication
- Role-Based Authorization
- BCrypt Password Encryption
- Member CRUD Operations
- Membership Management
- One-to-One Database Relationship
- Automatic Membership Expiry Tracking
- Spring Scheduler
- Automated Email Receipt using Spring Mail
- Asynchronous Email Processing
- Soft Delete Functionality
- Dashboard Statistics
- REST API Development
- DTO and Mapper Architecture
- Global Exception Handling
- Environment-Based Secret Management

---

## 👨‍💻 Developer

**Bhavesh Patil**

Computer Engineering Student  
Java Full Stack Developer

---

⭐ If you found this project useful, consider giving the repository a star.
