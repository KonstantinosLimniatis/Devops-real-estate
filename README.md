# Real Estate Management System
## Overview
This project is a Real Estate Management System
that allows owners to create real estates and manage rental requests
while users can search for real estates and make rental requests to them.
Admins oversee the platform by verifying users and managing real estates.
## Features
- Authentication & Authorization
- User login & registration (Users, Owners, Admin)
- Role-based access control with Spring Security
- **Users** can:
    - View available real estates: Browse all approved real estates listed on the platform.
    - Rent a real estate: Apply to rent a real estate.
- **Owners** can:
    - View own real estates: Owners can see only the real estates they have listed.
    - Add new real estate: Owners can submit new real estate listings by providing key details (city, address, type, description, size and price).
    - Edit real estate details: Owners can update specific fields (type, description, size, price).
    - Delete real estates: Owners can remove their real estates from the platform.
    - Approve or decline rental requests: Owners can review all rental requests for their real estates.
- **Admins** can:
    - Approve or reject new real estate listings: Admins review real estate listings before they become visible.
    - Manage users: Admins can view all users and modify their details (email, username) and they can also create new users by entering their username, email, and password.
    - Manage roles: Admins can add or remove roles (User, Owner, Admin) from any account.
### Tech Stack
- <b>Backend:</b> Spring Boot (Java), Hibernate (JPA), PostgreSQL
- <b>Frontend:</b> Thymeleaf
- <b>Security:</b> Spring Security
### 1. Installation & Setup
- Clone the Repository
```sh
git clone https://github.com/it2022069/ds-ergasia.git
```
### 2. Configure the Database
Modify the application.properties file with your database:
```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
### 3. Run the Application
---------------------------------------------------------------------------------------------
# DevOpsX – Real Estate Management System  
Spring Boot | Docker | PostgreSQL (Render) | Maven

## 📌 Overview  
Το DevOpsX είναι ένα Real Estate Management System βασισμένο σε Spring Boot, το οποίο τρέχει πλήρως σε Docker και συνδέεται σε PostgreSQL database που φιλοξενείται στο Render.  

Το project περιλαμβάνει:
- Spring Boot Application
- Cloud PostgreSQL (Render)
- Docker containerization
- Maven build system
- Git version control

---

# 🛠️ Τεχνολογίες & Εργαλεία που χρησιμοποιήθηκαν

## 1️⃣ **Java (OpenJDK 17 ή 21)**
Απαιτείται για να τρέξει το Spring Boot project.

**Εγκατάσταση σε Linux (Ubuntu):**
```bash
sudo apt install openjdk-17-jdk

Maven
sudo apt install maven
mvn -v

Docker
sudo apt install docker.io
sudo systemctl start docker
sudo systemctl enable docker

sudo usermod -aG docker $USER
newgrp docker

sudo apt install docker-compose

sudo apt install git
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]



version: "3.8"

services:
  app:
    build: .
    container_name: realestate-app
    ports:
      - "8080:8080"
    environment:
      SPRING_APPLICATION_NAME: ds-2025
      SPRING_DATASOURCE_URL: jdbc:postgresql://dpg-YOUR-RENDER-DB:5432/YOUR_DB?sslmode=require
      SPRING_DATASOURCE_USERNAME: dbuser
      SPRING_DATASOURCE_PASSWORD: yourpassword
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT: org.hibernate.dialect.PostgreSQLDialect
      SPRING_JPA_SHOW_SQL: "true"
      SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL: "true"

1️⃣ Build τοπικά (προαιρετικό)
mvn clean package -DskipTests

2️⃣ Τρέξιμο μόνο με Docker
docker-compose up --build

3️⃣ Τερματισμός
docker-compose down


📥 Πώς κατεβάζεις το project σε άλλο laptop
1️⃣ Κατέβασε το repository
git clone https://github.com/KonstantinosLimniatis/DevOpsX.git

2️⃣ Μπες στον φάκελο
cd DevOpsX

3️⃣ Τρέξε το project με Docker
docker-compose up --build

