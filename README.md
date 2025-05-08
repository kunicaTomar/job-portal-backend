# 💼 Job Portal Backend

A complete Spring Boot REST API for a Job Portal system supporting users, recruiters, and admin roles with JWT authentication, job posting, and job applications.

---

## 🔧 Tech Stack

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (MySQL)
- Lombok
- Swagger UI (OpenAPI 3)
- Maven

---

### 📁 Project Folder Structure

```
job-portal-backend/
├── .mvn/
├── .vscode/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/job_portal_backend/
│   │   │       ├── config/           # Configuration files (e.g., WebSecurityConfig)
│   │   │       ├── controllers/      # REST Controllers (UserController, AuthController, etc.)
│   │   │       ├── dto/              # Data Transfer Objects (Request/Response)
│   │   │       ├── enums/            # Enum types (Roles, Status, etc.)
│   │   │       ├── exception/        # Custom exceptions and global handlers
│   │   │       ├── models/           # Entity classes (User, Job, Application)
│   │   │       ├── repository/       # Spring Data JPA repositories
│   │   │       ├── security/         # JWT filters and auth handlers
│   │   │       ├── services/         # Service layer (business logic)
│   │   │       └── specification/    # JPA Specifications for filtering/search
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ... (e.g., static files or SQL seed files)
├── test/                            # Unit and integration tests
├── target/                          # Compiled bytecode (generated)
├── .gitignore
├── .gitattributes
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml                          # Maven dependencies
└── README.md

```

## 🧩 Roles & Permissions

| Role       | Permissions |
|------------|-------------|
| USER       | Register, Login, View & edit own profile, Apply & manage job applications |
| RECRUITER  | Register, Login, Post/manage jobs, View applicants |
| ADMIN      | Full access, View all users |

---

## 📚 API Endpoints Overview

### 🔐 Auth APIs (`/api/auth`)
| Method | Endpoint           | Description                        |
|--------|--------------------|------------------------------------|
| POST   | `/register`        | Register as user or recruiter      |
| POST   | `/login`           | Login and receive JWT token        |

---

### 👤 User APIs (`/api/user`)
| Method | Endpoint               | Description                             | Role           |
|--------|------------------------|-----------------------------------------|----------------|
| GET    | `/allUser`             | View all users with pagination          | ADMIN, RECRUITER |
| GET    | `/myProfile`           | Get your profile                        | ALL            |
| PUT    | `/myProfile`           | Update your profile                     | ALL            |
| DELETE | `/myProfile`           | Delete your profile                     | ALL            |

---

### 🧑‍💼 Recruiter Job APIs (`/api/recruiter/job`)
| Method | Endpoint                  | Description                          | Role      |
|--------|---------------------------|--------------------------------------|-----------|
| POST   | `/postJob`                | Post a new job                       | RECRUITER |
| GET    | `/jobPostedByMe`          | View jobs posted by current recruiter| RECRUITER |
| PUT    | `/{jobId}`                | Update a job                         | RECRUITER |
| DELETE | `/{jobId}`                | Delete a job                         | RECRUITER |

---

### 📥 Recruiter Application APIs (`/api/recruiter/applications`)
| Method | Endpoint                      | Description                         | Role      |
|--------|-------------------------------|-------------------------------------|-----------|
| GET    | `/job-applicants`             | View all applicants to posted jobs | RECRUITER |
| PUT    | `/{applicationId}/status`     | Update application status          | RECRUITER |

---

### 🧑‍🎓 User Application APIs (`/api/user/applications`)
| Method | Endpoint             | Description                         | Role   |
|--------|----------------------|-------------------------------------|--------|
| POST   | `/apply`             | Apply to a job                      | USER   |
| GET    | `/`                  | View your job applications          | USER   |
| DELETE | `/{jobId}`           | Withdraw job application            | USER   |

---

### 💼 Public Job APIs (`/api/job`)
| Method | Endpoint        | Description                        | Role     |
|--------|-----------------|------------------------------------|----------|
| GET    | `/allJob`       | Get all jobs                       | Public   |
| GET    | `/{jobId}`      | Get job by ID                      | Public   |
| POST   | `/search`       | Search jobs with filters           | Public   |

---

### 🛠️ Admin Dashboard APIs (`/api/admin`)
| Method | Endpoint         | Description                  | Role  |
|--------|------------------|------------------------------|-------|
| GET    | `/`              | View admin dashboard details | ADMIN |

---

## 🚀 Setup Instructions

### Prerequisites
- Java 17
- MySQL running
- Maven
- Git

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/job-portal-backend.git
   cd job-portal-backend
   ```

2. **Create the Database**
   ```sql
   CREATE DATABASE job_portal;
   ```
3. **Update `application.properties`**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
   spring.datasource.username=your_mysql_user
   spring.datasource.password=your_mysql_password
   jwt.secret=your_jwt_secret_key
   ```

4. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access Swagger UI**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

## 🔐 Security Notes

All protected endpoints require JWT tokens. Use this header:
```
Authorization: Bearer <your_token_here>
```

---

## 🤝 Contribution

1. Fork the repo
2. Create a feature branch: `git checkout -b feature-x`
3. Commit your changes
4. Push the branch and open a PR

---
## 🙌 Acknowledgements

Big thanks to Spring Boot and the open-source community!
