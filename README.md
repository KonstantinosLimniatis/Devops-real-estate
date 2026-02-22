# Real Estate Management System

## Overview
Το project είναι μια web εφαρμογή διαχείρισης ακινήτων με:
- διαχείριση χρηστών και ρόλων,
- καταχώριση/έγκριση αγγελιών,
- αιτήματα ενοικίασης,
- αποστολή email κατά την εγγραφή.

Η εφαρμογή υλοποιείται με Spring Boot και PostgreSQL, και υποστηρίζει deployment με Docker Compose και Ansible.

## Features
- Authentication / Authorization (Spring Security)
- Ρόλοι: `USER`, `OWNER`, `ADMIN`
- User registration / login
- Διαχείριση αγγελιών ακινήτων
- Έγκριση/απόρριψη αγγελιών από admin
- Rental request flow (accept / decline από owner)
- Email notification σε επιτυχημένη εγγραφή

## Tech Stack
- Backend: Spring Boot (Java 21), Spring Security, JPA/Hibernate
- Frontend: Thymeleaf
- Database: PostgreSQL 16
- DevOps: Docker, Docker Compose, Ansible
- Build: Maven

## Components (σε σχέση με την εκφώνηση)
- Βάση δεδομένων: PostgreSQL
- Σύστημα διαχείρισης χρηστών (admin περιβάλλον): υλοποιημένο
- Βασικό πληροφοριακό σύστημα: υλοποιημένο
- Σύστημα ενημέρωσης με email: υλοποιημένο
- Jenkins: δεν υλοποιείται σε αυτό το repo
- Kubernetes: δεν υλοποιείται σε αυτό το repo

## Project Structure

```text
.
├── src/                          # Spring Boot application
├── docker/
│   ├── docker-compose.yml        # Docker services (spring + db)
│   └── backend.env
├── ansible-devops/
│   ├── hosts.yaml
│   ├── group_vars/
│   ├── host_vars/
│   └── playbooks/
├── Dockerfile
└── pom.xml
```

## Local Run (Docker Compose)

1. Πήγαινε στον φάκελο `docker`:

```bash
cd docker
```

2. Σήκωσε services:

```bash
docker compose up -d --build
```

3. Άνοιξε εφαρμογή:

```text
http://localhost:8080
```

4. Τερματισμός:

```bash
docker compose down
```

## Local Run (χωρίς Docker)

Χρησιμοποίησε PostgreSQL και ρύθμισε τις τιμές στο:
- `src/main/resources/application.properties`

Έπειτα:

```bash
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

## Ansible Deployment

Το repo περιέχει playbooks για δύο σενάρια:

1. VM deployment (PostgreSQL VM + Spring VM + Nginx)
- `ansible-devops/playbooks/postgres-16.yaml`
- `ansible-devops/playbooks/spring.yaml`

2. Docker deployment σε VM (compose μέσω Ansible)
- `ansible-devops/playbooks/docker.yaml`

### Βασικά βήματα

```bash
cd ansible-devops
ansible -i hosts.yaml appserver-vm -m ping
ansible -i hosts.yaml dbserver-vm -m ping
ansible-playbook -i hosts.yaml playbooks/deploy-all.yaml
```

## Email Functionality

Η αποστολή email γίνεται κατά το registration μέσω:
- `src/main/java/gr/hua/dit/ds/ds_2025/services/EmailService.java`
- `src/main/resources/templates/email/registration-success.html`

SMTP ρυθμίσεις:
- `spring.mail.host`
- `spring.mail.port`
- `spring.mail.username`
- `spring.mail.password`

## Notes
- Η βάση που χρησιμοποιείται είναι PostgreSQL (όχι Render).
- Για παραγωγικό περιβάλλον προτείνεται αποθήκευση secrets εκτός repository.
