# Secure User Authentication System

A full-stack authentication and user management web application built using Spring Boot and MySQL.

This system allows users to register, login securely with encrypted passwords, and track login activity. It also includes role-based access control with an Admin panel.

---

## 🚀 Features

- User Registration
- Secure Login with BCrypt password hashing
- Session-based authentication
- Role-based access control (ADMIN / USER)
- Admin panel to view all users
- Login activity tracking with timestamp
- Dashboard showing:
  - Total login count
  - Login history
- Relational database design with foreign key mapping
- Clean and responsive UI using Bootstrap

---

## 🗄 Database Design

### Users Table
- id (Primary Key)
- username (Unique)
- password (Encrypted using BCrypt)
- role (USER / ADMIN)

### Login Activity Table
- id (Primary Key)
- login_time
- user_id (Foreign Key → Users table)

---

## 🛠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- MySQL
- BCrypt (spring-security-crypto)

### Frontend
- HTML
- CSS
- Bootstrap
- Thymeleaf

---

## Security Implementation

- Passwords are encrypted using BCrypt
- Plain-text passwords are never stored
- Role-based route protection
- Session management for authentication

---

## ▶ How to Run

1. Clone the repository
2. Configure MySQL credentials in `application.properties`
3. Create a database named: auth_system 
4. Run `UserauthApplication.java`
5. Open in browser:

- http://localhost:8080/register-page
- http://localhost:8080/login-page

---

## 📌 Future Improvements

- Password reset functionality
- Pagination for login history
- REST API version
- Deployment to cloud
