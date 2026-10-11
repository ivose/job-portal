# AI Job Portal System 🚀

A full-stack AI-powered Job Portal project built using **Spring Boot, React, PostgreSQL, Docker, and Microservices Architecture**.

This is a hands-on learning project developed step by step, following the [AI Job Portal YouTube Tutorial](https://youtu.be/46Uu_vyqJB8) by Code With Zosh.

> **Project Status:** 🚧 Work in Progress — Currently developing the backend infrastructure and User Service.

## 🛠️ Tech Stack

**Backend**
- Java 21
- Spring Boot 4.0.5
- Spring Cloud 2025.1.1
- Spring Data JPA / Hibernate
- Maven (Multi-Module Project)
- PostgreSQL
- Docker & Docker Compose

**Frontend (Planned)**
- React 19

**Other Technologies (Planned)**
- Kafka
- Gemini AI
- Spring Cloud Microservices

## 📁 Project Structure

The project is organized as a Maven multi-module application.

- **`cloud/`** — Cloud infrastructure modules (planned: service discovery, config server, API gateway).
- **`common-lib/`** — Shared classes, domain models, enums and DTOs used across services.
- **`services/`** — Parent module for business microservices.
    - **`job-portal-user-service/`** — User service containing the Spring Boot application, REST controllers and database configuration.
- **`docker-compose.yml`** — Docker configuration for local PostgreSQL.
- **`pom.xml`** — Root Maven configuration and dependency management.

## ✨ Planned Features

- User authentication and profile management
- Job posting and application management
- AI-powered job matching
- AI resume builder
- AI cover letter generator
- Smart AI job search
- AI candidate screening
- Microservices communication

These features will be developed gradually as the project progresses.

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed:

- JDK 21
- Maven
- Docker Desktop
- IntelliJ IDEA (recommended)

### 1. Clone the Repository

Clone this repository and open the `job-portal-system` directory in IntelliJ IDEA.

### 2. Start PostgreSQL

From the project root, run:

`docker compose up -d`

This starts PostgreSQL using the configuration in `docker-compose.yml`.

### 3. Build the Project

Run from the root directory:

`mvn clean install -DskipTests`

### 4. Run the User Service

Navigate to the user service:

`cd services/job-portal-user-service`

Start Spring Boot:

`mvn spring-boot:run`

The service is configured to run at:

**http://localhost:5001**

The current root endpoint is a simple test controller.

## 🗄️ Database Configuration

The User Service connects to a local PostgreSQL database.

| Property | Value |
|---|---|
| Database | PostgreSQL |
| Host | localhost |
| Port | 5432 |
| Database Name | yt_job_portal_user |
| Username | postgres |

Configuration file:

`services/job-portal-user-service/src/main/resources/application.yaml`

Database credentials in the current setup are intended for local development only. Production credentials should be stored securely, using environment variables or secret management.

## 📌 Current Progress

- [x] Created Maven multi-module project
- [x] Configured Java 21 and Spring Boot
- [x] Set up shared Common Library
- [x] Created User Service module
- [x] Added basic REST controller
- [x] Configured PostgreSQL connection
- [x] Added Docker Compose configuration
- [ ] Implement user management APIs
- [ ] Implement authentication and authorization
- [ ] Develop additional microservices
- [ ] Build React frontend
- [ ] Integrate AI functionality
- [ ] Add automated tests and production deployment

## 📚 Learning Resource

This project is based on the following tutorial:

**AI Job Portal — Full Stack AI Project**

YouTube: https://youtu.be/46Uu_vyqJB8

Credit goes to **Code With Zosh** for the original tutorial and project guidance.

This repository documents my own implementation and learning progress.

---

**Built for learning, experimenting, and improving full-stack development skills.**