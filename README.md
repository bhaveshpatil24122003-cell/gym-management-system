
# Gym Management System

A Spring Boot REST API for managing gym members, memberships, and membership expiry.

## Technologies Used
- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- Spring Security
- JWT Authentication
- MySQL
- Hibernate
- Maven
- Lombok
- REST APIs

## Features
- Member Management (CRUD)
- Membership Management
- Membership Expiry Tracking
- Admin and Member Roles
- JWT-Based Login
- Admin Dashboard
- Member Dashboard
- Email Integration

## API Endpoints

### Authentication
- POST /auth/login

### Member Management
- POST /members
- GET /members
- GET /members/{id}
- PUT /members/{id}
- DELETE /members/{id}
- GET /members/me

### Membership Management
- POST /memberships/member/{memberId}
- GET /memberships
- GET /memberships/member/{memberId}
- PUT /memberships/{membershipId}
- DELETE /memberships/{membershipId}

## Database Setup

```sql
CREATE DATABASE gym_management;
```

## Environment Variables

Configure the following variables:

```env
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
MAIL_USERNAME=your_email
MAIL_PASSWORD=your_email_app_password
JWT_SECRET=your_secure_jwt_secret
```

Never upload real passwords or secret keys to GitHub.

## Run Application

```bash
./mvnw spring-boot:run
```

For Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## API Base URL

http://localhost:8080

## Tools
- Spring Tool Suite / Eclipse
- VS Code
- MySQL Workbench
- Postman
- Git & GitHub

## Author
Bhavesh Patil

