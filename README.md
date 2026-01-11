# 📝 To-Do List – Backend

A secure, scalable, and production-ready **Spring Boot backend** for a modern To-Do application.  
This project is built following **industry best practices** and supports authentication, task management, filtering, sorting, and session handling.

---

## 🚀 Project Overview

This backend powers a full-stack **To-Do List Application** where users can securely manage their personal tasks.  
Each user has isolated access to their own data with session-based authentication.

The system is designed to be **clean, maintainable, and scalable**, making it suitable for real-world applications and enterprise environments.

---

## ✨ Features

### 🔐 Authentication & Security
- User Signup & Login
- Password encryption using **BCrypt**
- Session-based authentication
- Secured REST APIs using **Spring Security**
- Logout with session invalidation

### ✅ Task Management
- Add new tasks
- Edit existing tasks
- Delete tasks
- Mark tasks as **Done**
- Each task supports:
  - **Priority** → Urgent / Normal / Do when time allows
  - **Status** → Not Started / In Progress / Done / Missed Deadline
  - **Deadline** (optional)

### 🔎 Filtering & Sorting
- Filter tasks by priority and status
- Sort tasks by priority, status, or deadline
- User-specific task isolation (no data leakage)

---

## 🛠 Tech Stack

| Layer | Technology |
|-----|-----------|
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security |
| ORM | Spring Data JPA |
| Database | MySQL |
| Authentication | Session-based |
| Build Tool | Maven |

---

## 🧩 Project Structure

src/main/java
└── com.hcl.todo_backend
├── controller # REST Controllers
├── service # Business Logic
├── repository # JPA Repositories
├── entity # Database Entities
├── dto # Request DTOs
├── security # Spring Security Config
└── config # Application Configurations


This structure ensures:
- Separation of concerns
- Easy debugging
- Clean and scalable codebase

---

## 🌐 REST API Endpoints

| Method | Endpoint | Description |
|------|---------|------------|
| POST | `/auth/signup` | Register a new user |
| POST | `/auth/login` | Login user |
| POST | `/auth/logout` | Logout user |
| GET | `/tasks` | Fetch logged-in user tasks |
| POST | `/tasks` | Add new task |
| PUT | `/tasks/{id}` | Update task |
| DELETE | `/tasks/{id}` | Delete task |

---

## 🔗 Frontend Integration

This backend is designed to work with a **React.js frontend dashboard**.

👉 **Frontend GitHub Repository:**  
🔗 https://github.com/Premkumar981/to-do_frontend

---

## ⚙️ Run Locally

### 1️⃣ Clone Repository
```bash
git clone https://github.com/Premkumar981/To-do_backend.git
```
### 2️⃣ Configure Database

Update application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```

### 3️⃣ Start Application
```bash
mvn spring-boot:run
```

Backend runs at:
```bash
http://localhost:8080
```

🎯 Purpose of This Project

- Built as part of HCL Tech Java Full Stack Training

- Demonstrates real-world backend development skills

- Showcases:
  Authentication & Security, REST API design, Spring Boot best practices, Clean architecture

## 👨‍💻 Author

Prem Kumar<br>
Java Full Stack Developer<br>
📍 India

Focused on building secure, clean, and scalable backend systems.
